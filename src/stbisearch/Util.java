package stbisearch;

import java.io.IOException;
import static java.lang.Math.log;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cilvia
 */
public class Util {
	public List<Vector> docs;
	public List<Vector> queries;
	public List<Integer> judgeNoQuery;
	public List<Integer> judgeNoDoc;
	
	public void clear(){
		docs.clear();
		queries.clear();
		judgeNoQuery.clear();
		judgeNoDoc.clear();
	}
	
	public String readFile(String location){
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(location)));
		} catch (IOException ex) {
		}
		return content;
	}
	
	public List getVectors(String location){
		List vectors = new ArrayList<>();    
		String temp = readFile(location);
		int no;
		String title,author,content;		
		String state;
		for(String doc: temp.split(".I ")){
			if(doc.length()>0){
				title = "";
				author = "";
				content = "";
				state = "";

				no = Integer.parseInt(doc.substring(0,doc.indexOf("\n")));
				doc = doc.substring(doc.indexOf("\n")+1);

				for(String line: doc.split("\n")){
					switch(line){
						case ".A":
							state = "author";
							break;
						case ".T":
							state = "title"; 
							break;
						case ".W":
							state = "content";
							break;
						default:
							switch (state) {
								case "author":
									author += line;
									break;
								case "title":
									title += line;
									break;
								case "content":
									content += line;
									break;
							}
					}
				}
				
				vectors.add(new Vector(no,title,author,content));
			}
		}
		
		return vectors;
	}
	
	public void getDocuments(String location){
		docs = new ArrayList<>();
		docs = getVectors(location);
	}
	
	public void getQueries(String location){
		queries = new ArrayList<>();
		queries = getVectors(location);
	}
	
	public void getRelevanceJudgement(String location){
        judgeNoQuery = new ArrayList<>();
		judgeNoDoc = new ArrayList<>();
		String temp = readFile(location);
		String[] arr;
		for(String line: temp.split("\n")){
			arr = line.split("\\s+");
			judgeNoQuery.add(Integer.parseInt(arr[0]));
			judgeNoDoc.add(Integer.parseInt(arr[1]));
		}
	}
	
	public String getStopWords(String location) throws IOException{
            String content;
            content = new String(Files.readAllBytes(Paths.get(location)));
            return content;
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
	
	public double idf(String term){
		int count = 0;
		for(Vector doc: docs){
			if(doc.terms.contains(term)){
				count++;
			}
		}
		return log(docs.size()/count);
	}
	
	public void termWeighting(String methodTF, boolean bIdf, boolean bNormalize){
		//TF
		switch(methodTF){
			case "raw":
				break;
			case "log":
				break;
			case "binary":
				break;
			case "aug":
				break;
		}
		if(bIdf){
			
		}
		if(bNormalize){
		
		}
	}
	
	public void stemming(){
	
	}
	
	public void stopWordRemoval(){
	
	}

	public void printDocuments(){
		System.out.println("=========================");
		System.out.println("==  D O C U M E N T S  ==");
		System.out.println("=========================");
		for(Vector d: docs){
			d.printVector();
		}
	}
	
	public void printQueries(){
		System.out.println("=========================");
		System.out.println("==    Q U E R I E S    ==");
		System.out.println("=========================");
		for(Vector q: queries){
			q.printVector();
		}
	}
	
	public void printJudgement(){
		System.out.println("=========================");
		System.out.println("==  J U D G E M E N T  ==");
		System.out.println("=========================");
		for(int i=0;i<judgeNoDoc.size();i++){
			System.out.println(judgeNoQuery.get(i)+" "+judgeNoDoc.get(i));
		}
	}
}
