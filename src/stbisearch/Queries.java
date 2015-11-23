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
	private String tfMethod;
	private boolean bIdf, bNormalization, bStemming;
	
	public Queries(){
		queries = new ArrayList<>();
		invFile = new InvertedFile();
		util = new Util();
		tfMethod = "no";
		bIdf = false;
		bNormalization = false;
		bStemming = false;
		precision = 0;
		recall = 0;
		niap = 0;
	}
	
	// set the configuration for query
	public void setQuerySetting(String tfMethod, boolean bIdf, 
			boolean bNormalization, boolean bStemming){
		this.tfMethod = tfMethod;
		this.bIdf = bIdf;
		this.bNormalization = bNormalization;
		this.bStemming = bStemming;
	}
	
	public String searchInteractive(String queryInput, String locStopwords, String locDocuments){
		String result;
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
		result = qp.search(-1);
		result = result.substring(result.indexOf("\n")); // to delete query number line
		
		queries.add(qp);
		
		return result;
	}
	
	public String searchExperiment(String locRlvJudge, String locQueries, String locStopwords, 
			String locDocuments, int numDocsRetrieved){
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
		
		// do searching for each query
		System.out.println("searching..");
		String result = "";
		for(Map.Entry<Integer,Vector> query: util.queries.entrySet()){
			QueryProcess qp = new QueryProcess();
			qp.util.rlvJudgement = new HashMap<>(util.rlvJudgement);
			qp.util.docs = new HashMap<>(util.docs);
			qp.invFile.file = new HashMap<>(invFile.file);
			qp.util.idfFile = new HashMap<>(util.idfFile);
			
			if(qp.doesHaveRelevantDoc(query.getKey())){
				qp.query.copy(query.getValue());

				// do term-weighting for query
				qp.util.termWeighting(qp.query,tfMethod,bIdf,bNormalization,true);
				
				// searching
				result += qp.search(numDocsRetrieved);
				
				// get relevance judgement result
				result += qp.judgeRelevance();
				
				// add to batch to count the average final
				precision += qp.precision;
				recall += qp.recall;
				niap += qp.niap;
				
				// put in the list
				queries.add(qp);
			}
		}
		
		// count the average final
		result = "Precision = " + (precision/util.queries.size()) + "\n" +
				"Recall = " + (recall/util.queries.size()) + "\n" +
				"NIAP = " + (niap/util.queries.size()) + "\n" +
				"The result of each query:" + "\n" + result;
		
		return result;
	}
	
	public String relevanceFeedbackExperiment(String algo, boolean bSameDocs, boolean bQueryExpansion, 
			int numDocsRetrieved, int numTopDocsRlv){
		String result = "";
		
		for(QueryProcess qp: queries){
			// tentuin doc relevant & irrelevant
			if(numTopDocsRlv == -1){
				qp.determineRelevantDocs(qp.query.no);
			} else { // pseudo
				// put numTopDocsRlv docs to relevantDocs 
			}
			
			if(bSameDocs){
				qp.deleteDocs();
			}
			
			// print query lama
			result += "Query lama = \n" + qp.query.content + "\n";
			result += "Bobot query lama = " + qp.query.getAllWeight() + "\n";
			
			qp.reweighting(bQueryExpansion, algo);
			
			// print query baru
			result += "Query baru = \n" + qp.query.content + "\n";
			result += "Bobot query baru = " + qp.query.getAllWeight() + "\n";
			
			// print result
			result += qp.search(numDocsRetrieved);
			result = result.substring(result.indexOf("\n")); // to delete query number line
		}
		
		return result;
	}
	
	// if numTopDocsRlv is -1, it is pseudo relevance feedback
	public String relevanceFeedbackInteractive(String algo, boolean bSameDocs, boolean bQueryExpansion,
			List<Integer> rlvDocs, List<Integer> irrlvDocs, int numTopDocsRlv){
		String result = "";
		
		for(QueryProcess qp: queries){
			// tentuin doc relevant & irrelevant
			if(numTopDocsRlv == -1){
				qp.relevantDocs = new ArrayList<>(rlvDocs);
				qp.irrelevantDocs = new ArrayList<>(irrlvDocs);
			} else { // pseudo
				// put numTopDocsRlv docs to relevantDocs 
			}
			
			if(bSameDocs){
				qp.deleteDocs();
			}
			
			// print query lama
			result += "Query lama = \n" + qp.query.content + "\n";
			result += "Bobot query lama = " + qp.query.getAllWeight() + "\n";
			
			qp.reweighting(bQueryExpansion, algo);
			
			// print query baru
			result += "Query baru = \n" + qp.query.content + "\n";
			result += "Bobot query baru = " + qp.query.getAllWeight() + "\n";
			
			// print result
			result += qp.search(-1);
			result = result.substring(result.indexOf("\n")); // to delete query number line
		}
		
		return result;
	}
	
	public String pseudoRlvFeedbackInteractive(String queryInput, String locStopwords, String locDocuments,
			boolean bSameDocs, boolean bQueryExpansion, int numTopDocsRlv){
		String result = "";
		
		searchInteractive(queryInput,locStopwords,locDocuments);
		result += relevanceFeedbackInteractive("rocchio", bSameDocs, bQueryExpansion,
			null, null, numTopDocsRlv);
		
		return result;
	}
	
	public String pseudoRlvFeedbackExperiment(String locRlvJudge, String locQueries, String locStopwords, String locDocuments,
			boolean bSameDocs, boolean bQueryExpansion, int numTopDocsRlv, int numDocsRetrieved){
		String result = "";
		
		searchExperiment(locRlvJudge,locQueries,locStopwords,locDocuments,numDocsRetrieved);
		result += relevanceFeedbackExperiment("rocchio", bSameDocs, bQueryExpansion,
			numDocsRetrieved, numTopDocsRlv);
		
		return result;
	}
}
