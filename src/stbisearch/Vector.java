package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class Vector {
	public List<String> terms;
	public List<Integer> tf;
	public List<Double> weight;
	
	// get from raw document
	public Vector(String doc){
		terms = new ArrayList<>();
		tf = new ArrayList<>();
		weight = new ArrayList<>();
		
		int index;
		for (String term: doc.split(" ")){
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
}
