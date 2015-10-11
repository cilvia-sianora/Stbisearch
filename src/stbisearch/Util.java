package stbisearch;

import static java.lang.Math.log;

/**
 *
 * @author Cilvia
 */
public class Util {
	
	public void getRelevanceJudgement(String Location){
	
	}
	
	public void getDocument(String Location){
	
	}
	
	public void getQueries(String Location){
	
	}
	
	public void getStopWords(String Location){
	
	}
	
	public int rawTF(Vector vec, String term){
            return vec.getTF(term);
	}
	
	public double logTF(Vector vec, String term){
         return 1+log(vec.getTF(term));
    }
	
	public double augTF(Vector vec,String term){
         return (0.5+(0.5*vec.getTF(term)/vec.getMaxTF()));
	}
	
	public int binaryTF(Vector vec,String term){
		if((vec.getTF(term))>0){
			return 1;
		}else{
			return 0;
		}             
	}
	
	public double idf(){
		return 0f;
	}
	
	public void stemming(){
	
	}
	
	public void stopWordRemoval(){
	
	}

	
}
