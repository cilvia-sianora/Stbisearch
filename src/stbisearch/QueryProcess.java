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
	
	// count number of relevant documents retrieved
	private int countRelevantDocs(int queryNo){
		System.out.println("-Count Relevant Docs Retrieved-");
		int i,j;
		boolean stopSearch;
		
		for(Integer x: util.relevantNoDoc){
			System.out.print(x +" ");
		}
		System.out.println();
		
		int count = 0;
		j = 0;
		// for each document retrieved
		for(Integer docNo: resultDocNo){
			System.out.println(docNo);
			i=util.relevantNoQuery.indexOf(queryNo);
			System.out.println("index mulai= "+i);
			System.out.println("index akhir= "+util.relevantNoQuery.lastIndexOf(queryNo));
			stopSearch = false;
			while(!stopSearch && i<=util.relevantNoQuery.lastIndexOf(queryNo)){
				if(docNo <= util.relevantNoDoc.get(i)){
					System.out.println("masuk if");
					if(docNo == util.relevantNoDoc.get(i)){ // if relevant doc
						System.out.println("doc no relevant retrieved");
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
		System.out.println("recall= "+numRlvAcq+"/"+totalRlvDocs);
		return (double)numRlvAcq/(double)totalRlvDocs;
	}
	
	private double getPrecision(int numRlvAcq, int numDocsAcq){
		System.out.println("precision= "+numRlvAcq+"/"+numDocsAcq);
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
		
		return result;
	}
	
	private double similarity(Vector query, Vector doc){
		double sum = 0;
		for(Term t: query.terms){
			System.out.println(t.getContent());
			sum += t.getWeight() * invFile.getWeight(t.getContent(), doc.no);
			System.out.println("sum= "+sum);
		}
		return sum;
	}
	
	// do searching for 1 query
	private String search(Vector query){
		System.out.println("-SEARCH-");
		double result;
		int index;
		for(Vector doc: util.docs){
			System.out.println("docNo= "+doc.no);
			result = similarity(query,doc);
			System.out.println("result= "+result);
			index = findIndex(result);
			resultSim.add(index,result);
			resultDocNo.add(index,doc.no);
		}
		
		return printDocResult(query.no)+judgeRelevance(query.no);
	}
	
	public String searchInteractive(String queryInput, String locStopwords, String locDocuments){
		String result;
		
		util.getDocuments(locDocuments);
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords);
		}
		
		Vector query = new Vector();
		query.content = queryInput;
		query.preProcessed(locStopwords);
		util.termWeighting(query,"raw", true, true);
		result = search(query);
		
		resultSim.clear();
		resultDocNo.clear();
		
		return result;
	}
	
	public String searchExperiment(String locRlvJudge, String locQueries, String locStopwords, String locDocuments){
		util.getQueries(locQueries);
		util.getRelevanceJudgement(locRlvJudge);
		util.getDocuments(locDocuments);
		
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords);
		}
		for(Vector query: util.queries){
			query.preProcessed(locStopwords);
		}
		
		String result = "";
		for(Vector query: util.queries){
			util.termWeighting(query,"raw", true, false);
			result += search(query);
			resultSim.clear();
			resultDocNo.clear();
		}
		
		return result;
	}
	
}
