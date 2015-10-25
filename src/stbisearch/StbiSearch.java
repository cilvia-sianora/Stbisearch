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
public class StbiSearch {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Util util = new Util();
		util.getDocuments("Test Collection\\soal\\document.txt");
		util.getQueries("Test Collection\\soal\\query.txt");
		util.printDocuments();
		util.printQueries();
//		util.getRelevanceJudgement("Test Collection\\ADI\\qrels.text");
//		util.printJudgement();
		DocumentProcess dp = new DocumentProcess();
		dp.indexing("Test Collection\\soal\\document.txt");
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
    }

}
