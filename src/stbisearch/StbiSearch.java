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
		
		QueryProcess QP = new QueryProcess();
		QP.determineRelevantDocs(7);
		
	}

}
