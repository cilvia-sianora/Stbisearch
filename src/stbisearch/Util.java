package stbisearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.log10;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.tartarus.martin.Stemmer;

/**
 *
 * @author Cilvia
 */
public class Util {
	public List<Vector> docs;
	public List<Vector> queries;
	public List<Integer> relevantNoQuery;
	public List<Integer> relevantNoDoc;
	
	public void clear(){
		docs.clear();
		queries.clear();
		relevantNoQuery.clear();
		relevantNoDoc.clear();
	}
	
	// return content of file
	public String readFile(String location){
		String content = "";
                try {                
                    Scanner in = new Scanner(new File(location));
                    while(in.hasNextLine()){
                        content += in.nextLine() + "\n";
                    }
                } catch (FileNotFoundException ex) {
                }
        	return content;
        }
	
	// read documents/queries from file
	private List getVectors(String location){
		List vectors = new ArrayList<>();    
		int no;
		String title,author,content,state;
		
		String temp = readFile(location);
		for(String doc: temp.split(".I ")){
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
				
				vectors.add(new Vector(no,title,author,content));
			}
		}
		
		return vectors;
	}
	
	// get documents from file
	public void getDocuments(String location){
		docs = new ArrayList<>();
		docs = getVectors(location);
	}
	
	// get queries from file
	public void getQueries(String location){
		queries = new ArrayList<>();
		queries = getVectors(location);
	}
	
	// get relevance judgement from file
	public void getRelevanceJudgement(String location){
        relevantNoQuery = new ArrayList<>();
		relevantNoDoc = new ArrayList<>();
		String temp = readFile(location);
		String[] arr;
		for(String line: temp.split("\n")){
			if(line.length()>0){
				arr = line.split("\\s+");
				relevantNoQuery.add(Integer.parseInt(arr[0]));
				relevantNoDoc.add(Integer.parseInt(arr[1]));
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
		for(Vector doc: docs){
			if(doc.findIndexTerm(term) != -1){
				count++;
			}
		}
//		System.out.println("idf= "+(double)docs.size()+"/"+(double)count);
		if(count > 0){
			return log10((double)docs.size()/(double)count);
		} else {
			return 0;
		}
	}
	
	// do term-weighting
	public void termWeighting(Vector vec, String methodTF, boolean bIdf, boolean bNormalize){
//		System.out.println("-TERMWEIGHTING-");
		String term;
		for(Term t : vec.terms){
			term = t.getContent();
//			System.out.println(term);
			switch(methodTF){
				case "raw":
					vec.terms.get(vec.findIndexTerm(term)).setWeight(rawTF(vec,term));
					break;
				case "log":
					vec.terms.get(vec.findIndexTerm(term)).setWeight(logTF(vec,term));
					break;
				case "binary":
					vec.terms.get(vec.findIndexTerm(term)).setWeight(binaryTF(vec,term));
					break;
				case "aug":
					vec.terms.get(vec.findIndexTerm(term)).setWeight(augTF(vec,term));
					break;
				default:
					vec.terms.get(vec.findIndexTerm(term)).setWeight(1);
			}
//			System.out.println("tf= "+vec.terms.get(vec.findIndexTerm(term)).getWeight());
			if(bIdf){
				vec.terms.get(vec.findIndexTerm(term)).setWeight(vec.terms.get(vec.findIndexTerm(term)).getWeight()*idf(term));
//				System.out.println("idf= "+idf(term));
			}
//			System.out.println("tf-idf= "+vec.terms.get(vec.findIndexTerm(term)).getWeight());
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
		for(int i=0;i<relevantNoDoc.size();i++){
			System.out.println(relevantNoQuery.get(i)+" "+relevantNoDoc.get(i));
		}
	}
}
