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
public class DocumentProcess {
	private Util util;
	private InvertedFile invFile;
	
	public DocumentProcess(){
		util = new Util();
		invFile = new InvertedFile();
	}
	
	public void indexing(String locDocs, String locStopwords){
		util.getDocuments(locDocs);
		
		for(Vector doc: util.docs){
			doc.preProcessed(locStopwords);
		}
		
		// put to inverted file
		int index;
		for(Vector doc: util.docs){
			util.termWeighting(doc,"raw", true, false);
			for(Term t: doc.terms){
				// mencari index yang cocok
				index = invFile.findIndexToInsert(t.getContent(), doc.no);
				
				// tambahkan di index setelah ditemukan index yang cocok
				invFile.terms.add(index, t.getContent());
				invFile.docs.add(index, doc.no);
				invFile.weights.add(index, t.getWeight());
			}
		}
		
		invFile.write();
	}
}
