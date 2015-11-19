package stbisearch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private final String INV_LOCATION = "Test Collection\\invertedfile.txt";
	private final String IDF_LOCATION = "Test Collection\\idf.txt";
	public Map<String,Map<Integer,Double>> file;
	
	public InvertedFile(){
		util = new Util();
		file = new TreeMap<>();
	}
	
	// write inverted file
	public void write(int totalDoc){
		System.out.println("-WRITE INVERTED FILE-");
		// membuat inverted file
		PrintWriter invWriter, idfWriter;
		int countDoc;
		try {
			invWriter = new PrintWriter(INV_LOCATION, "UTF-8");
			idfWriter = new PrintWriter(IDF_LOCATION, "UTF-8");
			for(Entry<String,Map<Integer,Double>> entry1: file.entrySet()){
				countDoc = 0;
				for(Entry<Integer,Double> entry2: entry1.getValue().entrySet()){
//					System.out.println(entry1.getKey()+" "+entry2.getKey()+" "+entry2.getValue());
					invWriter.println(entry1.getKey()+" "+entry2.getKey()+" "+entry2.getValue());
					countDoc++;
				}
				//compute idf
				idfWriter.println(entry1.getKey()+" "+util.computeIdf(totalDoc, countDoc));
			}
			invWriter.close();
			idfWriter.close();
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
		List<String> content = util.readFile(INV_LOCATION);
		
		// parse the file into lines
//		for(String line: content.split("\n")){
		for(String line: content){
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
	
	public String getIdfLocation(){
		return IDF_LOCATION;
	}
	
	public Map<String,Double> readIdf(){
		Map<String,Double> temp = new HashMap<>();
		
		String term;
		double idf;
		String[] arr;
		
		// get the inverted file
		List<String> content = util.readFile(IDF_LOCATION);
		
		// parse the file into lines
//		for(String line: content.split("\n")){
		for(String line: content){
			arr = line.split(" ");
			
			term = arr[0];
			idf = Double.parseDouble(arr[1]);
			
			temp.put(term,idf);
		}
		
		return temp;
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
