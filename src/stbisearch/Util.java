package stbisearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.log10;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import org.tartarus.martin.Stemmer;

/**
 *
 * @author Cilvia
 */
public class Util {
	public Map<Integer,Vector> docs;
	public Map<Integer,Vector> queries;
	public Map<Integer,Set<Integer>> rlvJudgement;
	
	public void clear(){
		docs.clear();
		queries.clear();
		rlvJudgement.clear();
	}
	
	// return content of file
	public String readFile(String location){
		String content = "";
		String temp;
                try {                
                    Scanner in = new Scanner(new File(location));
                    while(in.hasNextLine()){
						temp = in.nextLine();
                        content += temp + "\n";
                    }
                } catch (FileNotFoundException ex) {
                }
        	return content;
        }
	
	// read documents/queries from file
	private Map getVectors(String location){
		Map vectors = new HashMap<>();    
		int no;
		String title,author,content,state;
		
		String temp = readFile(location);
		for(String doc: temp.split("\\.I ")){
			if(doc.length()>0){
				title = "";
				author = "";
				content = "";
				state = "number";
				no = 0;
				
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
						case ".X":
							state = "";
							break;
						case ".B":
							state = "";
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
								case "number" :
									no = Integer.parseInt(line);
									break;
							}
					}
				}
				
				vectors.put(no, new Vector(no,title,author,content));
			}
		}
		
		return vectors;
	}
	
	// get documents from file
	public void getDocuments(String location){
		System.out.println("getting documents..");
		docs = new HashMap<>();
		docs = getVectors(location);
	}
	
	// get queries from file
	public void getQueries(String location){
		System.out.println("getting queries..");
		queries = new HashMap<>();
		queries = getVectors(location);
	}
	
	// get relevance judgement from file
	public void getRelevanceJudgement(String location){
		System.out.println("getting relevance judgement..");
		rlvJudgement = new HashMap<>();
		String temp = readFile(location);
		
		String[] arr;
		int queryNo, docNo;
		for(String line: temp.split("\n")){
			if(line.length()>0){
				arr = line.split("\\s+");
				queryNo = Integer.parseInt(arr[0]);
				docNo = Integer.parseInt(arr[1]);
				if(!rlvJudgement.containsKey(queryNo)){
					rlvJudgement.put(queryNo, new HashSet<>());
				}
				rlvJudgement.get(queryNo).add(docNo);
			}
		}
	}
		
	// computing term-weighting method: rawTF
	public double rawTF(Vector vec, String term){
            if(vec.getTF(term)!= -1){
                return (double)vec.getTF(term);
            } else {
                return 0;
            }
	}
	
	// computing term-weighting method: logarithmTF
	public double logTF(Vector vec, String term){
            if(vec.getTF(term)!= -1){
                return 1+log10(vec.getTF(term));
            } else {
                return 0;
            }
        }
	
	// computing term-weighting method: augmentedTF
	public double augTF(Vector vec,String term){
            if(vec.getTF(term)!= -1){
                 return (0.5+(0.5*(double)vec.getTF(term)/(double)vec.getMaxTF()));
            } else {
                return 0;
            }

	}
	
	// computing term-weighting method: binaryTF
	public double binaryTF(Vector vec,String term){
		if((vec.getTF(term))>0){
			return 1;
		}else{
			return 0;
		}             
	}
	
	// computing term-weighting method: idf
	public double idf(String term){
		int count = 0;
		for(Entry<Integer,Vector> entry : docs.entrySet()) {
			if(entry.getValue().terms.containsKey(term)){
				count++;
			}
		}
		if(count > 0){
			return log10((double)docs.size()/(double)count);
		} else {
			return 0;
		}
	}
	
	// do term-weighting
	public void termWeighting(Vector vec, String methodTF, boolean bIdf, boolean bNormalize){
		double[] temp = new double[2];
		for(Entry<String,double[]> entry: vec.terms.entrySet()){
			temp[0] = entry.getValue()[0];
			temp[1] = entry.getValue()[1];
			switch(methodTF){
				case "raw":
					temp[1] *= rawTF(vec,entry.getKey());
					break;
				case "log":
					temp[1] *= logTF(vec,entry.getKey());
					break;
				case "binary":
					temp[1] *= binaryTF(vec,entry.getKey());
					break;
				case "aug":
					temp[1] *= augTF(vec,entry.getKey());
					break;
			}
			if(bIdf){
				temp[1] *= idf(entry.getKey());
			}
			vec.terms.put(entry.getKey(), temp);
		}
		if(bNormalize){
			vec.normalization();
		}
	}
	
	
    /**
     * @param @Document
     * Stemming setiap kata dari masukan
    **/
    public String stemming(String Document) {
	  String result = "";
	  String[] DocArr = Document.split(" ");
	  for (String D:DocArr){
		Stemmer stemmer = new Stemmer();
	
		if (!(D.isEmpty() | D.length() < 1 )){
			stemmer.add(D.toCharArray(), D.length());
			stemmer.stem();
			
			String temp = new String(stemmer.toString());
//		    String temp = new String(stemmer.getResultBuffer());
		    result += " " + temp;
//			result += " " + D;
		}
	  }
	  return result;
    }

    /**
     * @param @Documents
     * Hapus simbol-simbol dari dokumen masukan.
    **/
    public String delimiter(String Document) throws IOException{
	  String delimited = " " + Document;
	  String[] delimiters = {".", "/", "\\", "'t", "'d", "'s", "\"", "\'", ",", "!", "?", "(", ")", "-", "+", "=", "*", ":", ";", "\n"};
	  
	  for (String d: delimiters){
		delimited = delimited.replace(d, " ");
	  }
	  return delimited;
    }
    
    /**
     * @param @Documents
     * Hapus stopwords dari dokumen masukan.
    **/
    public String stopWordRemoval(String location, String Document) throws IOException {
	  String stemmed = Document;

	  // loads stopwordlist 
	  String stopString = readFile(location);
	  String[] stopList = stopString.split("\n");

	  for (String stopword : stopList) {
		if (Document.contains(stopword)) {
		    stemmed = stemmed.replaceAll(" " + stopword + " ", " ");
		}
	  }
	  return stemmed;
    }


	public void printDocuments(){
		System.out.println("=========================");
		System.out.println("==  D O C U M E N T S  ==");
		System.out.println("=========================");
		for(Entry<Integer,Vector> entry : docs.entrySet()) {
			entry.getValue().printVector();
		}
	}
	
	public void printQueries(){
		System.out.println("=========================");
		System.out.println("==    Q U E R I E S    ==");
		System.out.println("=========================");
		for(Entry<Integer,Vector> entry : queries.entrySet()) {
			entry.getValue().printVector();
		}
	}
	
	public void printJudgement(){
		System.out.println("=========================");
		System.out.println("==  J U D G E M E N T  ==");
		System.out.println("=========================");
		for(Entry<Integer,Set<Integer>> entry : rlvJudgement.entrySet()) {
			for(Integer d: entry.getValue()){
				System.out.println(entry.getKey()+" "+d);
			}
		}
	}
}
