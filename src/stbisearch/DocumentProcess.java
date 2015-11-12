package stbisearch;

import java.util.Map.Entry;

/**
 *
 * @author Cilvia
 */
public class DocumentProcess {
	private Util util;
	private InvertedFile invFile;
	public String locDocuments;
	public String locStopwords;
	
	public DocumentProcess(){
		util = new Util();
		invFile = new InvertedFile();
	}
	
	public void indexing(String locDocs, String locStopwords, String tfMethod, 
			boolean bIdf, boolean bNormalization, boolean bStemming){
		locDocuments = locDocs;
		this.locStopwords = locStopwords;
		util.getDocuments(locDocs);
		
		for(Entry<Integer,Vector> entry: util.docs.entrySet()){
			entry.getValue().preProcessed(locStopwords,bStemming);
		}
		
		// put to inverted file
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			util.termWeighting(doc.getValue(),tfMethod,bIdf,bNormalization);
			for(Entry<String,double[]> t: doc.getValue().terms.entrySet()){
				invFile.put(t.getKey(), doc.getKey(), t.getValue()[1]);
			}
		}
		
		invFile.write();
	}
}
