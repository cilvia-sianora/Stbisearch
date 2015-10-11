package stbisearch;

import java.io.IOException;
import static java.lang.Math.log;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Cilvia
 */
public class Util {
	
	public String getRelevanceJudgement(String Location) throws IOException{
            String content;
            content = new String(Files.readAllBytes(Paths.get(Location)));
            return content;	
	}
	
	public String getDocument(String Location) throws IOException{
            String content;
            content = new String(Files.readAllBytes(Paths.get(Location)));
            return content;
	}
	
	public String getQueries(String Location) throws IOException{
            String content;
            content = new String(Files.readAllBytes(Paths.get(Location)));
            return content;
	}
	
	public String getStopWords(String Location) throws IOException{
            String content;
            content = new String(Files.readAllBytes(Paths.get(Location)));
            return content;
	}
	
	public int rawTF(Vector vec, String term){
            return vec.getTF(term);
	}
	
	public double logTF(Vector vec, String term){
            return 1+log(vec.getTF(term));
        }
	
	public float augTF(Vector vec,String term){
            return (float) (0.5+(0.5*vec.getTF(term)/vec.getMaxTF()));
	}
	
	public int binaryTF(Vector vec,String term){
            if((vec.getTF(term))>0){
                return 1;
            }else{
                return 0;
            }             
	}
	
	public float idf(){
		return 0f;
	}
	
	public void stemming(){
	
	}
	
	public void stopWordRemoval(){
	
	}

	
}
