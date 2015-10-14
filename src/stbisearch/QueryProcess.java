package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cilvia
 */
public class QueryProcess {
	private Util util;
	private InvertedFile invFile;
	private List<Integer> resultDocNo;
	private List<Double> resultSim;
	private List<Double> precisionAtRlvDoc;
	public double precision;
	public double recall;
	public double niap;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		invFile.read();
		resultDocNo = new ArrayList<>();
		resultSim = new ArrayList<>();
		precisionAtRlvDoc = new ArrayList<>();
	}
	
	private int countRelevantDocs(int queryNo){
		int i;
		boolean stopSearch;
		
		int count = 0;
		for(Integer docNo: resultDocNo){
			i=util.relevantNoQuery.indexOf(queryNo);
			stopSearch = false;
			while(!stopSearch && i<=util.relevantNoQuery.lastIndexOf(queryNo)){
				if(docNo >= util.relevantNoDoc.get(i)){
					if(docNo == util.relevantNoDoc.get(i)){
						count++;
					}
					stopSearch = true;
				}
				i++;
			}
		}
		return count;
	}
	
	private double getRecall(int queryNo){
		return countRelevantDocs(queryNo)/util.relevantNoDoc.size();
	}
	
	private double getPrecision(int queryNo){
		return countRelevantDocs(queryNo)/resultDocNo.size();
	}
	
	// compute Non-Interpolated Average Precision
	private double getNIAP(){
		return 0;
	}
	
	// find the right index to rank document for the result
	private int findIndex(Double sim){
		int i = 0;
		boolean found = false;
		
		if(resultSim.size()>0){
			// mencari index yang cocok berdasarkan keterurutan term
			while(!found && i<resultSim.size()){
				if(sim >= resultSim.get(i)){
					found = true;
				} else {
					i++;
				}
			}
		}
		
		return i;
	}
	
	// print the rank of document
	private String printDocResult(int queryNo){
		String result = "";
		result += "Query Number = " + queryNo + "\n";
		result += "Result:\n";
		for(int i=0;i<resultDocNo.size();i++){
			result += (i+1) + ". " + resultDocNo.get(i) + "\n";
		}
		
		return result;
	}
	
	public String judgeRelevance(int queryNo){
		String result = "";

		result += "Precision = " + getPrecision(queryNo) + "\n";
		result += "Recall = " + getRecall(queryNo) + "\n";
		result += "Non-Interpolated Average Precision = " + getNIAP() + "\n";
		
		return result;
	}
	
	// do searching for 1 query
	private String search(Vector query){
		double result;
		int index;
		for(Vector doc: util.docs){
			result = query.similarity(doc);
			index = findIndex(result);
			resultSim.add(index,result);
			resultDocNo.add(index,doc.no);
		}
		
		return printDocResult(query.no)+judgeRelevance(query.no);
	}
	
	public String searchInteractive(String queryInput){
		String result;
		Vector query = new Vector();
		query.content = queryInput;
		
		util.stemming();
		util.stopWordRemoval();
		query.countFreq();
		util.termWeighting(query,"raw", true, true);
		result = search(query);
		
		resultSim.clear();
		resultDocNo.clear();
		
		return result;
	}
	
	public String searchExperiment(String locRlvJudge, String locQueries){
		util.getQueries(locQueries);
		util.getRelevanceJudgement(locRlvJudge);
		
		String result = "";
		util.stemming();
		util.stopWordRemoval();
		for(Vector query: util.queries){
			query.countFreq();
			util.termWeighting(query,"raw", true, true);
			result += search(query);
			resultSim.clear();
			resultDocNo.clear();
		}
		
		return result;
	}
	
}
