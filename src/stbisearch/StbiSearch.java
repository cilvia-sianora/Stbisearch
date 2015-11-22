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
		DocumentProcess dp = new DocumentProcess();
		dp.indexing(locDocuments,locStopwords,tfMethod,idf,norm,stem);
		QueryProcess qp = new QueryProcess();
		qp.setQuerySetting(tfMethod,idf,norm,stem);
		String result = qp.searchExperiment(locRlvJudge, locQueries, locStopwords, locDocuments);
		System.out.println("-RESULT-");
		System.out.println(tfMethod+" "+idf+" "+norm+" "+stem);
		System.out.println(result);
		
//		InvertedFile file = new InvertedFile();
//		file.readIdf();
		
//		Map<String,Double> map = new HashMap<>();
//		
//		map.put("satu",1.1);
//		map.put("dua",2.2);
//		int res=9999;
//		System.out.println(map.get("tiga"));
//		for(Map.Entry<String,Double> entry : map.entrySet()) {
//			System.out.println(entry.getKey()+" "+entry.getValue());
//		}
		
//		List<String> arrTf = new ArrayList<>();
//		List<Boolean> arrIdf = new ArrayList<>();
//		List<Boolean> arrNorm = new ArrayList<>();
//		List<Boolean> arrStem = new ArrayList<>();
//		arrTf.add("no");arrTf.add("raw");arrTf.add("binary");
//		arrTf.add("aug");arrTf.add("log");
//		arrIdf.add(true); arrIdf.add(false);
//		arrNorm.add(true); arrNorm.add(false);
//		arrStem.add(true); arrStem.add(false);
		
//		tfMethod = "no";
//		idf = true;
////		for(boolean aIdf: arrIdf){
////			idf = aIdf;
//			for(boolean aNorm: arrNorm){
//				norm = aNorm;
//				for(boolean aStem: arrStem){
//					stem = aStem;
////					if(idf){
//						dp.indexing(locDocuments,locStopwords,tfMethod,idf,norm,stem);
//						qp.setQuerySetting(tfMethod,idf,norm,stem);
//						result = qp.searchExperiment(locRlvJudge, locQueries, locStopwords, locDocuments);
//						System.out.println("-RESULT-");
//						System.out.println(tfMethod+" "+idf+" "+norm+" "+stem);
//						System.out.println(result);
////					}
//				}
//			}
////		}
		
//		tfMethod = "aug";
////		for(String tf: arrTf){
////			tfMethod = tf;
//			for(boolean aIdf: arrIdf){
//				idf = aIdf;
//				for(boolean aNorm: arrNorm){
//					norm = aNorm;
//					for(boolean aStem: arrStem){
//						stem = aStem;
////						if(!tf.equals("no") || idf){
//							dp.indexing(locDocuments,locStopwords,tfMethod,idf,norm,stem);
//							qp.setQuerySetting(tfMethod,idf,norm,stem);
//							result = qp.searchExperiment(locRlvJudge, locQueries, locStopwords, locDocuments);
//							System.out.println("-RESULT-");
//							System.out.println(tfMethod+" "+idf+" "+norm+" "+stem);
//							System.out.println(result);
////						}
//					}
//				}
//			}
////		}
		
//	  String document = "the ibm data systems division technical\n"
//		+ " information center (tic) provides an operating developmental\n"
//		+ "system for integrated and compatible mechanized\n"
//		+ " processing of technical information received within the organization.\n"
//		+ "  the system offers several advantages :\n"
//		+ "     1 . it is a sophisticated mechanized system for dissemination\n"
//		+ "and retrieval;\n"
//		+ "     2 . it is compatible with all library mechanized\n"
//		+ "  records produced under a standard processing format\n"
//		+ "  within ibm libraries, providing such traditional tools\n"
//		+ "  as 3 x 5 catalog cards, circulation records and overdue\n"
//		+ "notices;\n"
//		+ "     3 . it is reversible, so that discontinuation of machine\n"
//		+ "processing would not cause gaps in the library's\n"
//		+ "  manual records;\n"
//		+ "     4 . it is controlled, producing statistical evaluations\n"
//		+ "of its own program efficiency;\n"
//		+ "     5 . it is user-oriented, providing 24-hour copy access\n"
//		+ "and immediate microfilm access to its documents;\n"
//		+ "     6 . it is relatively simple, relying on the ibm 1401\n"
//		+ "  data processing system for all its processing and output.\n"
//		+ "\n"
//		+ "  since the system has been operating for over a year, the\n"
//		+ "conclusions drawn are based on actual experience .";
//	  try {
//		String removed = util.delimiter(document);
//		removed = util.stopWordRemoval("Test Collection\\stopwords-porter.txt", removed);
//		removed = util.stemming(removed);
//		System.out.println(removed);
//	  } catch (IOException ex) {
//		Logger.getLogger(StbiSearch.class.getName()).log(Level.SEVERE, null, ex);
//	  }
		
		long start;
//		HashSet<String> hashSet = new HashSet<>();
//		Map<String,String> hashMap = new HashMap<>();
//		ArrayList<String> arrayList = new ArrayList<String>();
//
//		start = System.currentTimeMillis();
//		for (int i = 0; i < 900000; i++) {
//			hashSet.add(String.valueOf(i));
//		}
//
//		System.out.println("Insert HashSet Time: " + (System.currentTimeMillis() - start));
//
//
//		start = System.currentTimeMillis();
//
//		for (int i = 0; i < 900000; i++) {
//			arrayList.add(String.valueOf(i));
//		}
//		System.out.println("Insert ArrayList Time: " + (System.currentTimeMillis() - start));
//		
//
//		start = System.currentTimeMillis();
//
//		for (int i = 0; i < 900000; i++) {
//			hashMap.put(String.valueOf(i),String.valueOf(i));
//		}
//		System.out.println("Insert HashMap Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (String s: hashSet) {	
//		}
//		System.out.println("Get HashSet Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (String s: arrayList) {
//		}
//		System.out.println("Get ArrayList Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (Entry<String,String> s: hashMap.entrySet()) {
//		}
//		System.out.println("Get HashMap Time: " + (System.currentTimeMillis() - start));

//		start = System.currentTimeMillis();
//		for (String s: hashSet) {	
//			hashSet.contains(s);
//		}
//		System.out.println("Contain HashSet Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (String s: arrayList) {
//			arrayList.contains(s);
//		}
//		System.out.println("Contain ArrayList Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (Entry<String,String> s: hashMap.entrySet()) {
//			hashMap.containsKey(s.getKey());
//		}
//		System.out.println("Contain Key HashMap Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//		for (Entry<String,String> s: hashMap.entrySet()) {
//			hashMap.containsKey(s.getValue());
//		}
//		System.out.println("Contain Value HashMap Time: " + (System.currentTimeMillis() - start));
		
//		start = System.currentTimeMillis();
//		util.readFilePerLine(locDocuments);
//		System.out.println("read file per line Time: " + (System.currentTimeMillis() - start));
//		
//		start = System.currentTimeMillis();
//			util.readFile(locDocuments);
//		System.out.println("read file all lines Time: " + (System.currentTimeMillis() - start));
	}

}
