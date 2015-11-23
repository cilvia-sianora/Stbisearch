package stbisearch;

import java.io.IOException;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cilvia
 */
public class StbiSearch {
//	public static Util util;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Util util;
		util = new Util();
		String locQueries = "Test Collection\\CISI\\query.text";
		String locDocuments = "Test Collection\\CISI\\cisi.all";
		String locStopwords = "Test Collection\\stopwords-porter.txt";
		String locRlvJudge = "Test Collection\\CISI\\qrels.text";
		String tfMethod = "raw";
		boolean idf = true;
		boolean norm = false;
		boolean stem = true;
//		String result;
//		util.getDocuments(locDocuments);
//		util.getQueries(locQueries);
//		util.printDocuments();
//		util.printQueries();
//		util.getRelevanceJudgement(locRlvJudge);
//		util.printJudgement();
//		DocumentProcess dp = new DocumentProcess();
//		dp.indexing(locDocuments,locStopwords,tfMethod,idf,norm,stem);
//		Queries qp = new Queries();
//		qp.setQuerySetting(tfMethod,idf,norm,stem);
//		String result = qp.searchExperiment(locRlvJudge, locQueries, locStopwords, locDocuments);
//		System.out.println("-RESULT-");
//		System.out.println(tfMethod+" "+idf+" "+norm+" "+stem);
//		System.out.println(result);
		
//		Map<Integer,List<Integer>> temp = new HashMap<>();
//		
//		temp.put(1, new ArrayList<>());
//		temp.get(1).add(11);
//		temp.get(1).add(12);
//		temp.get(1).add(13);
//		temp.put(2, new ArrayList<>());
//		temp.get(2).add(21);
//		
//		for(Entry<Integer,List<Integer>> entry: temp.entrySet()){
//			System.out.println(entry.getKey());
//			for(Integer i: entry.getValue()){
//				System.out.println(i);
//			}
//		}
//		
//		Map<Integer,List<Integer>> temp2 = new HashMap<>(temp);
//		
//		for(Entry<Integer,List<Integer>> entry: temp2.entrySet()){
//			System.out.println(entry.getKey());
//			for(Integer i: entry.getValue()){
//				System.out.println(i);
//			}
//		}
		
		QueryProcess QP = new QueryProcess();
		QP.determineRelevantDocs(10);
		double countWeightRelevantDoc = QP.countWeightRelevantDoc("system");
		System.out.println(countWeightRelevantDoc);
		double countWeightIRRelevantDoc = QP.countWeightIrrelevantDoc("system", 3);
		System.out.println(countWeightIRRelevantDoc);
		
	}

}
