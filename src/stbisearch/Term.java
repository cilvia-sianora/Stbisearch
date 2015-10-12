package stbisearch;

/**
 *
 * @author 
 */
public class Term {
	private String content;
	private int frequency;
	private double weight;
	
	public Term(String content, int freq, double weight){
		this.content = content;
		frequency = freq;
		this.weight = weight;
	}
	
	public void incrementFreq(){
		frequency++;
	}
	
	public void setWeight(double w){
		weight = w;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public int getFreq(){
		return frequency;
	}
	
	public String getContent(){
		return content;
	}
}
