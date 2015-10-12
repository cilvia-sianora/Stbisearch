package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cilvia
 */
public class DocumentProcess {
	Util util;
	
	public DocumentProcess(){
		util = new Util();
	}
	
//	public void indexing(String location){
////		util.getDocuments(location);
////		util.stemming();
////		util.stopWordRemoval();
////		util.termWeighting("raw", true, true);
//		
//		// put to inverted file
//		List<String> terms = new ArrayList<>();
//		List<Integer> docs = new ArrayList<>();
//		List<Double> weights = new ArrayList<>();
//		
//		int i, compareResult;
//		for(Vector doc: util.docs){
//			for(Term t: doc.terms){
//				i = 0;
//				
//				// mencari index yang cocok berdasarkan keterurutan term
//				compareResult = t.getContent().compareToIgnoreCase(terms.get(i));
//				while(compareResult>0 && i<terms.size()){
//					i++;
//				}
//				
//				if(compareResult == 0){ // jika ketemu term yang sama
//					// iterasi lagi yang docs
//					while(doc.no>docs.get(i) && i<terms.size()){
//						i++;
//					}
//				}
//				
//				// tambahkan di index setelah ditemukan index yang cocok
//				terms.add(i, t.getContent());
//				docs.add(i, doc.no);
//				weights.add(i, t.getWeight());
//			}
//		}
//		
//		// membuat inverted file
//		
//	}
}
