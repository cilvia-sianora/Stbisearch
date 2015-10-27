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
	private static double SIM_THRESHOLD = 0;;
	private String tfMethod;
	private boolean bIdf, bNormalization, bStemming;
	
//	StbiSearch ss;
	
	public QueryProcess(){
//		ss = new StbiSearch();
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
			if(i != -1){
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
		result += "NIAP = " + getNIAP(totalRlvDocs) + "\n";
				
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
		invFile.read();
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
		
		System.out.println("getting queries..");
		util.getQueries(locQueries);
		System.out.println(util.queries.size());
		System.out.println("getting relevance judgement..");
		util.getRelevanceJudgement(locRlvJudge);
		System.out.println("getting document..");
		util.getDocuments(locDocuments);
		System.out.println("preprocessing..");
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords,bStemming);
		}
		for(Vector query: util.queries){
			query.preProcessed(locStopwords,bStemming);
		}
		
		System.out.println("getting inverted file..");
		invFile.read();
		
		String result = "";
		for(Vector query: util.queries){
			System.out.println("termweighting..");
			util.termWeighting(query,tfMethod,bIdf,bNormalization);
			System.out.println("searching..");
			result += search(query);
			result += judgeRelevance(query.no);
			clear();
		}
		
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
