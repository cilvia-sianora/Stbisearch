package stbisearch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cilvia
 */
public class InvertedFile {
	private Util util;
	List<String> terms;
	List<Integer> docs;
	List<Double> weights;
	
	
	public InvertedFile(){
		util = new Util();
		terms = new ArrayList<>();
		docs  = new ArrayList<>();
		weights = new ArrayList<>();
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
				index = findIndex(t.getContent(), doc.no);
				
				// tambahkan di index setelah ditemukan index yang cocok
				terms.add(index, t.getContent());
				docs.add(index, doc.no);
				weights.add(index, t.getWeight());
			}
		}
		
		// membuat inverted file
		PrintWriter writer;
		try {
			writer = new PrintWriter(location, "UTF-8");
			for(int i =0;i<terms.size();i++){
				writer.println(terms.get(i)+" "+docs.get(i)+" "+weights.get(i));
			}
			writer.close();
		} catch (FileNotFoundException|UnsupportedEncodingException ex) {
		}
	}
	
	public int findIndex(String term, int docNo){
		int i = 0;
		
		// mencari index yang cocok berdasarkan keterurutan term
		int compareResult = term.compareToIgnoreCase(terms.get(i));
		while(compareResult>0 && i<terms.size()){
			i++;
		}
		
		if(compareResult == 0){ // jika ketemu term yang sama
			// iterasi lagi yang docs
			while(docNo>docs.get(i) && i<terms.size()){
				i++;
			}
		}
		
		// TODO: kalo buat getInvertedFile, term nya gak ada, harusnya return error
		return i;
	}
	
	public void getInvertedFile(){
		
	}
}
