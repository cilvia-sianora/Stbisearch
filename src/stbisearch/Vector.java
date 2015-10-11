package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class Vector {
	public int no;
	public String title;
	public String content;
	public String author;
	
	public List<String> terms;
	public List<Integer> tf;
	public List<Double> weight;
	
	public Vector(){
		terms = new ArrayList<>();
		tf = new ArrayList<>();
		weight = new ArrayList<>();
		no = 0;
		title = "";
		content = "";
		author = "";
	}
	
	public Vector(int no, String title, String author, String content){
		this.no = no;
		this.content = content;
		this.title = title;
		this.author = author;
		terms = new ArrayList<>();
		tf = new ArrayList<>();
		weight = new ArrayList<>();
	}
	
	// get from raw document
	public void countFreq(){
		int index;
		for (String term: content.split(" ")){
			index = terms.indexOf(term);
			if(index == -1){
				terms.add(term);
				tf.add(1);
			} else {
				tf.add(index, tf.get(index)+1);
			}
		}
	}
	
	public void normalization(){
		
	}
	
	public int getTF(String term){
		return tf.get(terms.indexOf(term));
	}

	public int getMaxTF(){
		int max=0;
		for(int i = 0 ;i<tf.size();i++){
			if(tf.get(i)>max){
				max = tf.get(i);
			}
		}
		return max;
	}
	
	public double getLength(){
		double sum = 0;
		for(double w: weight){
			sum += Math.pow(w,2);
		}
		return Math.sqrt(sum);
	}
	
	public void printVector(){
				System.out.println(no);
				System.out.println(title);
				System.out.println(author);
				System.out.println(content);
				System.out.println("--------------------------------------");
	}
}
