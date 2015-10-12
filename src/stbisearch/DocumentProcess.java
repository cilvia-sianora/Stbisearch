package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cilvia
 */
public class DocumentProcess {
	private Util util;
	private InvertedFile invFile;
	
	public DocumentProcess(){
		util = new Util();
		invFile = new InvertedFile();
	}
	
	public void indexing(String location){
//		util.getDocuments(location);
//		util.stemming();
//		util.stopWordRemoval();
//		util.termWeighting("raw", true, true);
		
		// put to inverted file
		int index;
		for(Vector doc: util.docs){
			for(Term t: doc.terms){
				// mencari index yang cocok
				index = invFile.findIndex(t.getContent(), doc.no);
				
				// tambahkan di index setelah ditemukan index yang cocok
				invFile.terms.add(index, t.getContent());
				invFile.docs.add(index, doc.no);
				invFile.weights.add(index, t.getWeight());
			}
		}
		
		invFile.write(location);
	}
}
