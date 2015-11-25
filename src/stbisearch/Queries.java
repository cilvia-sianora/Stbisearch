package stbisearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cilvia
 */
public class Queries {
	private List<QueryProcess> queries;
	private Util util;
	private InvertedFile invFile;
	private double precision, recall, niap; // to count the average final
	
	// for configuration
	public String tfMethod, algo;
	private boolean bIdf, bNormalization, bStemming, bSameDocs, bQueryExpansion;
	private int numTopRlvDocs;
	
	public Queries(){
		queries = new ArrayList<>();
		invFile = new InvertedFile();
		util = new Util();
		tfMethod = "no";
		bIdf = false;
		bNormalization = false;
		bStemming = false;
		algo = "rocchio";
		bSameDocs = true;
		bQueryExpansion = false;
		numTopRlvDocs = -1;
		precision = 0;
		recall = 0;
		niap = 0;
	}
	
	// set the configuration for query
	public void setQuerySetting(String tfMethod, boolean bIdf, 
			boolean bNormalization, boolean bStemming, String algo,
			boolean bSameDocs, boolean bQueryExpansion,
                        int numTopRlvDocs){
		this.tfMethod = tfMethod;
		this.bIdf = bIdf;
		this.bNormalization = bNormalization;
		this.bStemming = bStemming;
		this.algo = algo;
		this.bSameDocs = bSameDocs;
		this.bQueryExpansion = bQueryExpansion;
                this.numTopRlvDocs = numTopRlvDocs;
	}
	
	public List<String> searchInteractive(String queryInput, String locStopwords, 
			String locDocuments){
//		String result;
		queries.clear();
		
		// get query input
		Vector query = new Vector();
		query.content = queryInput;
		
		// do preprocessing for query
		query.preProcessed(locStopwords,bStemming);
		
		QueryProcess qp = new QueryProcess();
		qp.query.copy(query);
		
		// read inverted file
		qp.invFile.read();
		
		// read idf file
		qp.util.getIdfFile(qp.invFile.getIdfLocation());
		
		// load documents
		qp.util.getDocuments(locDocuments);
		
		// do preprocessing for documents
		for(Map.Entry<Integer,Vector> doc: qp.util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}
		
		// do term-weighting for query
		qp.util.termWeighting(qp.query,tfMethod,bIdf,bNormalization,true);
		
		// do searching
		List<String> result = new ArrayList<>(qp.search(-1));
		
		queries.add(qp);
		
		return result;
	}
	
	public List<String> searchExperiment(String locRlvJudge, String locQueries, 
			String locStopwords, String locDocuments, int numDocsRetrieved){
		queries.clear();

		// get files
		util.getQueries(locQueries);
		
		// do preprocessing
		System.out.println("preprocessing..");
		for(Map.Entry<Integer,Vector> query: util.queries.entrySet()){
			query.getValue().preProcessed(locStopwords,bStemming);
		}
		
		util.getRelevanceJudgement(locRlvJudge);

		util.getDocuments(locDocuments);
		for(Map.Entry<Integer,Vector> doc: util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}

		// read inverted file
		System.out.println("getting inverted file..");
		invFile.read();

		// read idf file
		util.getIdfFile(invFile.getIdfLocation());
		
		// init for batch
		precision = 0;
		recall = 0;
		niap = 0;
		
		// do searching for each query
		System.out.println("searching..");
		List<String> result = new ArrayList<>();
		for(Map.Entry<Integer,Vector> query: util.queries.entrySet()){
			QueryProcess qp = new QueryProcess();
			qp.util.rlvJudgement = new HashMap<>(util.rlvJudgement);
			qp.util.docs = new HashMap<>(util.docs);
			qp.invFile.file = new HashMap<>(invFile.file);
			qp.util.idfFile = new HashMap<>(util.idfFile);
			
			if(qp.doesHaveRelevantDoc(query.getKey())){
				qp.query.copy(query.getValue());
				
				// do term-weighting for query
				System.out.println("Termweighting query "+qp.query.no+" ...");
				qp.util.termWeighting(qp.query,tfMethod,bIdf,bNormalization,true);
				
				// searching
				result.add("Query number = "+qp.query.no+"\n");
				result.addAll(qp.search(numDocsRetrieved));
				
				// get relevance judgement result
				result.addAll(qp.judgeRelevance());
				
				// add to batch to count the average final
				precision += qp.precision;
				recall += qp.recall;
				niap += qp.niap;
				
				// put in the list
				queries.add(qp);
			}
		}
		
		// count the average final
		result.add(0,"The result of each query:" + "\n");
                result.add(0,"NIAP = " + (niap/util.queries.size()) + "\n" );
                result.add(0,"Recall = " + (recall/util.queries.size()) + "\n");
                result.add(0,"Precision = " + (precision/util.queries.size()) + "\n");
                
		
		return result;
	}
	
	public List<String> relevanceFeedbackExperiment(int numDocsRetrieved){
		List<String> result = new ArrayList<>();
		
		// init for batch
		precision = 0;
		recall = 0;
		niap = 0;
		
		for(QueryProcess qp: queries){
			// tentuin doc relevant & irrelevant
			// put numTopDocsRlv docs to relevantDocs
			// if numTopDocsRlv is -1, pseudo
			System.out.println("Determine relevant docs...");
			qp.determineRelevantDocs(numTopRlvDocs);
			
			// print query lama
			result.add("Query number = "+qp.query.no+"\n");
			result.add("Query lama: \n");
			result.addAll(qp.query.getTerms());
                        
			System.out.println("Reweighting...");
			qp.reweighting(bQueryExpansion, algo);
			
			// print query baru
			result.add("Query baru: \n");
			result.addAll(qp.query.getTerms());
                        
			if(!bSameDocs){
				System.out.println("Delete docs...");
				System.out.println(qp.util.docs.size());
				qp.deleteDocs();
				System.out.println(qp.util.docs.size());
			}
			
			// print result
			result.addAll(qp.search(numDocsRetrieved));
			
			// get relevance judgement result
			result.addAll(qp.judgeRelevance());
			
			// add to batch to count the average final
			precision += qp.precision;
			recall += qp.recall;
			niap += qp.niap;
		}
		
		// count the average final
		result.add(0,"The result of each query:" + "\n");
                result.add(0,"NIAP = " + (niap/util.queries.size()) + "\n" );
                result.add(0,"Recall = " + (recall/util.queries.size()) + "\n");
                result.add(0,"Precision = " + (precision/util.queries.size()) + "\n");
		
		return result;
	}
	
	// if numTopDocsRlv is -1, it is pseudo relevance feedback
	public List<String> relevanceFeedbackInteractive(List<Integer> rlvDocs, 
			List<Integer> irrlvDocs){
		List<String> result = new ArrayList<>();
		
		for(QueryProcess qp: queries){
			// tentuin doc relevant & irrelevant
			if(numTopRlvDocs == -1){
				qp.relevantDocs.addAll(rlvDocs);
				qp.irrelevantDocs.addAll(irrlvDocs);
			} else { // pseudo
				qp.determineRelevantDocs(numTopRlvDocs);
			}
			
			// print query lama
			result.add("Query lama: \n");
                        result.addAll(qp.query.getTerms());
			
			qp.reweighting(bQueryExpansion, algo);
			
			// print query baru
			result.add("Query baru: \n");
                        result.addAll(qp.query.getTerms());
			
			if(!bSameDocs){
				System.out.println("Delete docs...");
				qp.deleteDocs();
			}
			
			// print result
			result.addAll(qp.search(-1));
		}
		
		return result;
	}
	
	public List<String> pseudoRlvFeedbackInteractive(String queryInput,
			String locStopwords, String locDocuments){
		List<String> result = new ArrayList<>();
		
		searchInteractive(queryInput,locStopwords,locDocuments);
		result.addAll(relevanceFeedbackInteractive(null, null));
		
		return result;
	}
	
	public List<String> pseudoRlvFeedbackExperiment(String locRlvJudge, 
			String locQueries, String locStopwords, String locDocuments,
			int numDocsRetrieved){
		List<String> result = new ArrayList<>();
		
		searchExperiment(locRlvJudge,locQueries,locStopwords,locDocuments,numDocsRetrieved);
		result.addAll(relevanceFeedbackExperiment(numDocsRetrieved));
		
		return result;
	}
}
