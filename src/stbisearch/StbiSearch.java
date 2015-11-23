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
		Queries qp = new Queries();
		qp.setQuerySetting(tfMethod,idf,norm,stem);
		String result = qp.searchExperiment(locRlvJudge, locQueries, locStopwords, locDocuments);
		System.out.println("-RESULT-");
		System.out.println(tfMethod+" "+idf+" "+norm+" "+stem);
		System.out.println(result);
		
//		List<Vector> vecs = new ArrayList<>();
//		for(int i=0;i<6;i++){
//			Vector vec = new Vector();
//			vec.no = i;
//			vecs.add(vec);
//		}
//		for(Vector vec: vecs){
//			System.out.println(vec.no);
//		}
//		List<Vector> vecs2 = new ArrayList<>(vecs);
//		for(Vector vec: vecs2){
//			System.out.println(vec.no);
//		}
//		vecs.remove(3);
//		for(Vector vec: vecs){
//			System.out.println(vec.no);
//		}
//		for(Vector vec: vecs2){
//			System.out.println(vec.no);
//		}
//		InvertedFile file = new InvertedFile();
//		file.readIdf();
		
//		QueryProcess QP = new QueryProcess();
//		QP.determineRelevantDocs(7);
		
	}

}
