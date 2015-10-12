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
	public void write(String location){
		// membuat inverted file
		PrintWriter writer;
		try {
			writer = new PrintWriter(location, "UTF-8");
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
	public int findIndex(String term, int docNo){
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
					if(docNo>docs.get(i)){
						found = true;
					}
					i++;
				}
			}
		}
		
		return i;
	}
	
	// read inverted file
	public void read(String location){
		// clear the list
		terms.clear();
		docs.clear();
		weights.clear();
		
		String[] arr;
		String content = util.readFile(location);
		for(String line: content.split("\n")){
			arr = line.split(" ");
			terms.add(arr[0]);
			docs.add(Integer.parseInt(arr[1]));
			weights.add(Double.parseDouble(arr[2]));
		}
	}
}
