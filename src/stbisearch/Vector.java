package stbisearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class Vector {
	private Util util;
	public int no;
	public String title;
	public String content;
	public String author;
	private int maxTf;
	
	// key = term
	// value[0] = frequency
	// value[1] = weight
	public Map<String,double[]> terms; 
	
	public Vector(){
		terms = new HashMap<>();
		util = new Util();
		no = 0;
		title = "";
		content = "";
		author = "";
		maxTf = 0;
	}
	
	public Vector(int no, String title, String author, String content){
		util = new Util();
		this.no = no;
		this.content = content;
		this.title = title;
		this.author = author;
		terms = new HashMap<>();
		maxTf = 0;
	}
	
	public void copy(Vector v){
		no = v.no;
		title = v.title;
		content = v.content;
		author = v.author;
		maxTf = v.maxTf;
		terms = new HashMap<>(v.terms); 
	}
	
	private String getAllText(){
		return author + " " + title + " " + content;
	}
	
	public List<String> getTerms(){
		List<String> result = new ArrayList<>();
		for(Entry<String,double[]> entry: terms.entrySet()){
			if(entry.getValue()[1] > 0){
				result.add(entry.getKey() + " " + entry.getValue()[1] + "\n");
			}
		}
		return result;
	}
	
	public Double getAllWeight(){
		double sum = 0;
		for(Entry<String,double[]> entry: terms.entrySet()){
			sum += entry.getValue()[1];
		}
		return sum;
	}
	
	// preprocessing: delimiter->stopwordremoval->stemming
	public void preProcessed(String locStopwords, boolean bStemming){
		String text = getAllText();
		try {
			text = util.delimiter(text);
			text = util.stopWordRemoval(locStopwords, text);
		} catch (IOException ex) {
			Logger.getLogger(DocumentProcess.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(bStemming){
			text = util.stemming(text);
		}
		countFreq(text);
	}
	
	// count frequency of term from raw document/query
	public void countFreq(String text){
		terms.clear();
		for (String term: text.split("\\s+")){
			if(term.length()>0){
				double[] temp = new double[2];
				temp[1] = 1;
				if(terms.containsKey(term)){
					temp[0] = terms.get(term)[0]+1;
				} else {
					temp[0] = 1;
				}
				terms.put(term, temp);
				
				//get max tf
				if(maxTf < temp[0]){
					maxTf = (int)temp[0];
				}
			}
		}
	}	
	
	// get frequency of term
	public int getTF(String term){
		if(terms.containsKey(term)){
			return (int)terms.get(term)[0];
		} else {
			return -1;
		}
	}
	
	// get maximun term frequency in document/query
	public int getMaxTF(){
		return maxTf;
	}
	
	// get length of document/query
	public double getLength(){
		double sum = 0;
		for(Map.Entry<String,double[]> entry : terms.entrySet()) {
			sum += Math.pow(entry.getValue()[1],2);
		}
		return Math.sqrt(sum);
	}
	
	// do normalization to document/query
	public void normalization(){
		double length = getLength();
		for(Map.Entry<String,double[]> entry : terms.entrySet()) {
			entry.getValue()[1] = entry.getValue()[1]/length;
		}
	}
	
	public void printVector(){
		System.out.println(no);
		System.out.println(title);
		System.out.println(author);
		System.out.println(content);
		System.out.println("--------------------------------------");
	}
	
	public void printTerms(){
		for(Entry<String,double[]> entry: terms.entrySet()){
			System.out.println(entry.getKey()+" "+entry.getValue()[0]+" "+entry.getValue()[1]);
		}
	}
}
