package stbisearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static double SIM_THRESHOLD = 0.05;
	private double precision;
	private double recall;
	private double niap;
	private String tfMethod;
	private boolean bIdf, bNormalization, bStemming;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		resultDocNo = new ArrayList<>();
		resultSim = new ArrayList<>();
		precisionAtRlvDoc = new ArrayList<>();
		tfMethod = "no";
		bIdf = false;
		bNormalization = false;
		bStemming = false;
	}
	
	private void clear(){
		resultDocNo.clear();
		resultSim.clear();
		precisionAtRlvDoc.clear();
	}
	
	// count number of relevant documents retrieved
	private int countRelevantDocs(int queryNo){
		int i,j;
		boolean stopSearch;
		
		int count = 0;
		j = 0;
		// for each document retrieved
		for(Integer docNo: resultDocNo){
			i=util.relevantNoQuery.indexOf(queryNo);
			stopSearch = false;
			while(!stopSearch && i<=util.relevantNoQuery.lastIndexOf(queryNo)){
				if(docNo <= util.relevantNoDoc.get(i)){
					if(docNo == util.relevantNoDoc.get(i)){ // if relevant doc
						count++;
						precisionAtRlvDoc.add((double)count/(double)(j+1));
					}
					stopSearch = true;
				}
				i++;
			}
			j++;
		}
		return count;
	}
	
	private int countAllRelevantDocs(int queryNo){
		return util.relevantNoQuery.lastIndexOf(queryNo) - util.relevantNoQuery.indexOf(queryNo) + 1;
	}
	
	private double getRecall(int numRlvAcq, int totalRlvDocs){
		return (double)numRlvAcq/(double)totalRlvDocs;
	}
	
	private double getPrecision(int numRlvAcq, int numDocsAcq){
		return (double)numRlvAcq/(double)numDocsAcq;
	}
	
	// compute Non-Interpolated Average Precision
	private double getNIAP(int totalRlvDocs){
		double sum = 0;
		for(double p: precisionAtRlvDoc){
			sum += p;
		}
		return sum/(double)totalRlvDocs;
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
	
	private String judgeRelevance(int queryNo){
		String result = "";

		int numRlvAcq = countRelevantDocs(queryNo);
		int totalRlvDocs = countAllRelevantDocs(queryNo);
		
		result += "Precision = " + getPrecision(numRlvAcq,resultDocNo.size()) + "\n";
		result += "Recall = " + getRecall(numRlvAcq,totalRlvDocs) + "\n";
		result += "Non-Interpolated Average Precision = " + getNIAP(totalRlvDocs) + "\n";
		
		precision += getPrecision(numRlvAcq,resultDocNo.size());
		recall += getRecall(numRlvAcq,totalRlvDocs);
		niap += getNIAP(totalRlvDocs);
		
		return result;
	}
	
	private double similarity(Vector query, Vector doc){
		double sum = 0;
		for(Term t: query.terms){
			sum += t.getWeight() * invFile.getWeight(t.getContent(), doc.no);
		}
		return sum;
	}
	
	// do searching for 1 query
	private String search(Vector query){
		invFile.read();
		precision = 0;
		recall = 0;
		niap = 0;
		
		double result;
		int index;
		for(Vector doc: util.docs){
			result = similarity(query,doc);
			if(result > SIM_THRESHOLD){
				index = findIndex(result);
				resultSim.add(index,result);
				resultDocNo.add(index,doc.no);
			}
		}
		
		return printDocResult(query.no);
	}
	
	public String searchInteractive(String queryInput, String locStopwords, String locDocuments){
		String result;
		
		util.getDocuments(locDocuments);
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords,bStemming);
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
		util.getQueries(locQueries);
		util.getRelevanceJudgement(locRlvJudge);
		util.getDocuments(locDocuments);
		
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords,bStemming);
		}
		for(Vector query: util.queries){
			query.preProcessed(locStopwords,bStemming);
		}
		
		String result = "";
		for(Vector query: util.queries){
			util.termWeighting(query,tfMethod,bIdf,bNormalization);
			result += search(query);
			result += judgeRelevance(query.no);
			clear();
		}
		
		result = "Precision = " + (precision/(double)util.queries.size()) + "\n" +
				"Recall = " + (recall/(double)util.queries.size()) + "\n" +
				"NIAP = " + (niap/(double)util.queries.size()) + "\n" +
				"Searching Result:" + "\n" + result;
		
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
