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
	private final String LOCATION = "Test Collection\\invertedfile.txt";
	public List<String> terms;
	public List<Integer> docs;
	public List<Double> weights;
	
	
	public InvertedFile(){
		util = new Util();
		terms = new ArrayList<>();
		docs  = new ArrayList<>();
		weights = new ArrayList<>();
	}
	
	// write inverted file
	public void write(){
		System.out.println("-WRITE INVERTED FILE-");
		// membuat inverted file
		PrintWriter writer;
		try {
			writer = new PrintWriter(LOCATION, "UTF-8");
			for(int i =0;i<terms.size();i++){
				System.out.println(terms.get(i)+" "+docs.get(i)+" "+weights.get(i));
				writer.println(terms.get(i)+" "+docs.get(i)+" "+weights.get(i));
			}
			writer.close();
		} catch (FileNotFoundException|UnsupportedEncodingException ex) {
		}
	}
	
	// find the right index to put term for indexing
	// or find the index of a term for searching
	// NOTE: for searching, if return index is more than the size of list,
	//			it means the term is not found
	public int findIndexToInsert(String term, int docNo){
		int i = 0;
		boolean found = false;
		
		if(terms.size()>0){
			// mencari index yang cocok berdasarkan keterurutan term
			int compareResult = 1;
			while(!found && i<terms.size()){
				compareResult = term.compareToIgnoreCase(terms.get(i));
				if(compareResult<=0){
					found = true;
				} else {
					i++;
				}
			}

			if(compareResult == 0){ // jika ketemu term yang sama
				// iterasi lagi yang docs
				found = false;
				while(!found && i<terms.size()){
					if(docNo<docs.get(i) || !term.equalsIgnoreCase(terms.get(i))){
						found = true;
					} else {
						i++;
					}
				}
			}
		}
		
		return i;
	}
	
	// read inverted file
	public void read(){
		// clear the list
		terms.clear();
		docs.clear();
		weights.clear();
		
		String[] arr;
		String content = util.readFile(LOCATION);
		for(String line: content.split("\n")){
			arr = line.split(" ");
			terms.add(arr[0]);
			docs.add(Integer.parseInt(arr[1]));
			weights.add(Double.parseDouble(arr[2]));
		}
	}
	
	public int findIndex(String term, int docNo){
		int index = terms.indexOf(term);
		boolean found = false;
		if(index != -1){
			while(!found && index<=terms.lastIndexOf(term)){
				if(docNo == docs.get(index)){
					found = true;
				} else {
					index++;
				}
			}
			if(index>terms.lastIndexOf(term)){
				index = -1;
			}
		}
		return index;
	}
	
	public double getWeight(String term, int docNo){
		int index = findIndex(term,docNo);
		if(index != -1){
			return weights.get(index);
		} else {
			return 0;
		}
	}
}
