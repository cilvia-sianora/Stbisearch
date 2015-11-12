package stbisearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Cilvia
 */
public class QueryProcess {
	private Util util;
	private InvertedFile invFile;
	private Map<Double,List<Integer>> resultSearch;
	private List<Double> precisionAtRlvDoc;
	private static double SIM_THRESHOLD = 0;;
	private String tfMethod;
	private boolean bIdf, bNormalization, bStemming;
	private double precision, recall, niap;
	private int numRlvDocsRetrieved,numDocsRetrieved;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		resultSearch = new TreeMap<>();
		precisionAtRlvDoc = new ArrayList<>();
		tfMethod = "no";
		bIdf = false;
		bNormalization = false;
		bStemming = false;
		precision = 0;
		recall = 0;
		niap = 0;
	}
	
	private void clear(){
		resultSearch.clear();
		precisionAtRlvDoc.clear();
	}
	
	// return true if document docNo is relevant for query queryNo
	private boolean isRelevantDoc(int queryNo, int docNo){
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if(temp!=null){
			return temp.contains(docNo);
		} else { // if query queryNo has no relevant document
			return false;
		}
	}
	
	// return number of relevant documents of a query
	// return -1 if there is no relevant document
	private int countAllRelevantDocs(int queryNo){
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if(temp!=null){
			return temp.size();
		} else {
			return -1;
		}
	}
	
	private boolean doesHaveRelevantDoc(int queryNo){
		return (countAllRelevantDocs(queryNo) != -1);
	}
	
	private double getRecall(int totalRlvDocs){
		return (double)numRlvDocsRetrieved/(double)totalRlvDocs;
	}
	
	private double getPrecision(){
		return (double)numRlvDocsRetrieved/(double)numDocsRetrieved;
	}
	
	// compute Non-Interpolated Average Precision
	private double getNIAP(int totalRlvDocs){
		double sum = 0;
		for(double p: precisionAtRlvDoc){
			sum += p;
		}
		return sum/(double)totalRlvDocs;
	}
	
	// print the rank of document
	private String getDocResult(int queryNo){
		numRlvDocsRetrieved = 0;
		numDocsRetrieved = 0;
		
		String result = "";
		result += "Query Number = " + queryNo + "\n";
		result += "Result:\n";
		
		for(Entry<Double,List<Integer>> entry: resultSearch.entrySet()){
			for(Integer docNo: entry.getValue()){
				numDocsRetrieved++;
				
				// print document retrieved
				result += numDocsRetrieved + ". " + docNo + 
						" " + util.docs.get(docNo).title + "\n";
				
				// get precision if document retrieved is relevant
				if(isRelevantDoc(queryNo,docNo)){
					numRlvDocsRetrieved++;
					precisionAtRlvDoc.add(getPrecision());
				}
			}
		}
		
		
		return result;
	}
	
	private String judgeRelevance(int queryNo){
		String result = "";

		int totalRlvDocs = countAllRelevantDocs(queryNo);
		
		
		result += "Precision = " + getPrecision() + "\n";
		result += "Recall = " + getRecall(totalRlvDocs) + "\n";
		result += "NIAP = " + getNIAP(totalRlvDocs) + "\n";
		
		precision += getPrecision();
		recall += getRecall(totalRlvDocs);
		niap += getNIAP(totalRlvDocs);
		
		return result;
	}
	
	private double similarity(Vector query, Vector doc){
		double sum = 0;
		for(Entry<String,double[]> entry: query.terms.entrySet()){
			sum += entry.getValue()[1] * invFile.getWeight(entry.getKey(), doc.no);
		}
		return sum;
	}
	
	// do searching for 1 query
	private String search(Vector query){
		double result;
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			result = similarity(query,doc.getValue());
			if(result > SIM_THRESHOLD){
				if(!resultSearch.containsKey(result)){
					resultSearch.put(result, new ArrayList<>());
				}
				resultSearch.get(result).add(doc.getKey());
			}
		}
		
		return getDocResult(query.no);
	}
	
	public String searchInteractive(String queryInput, String locStopwords, String locDocuments){
		invFile.read();
		String result;
		
		util.getDocuments(locDocuments);
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}
		
		Vector query = new Vector();
		query.content = queryInput;
		query.preProcessed(locStopwords,bStemming);
		util.termWeighting(query,tfMethod,bIdf,bNormalization);
		
		result = search(query);
		result = result.substring(result.indexOf("\n"));
		clear();
		
		return result;
	}
	
	public String searchExperiment(String locRlvJudge, String locQueries, String locStopwords, String locDocuments){
		precision = 0;
		recall = 0;
		niap = 0;
		
		util.getQueries(locQueries);
		util.getRelevanceJudgement(locRlvJudge);
		util.getDocuments(locDocuments);
		
		System.out.println("preprocessing..");
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}
		for(Entry<Integer,Vector> query: util.queries.entrySet()){
			query.getValue().preProcessed(locStopwords,bStemming);
		}
		
		System.out.println("getting inverted file..");
		invFile.read();
		
		System.out.println("searching..");
		String result = "";
		for(Entry<Integer,Vector> query: util.queries.entrySet()){
			if(doesHaveRelevantDoc(query.getKey())){
				util.termWeighting(query.getValue(),tfMethod,bIdf,bNormalization);
				result += search(query.getValue());
				result += judgeRelevance(query.getValue().no);
				clear();
			}
		}
		
		result = "Precision = " + (precision/util.queries.size()) + "\n" +
				"Recall = " + (recall/util.queries.size()) + "\n" +
				"NIAP = " + (niap/util.queries.size()) + "\n" +
				"The result of each query:" + "\n" + result;
		
		return result;
	}
	
	public void setQuerySetting(String tfMethod, boolean bIdf, 
			boolean bNormalization, boolean bStemming){
		this.tfMethod = tfMethod;
		this.bIdf = bIdf;
		this.bNormalization = bNormalization;
		this.bStemming = bStemming;
	}
	
}
