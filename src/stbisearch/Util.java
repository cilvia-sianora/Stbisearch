package stbisearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.log10;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.martin.Stemmer;

/**
 *
 * @author Cilvia
 */
public class Util {
//	private InvertedFile invFile;
	public Map<String,Double> idfFile;
	public Map<Integer,Vector> docs;
	public Map<Integer,Vector> queries;
	public Map<Integer,Set<Integer>> rlvJudgement;
	
	public Util(){
//		invFile = new InvertedFile();
		docs = new HashMap<>();
		queries = new HashMap<>();
		rlvJudgement = new HashMap<>();
		idfFile = new HashMap<>();
	}
	
	public void clear(){
		docs.clear();
		queries.clear();
		rlvJudgement.clear();
	}
	
	// return content of file
	public String readFilePerLine(String location){
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
	
	public List<String> readFile(String location){
		List<String> temp = new ArrayList<>();
		try {
			temp =  Files.readAllLines(Paths.get(location));
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return temp;
	}
	
	// read documents/queries from file
	private Map getVectors(String location){
		Map vectors = new HashMap<>();    
		int no = -1;
		String title = "";
		String author = "";
		String content = "";
		String state = "";
		
		List<String> temp = readFile(location);
		
//		for(String doc: temp.split("\\.I ")){
//			if(doc.length()>0){
//				title = "";
//				author = "";
//				content = "";
//				state = "number";
//				no = 0;
//				
//				for(String line: doc.split("\n")){
				for(String line: temp){
						if(line.equals(".A"))
							state = "author";
						else if(line.equals(".T"))
							state = "title"; 
						else if(line.equals(".W"))
							state = "content";
						else if(line.equals(".X"))
							state = "";
						else if(line.equals(".B"))
							state = "";
						else if(line.startsWith(".I")){
							if(no != -1){
								vectors.put(no, new Vector(no,title,author,content));
							}
							title = "";
							author = "";
							content = "";
							state = "";
							line = line.substring(line.indexOf(" ")+1);
							no = Integer.parseInt(line);
						} else {
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
				
//			}
//		}
		
		
		return vectors;
	}
	
	public void getIdfFile(String idfLocation){
		idfFile.clear();
//		idfFile = invFile.readIdf();
		
		String term;
		double idf;
		String[] arr;
		
		// get the inverted file
		List<String> content = readFile(idfLocation);
		
		// parse the file into lines
//		for(String line: content.split("\n")){
		for(String line: content){
			arr = line.split(" ");
			
			term = arr[0];
			idf = Double.parseDouble(arr[1]);
			
			idfFile.put(term,idf);
		}
	}
	
	/**
	 * Mengambil daftar dokumen dari file
	 * @param location lokasi dokumen
	 */
	public void getDocuments(String location){
		System.out.println("getting documents..");
		docs.clear();
		docs = getVectors(location);
	}
	
	/**
	 * Mengambil daftar query dari file
	 * @param location lokasi query
	 */
	public void getQueries(String location){
		System.out.println("getting queries..");
		queries.clear();
		queries = getVectors(location);
	}
	
	/**
	 * Mengambil relevance judgement dari file
	 * @param location lokasi file
	 */
	public void getRelevanceJudgement(String location){
		System.out.println("getting relevance judgement..");
		rlvJudgement.clear();
		List<String> temp = readFile(location);
		
		String[] arr;
		int queryNo, docNo;
//		for(String line: temp.split("\n")){
		for(String line: temp){
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
		
	/**
	 * Menghitung bobot suatu term menggunakan metode raw TF
	 * @param vec query/dokumen
	 * @param term term
	 * @return besar bobot term
	 */
	public double rawTF(Vector vec, String term){
            if(vec.getTF(term)!= -1){
                return (double)vec.getTF(term);
            } else {
                return 0;
            }
	}
	
	/**
	 * Menghitung bobot suatu term menggunakan metode logarithmic TF
	 * @param vec query/dokumen
	 * @param term term
	 * @return besar bobot term
	 */
	public double logTF(Vector vec, String term){
            if(vec.getTF(term)!= -1){
                return 1+log10(vec.getTF(term));
            } else {
                return 0;
            }
        }
	
	/**
	 * Menghitung bobot suatu term menggunakan metode augmented TF
	 * @param vec query/dokumen
	 * @param term term
	 * @return besar bobot term
	 */
	public double augTF(Vector vec,String term){
            if(vec.getTF(term)!= -1){
                 return (0.5+(0.5*(double)vec.getTF(term)/(double)vec.getMaxTF()));
            } else {
                return 0;
            }

	}
	
	/**
	 * Menghitung bobot suatu term menggunakan metode binary TF
	 * @param vec query/dokumen
	 * @param term term
	 * @return besar bobot term
	 */
	public double binaryTF(Vector vec,String term){
		if((vec.getTF(term))>0){
			return 1;
		}else{
			return 0;
		}             
	}
	
	// computing term-weighting method: idf
	public double idf(String term, boolean searchProcess){
		if(!searchProcess){
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
		} else {
			if(idfFile.containsKey(term)){
				return idfFile.get(term);
			} else {
				return 0;
			}
		}
	}
	
	/**
	 * Menghitung idf berdasarkan masukan
	 * @param totalDoc jumlah total dokumen
	 * @param numDoc jumlah dokumen yang mengandung suatu term
	 * @return nilai idf dari masukan
	 */
	public double computeIdf(int totalDoc, int numDoc){
		if(numDoc > 0){
			return log10((double)totalDoc/(double)numDoc);
		} else {
			return 0;
		}
	}
	
	/**
	 * Melakukan term-weighting terhadap query/document berdasarkan konfigurasi masukan
	 * @param vec	query/dokumen
	 * @param methodTF	metode term frequency yang digunakan
	 * @param bIdf	idf or not
	 * @param bNormalize normalization or not
	 */
	public void termWeighting(Vector vec, String methodTF, boolean bIdf, boolean bNormalize, boolean searchProcess){
		for(Entry<String,double[]> entry: vec.terms.entrySet()){
			switch(methodTF){
				case "raw":
					entry.getValue()[1] *= rawTF(vec,entry.getKey());
					break;
				case "log":
					entry.getValue()[1] *= logTF(vec,entry.getKey());
					break;
				case "binary":
					entry.getValue()[1] *= binaryTF(vec,entry.getKey());
					break;
				case "aug":
					entry.getValue()[1] *= augTF(vec,entry.getKey());
					break;
			}
			if(bIdf){
				entry.getValue()[1] *= idf(entry.getKey(),searchProcess);
			}
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
	  String stopString = readFilePerLine(location);
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
	
	public void printIdf(){
		for(Entry<String,Double> entry1: idfFile.entrySet()){
			System.out.println(entry1.getKey()+" "+entry1.getValue());
		}
	}
}
