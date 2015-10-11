package stbisearch;

import java.util.ArrayList;
import java.util.List;

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
		util.getDocuments("C:\\Users\\Anggi\\Documents\\NetBeansProjects\\StbiSearch\\Test Collection\\ADI\\adi.all");
		util.getQueries("C:\\Users\\Anggi\\Documents\\NetBeansProjects\\StbiSearch\\Test Collection\\ADI\\query.text");
		util.printDocuments();
		util.printQueries();
	}
	
}
