package stbisearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	public List<Term> terms;
	
	public Vector(){
		util = new Util();
		terms = new ArrayList<>();
		no = 0;
		title = "";
		content = "";
		author = "";
	}
	
	public Vector(int no, String title, String author, String content){
		util = new Util();
		this.no = no;
		this.content = content;
		this.title = title;
		this.author = author;
		terms = new ArrayList<>();
	}
	
	private String getAllText(){
		return author + " " + title + " " + content;
	}
	
	public void preProcessed(String locStopwords, boolean bStemming){
		String text = getAllText();
		try {
//			System.out.println("delimiter");
			text = util.delimiter(text);
//			System.out.println(text);
//			System.out.println("stopwords");
			text = util.stopWordRemoval(locStopwords, text);
//			System.out.println(text);
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
		int index;
		for (String term: text.split("\\s+")){
			if(term.length()>0){
				index = findIndexTerm(term);
				if(index == -1){
					terms.add(new Term(term,1,0));
				} else {
					terms.get(index).incrementFreq();
				}
			}
		}
	}	
	
	// get frequency of term
	public int getTF(String term){
		int i = findIndexTerm(term);
                if(i > -1){
			return terms.get(i).getFreq();
		} else {
			return 0;
		}
	}
        
    // get index of term
    // return -1 if term not found
	public int findIndexTerm(String term){
		boolean found = false;
		int i = 0;
		while(!found && i<terms.size()){
			if(terms.get(i).getContent().equalsIgnoreCase(term)){
				found = true;
			} else {
				i++;
			}
		}
                
		if(found){
			return i;
		} else {
			return -1;
		}
	}
	
	// get maximun term frequency in document/query
	public int getMaxTF(){
		int max=0;
		for(int i=0;i<terms.size();i++){
			if(terms.get(i).getFreq()>max){
				max = terms.get(i).getFreq();
			}
		}
		return max;
	}
	
	// get length of document/query
	public double getLength(){
		double sum = 0;
		for(Term t: terms){
			sum += Math.pow(t.getWeight(),2);
		}
		return Math.sqrt(sum);
	}
	
	// do normalization to document/query
	public void normalization(){
		double length = getLength();
		for(int i=0;i<terms.size();i++){
			terms.get(i).setWeight(terms.get(i).getWeight()/length);
		}
	}
	
	public void printVector(){
		System.out.println(no);
		System.out.println(title);
		System.out.println(author);
		System.out.println(content);
		System.out.println("--------------------------------------");
	}
}
