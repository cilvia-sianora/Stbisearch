package stbisearch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Cilvia
 */
public class InvertedFile {
	private Util util;
	private final String LOCATION = "Test Collection\\invertedfile.txt";	
	public Map<String,Map<Integer,Double>> file;
	
	public InvertedFile(){
		util = new Util();
		file = new TreeMap<>();
	}
	
	// write inverted file
	public void write(){
		System.out.println("-WRITE INVERTED FILE-");
		// membuat inverted file
		PrintWriter writer;
		try {
			writer = new PrintWriter(LOCATION, "UTF-8");
			for(Entry<String,Map<Integer,Double>> entry1: file.entrySet()){
				for(Entry<Integer,Double> entry2: entry1.getValue().entrySet()){
//					System.out.println(entry1.getKey()+" "+entry2.getKey()+" "+entry2.getValue());
					writer.println(entry1.getKey()+" "+entry2.getKey()+" "+entry2.getValue());
				}
			}
			writer.close();
		} catch (FileNotFoundException|UnsupportedEncodingException ex) {
		}
		file.clear();
	}

	// read inverted file
	public void read(){
		// clear the list
		file.clear();
		
		String term;
		int docNo;
		double weight;
		String[] arr;
		
		// get the inverted file
		String content = util.readFile(LOCATION);
		
		// parse the file into lines
		for(String line: content.split("\n")){
			arr = line.split(" ");
			
			term = arr[0];
			docNo = Integer.parseInt(arr[1]);
			weight = Double.parseDouble(arr[2]);
			
			if(!file.containsKey(term)){
				file.put(term, new TreeMap<>());
			}
			file.get(term).put(docNo, weight);
		}
	}
	
	// get the weight of term in document docNo
	public double getWeight(String term, int docNo){
		Map<Integer,Double> temp = file.get(term);
		if(temp != null){
			Double result = temp.get(docNo);
			if(result != null){
				return result;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	// put an element to invertedfile
	public void put(String term, int docNo, double weight){
		if(!file.containsKey(term)){
			file.put(term, new TreeMap<>());
		}
		file.get(term).put(docNo, weight);
	}
	
	public void print(){
		for(Entry<String,Map<Integer,Double>> entry1: file.entrySet()){
			for(Entry<Integer,Double> entry2: entry1.getValue().entrySet()){
				System.out.println(entry1.getKey()+" "+entry2.getKey()+" "+entry2.getValue());
			}
		}
	}
}
