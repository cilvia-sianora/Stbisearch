package stbisearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Cilvia
 */
public class QueryProcess {
	private static double SIM_THRESHOLD = 0;
	private Util util;
	private InvertedFile invFile;
	private double precision, recall, niap; // to count the average final
	private int numRlvDocsRetrieved,numDocsRetrieved;
	
	private Map<Double,List<Integer>> resultSearch;
	private List<Double> precisionAtRlvDoc;
	
	// for relevance feedback
	private List<Integer> relevantDocs;
	private List<Integer> irrelevantDocs;
	
	// for configuration
	private String tfMethod;
	private boolean bIdf, bNormalization, bStemming;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		resultSearch = new TreeMap<>();
		precisionAtRlvDoc = new ArrayList<>();
		relevantDocs = new ArrayList<>();
		irrelevantDocs = new ArrayList<>();
		tfMethod = "no";
		bIdf = false;
		bNormalization = false;
		bStemming = false;
		precision = 0;
		recall = 0;
		niap = 0;
	}
	
	private void clear(){
		resultSearch.clear();
		precisionAtRlvDoc.clear();
		relevantDocs.clear();
		irrelevantDocs.clear();
	}
	
	// return true if document docNo is relevant for query queryNo
	private boolean isRelevantDoc(int queryNo, int docNo){
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if(temp!=null){
			return temp.contains(docNo);
		} else { // if query queryNo has no relevant document
			return false;
		}
	}
	
	// return number of relevant documents of a query
	// return -1 if there is no relevant document
	private int countAllRelevantDocs(int queryNo){
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if(temp!=null){
			return temp.size();
		} else {
			return -1;
		}
	}
	
	// return true if query queryNo has relevant document
	private boolean doesHaveRelevantDoc(int queryNo){
		return (countAllRelevantDocs(queryNo) != -1);
	}
	
	// compute recall
	private double getRecall(int totalRlvDocs){
		return (double)numRlvDocsRetrieved/(double)totalRlvDocs;
	}
	
	//compute precision
	private double getPrecision(){
		return (double)numRlvDocsRetrieved/(double)numDocsRetrieved;
	}
	
	// compute Non-Interpolated Average Precision
	private double getNIAP(int totalRlvDocs){
		double sum = 0;
		for(double p: precisionAtRlvDoc){
			sum += p;
		}
		return sum/(double)totalRlvDocs;
	}
	
	// print the rank of document
	private String getDocResult(int queryNo){
		numRlvDocsRetrieved = 0;
		numDocsRetrieved = 0;
		
		String result = "";
		result += "Query Number = " + queryNo + "\n";
		result += "Result:\n";
		
		for(Entry<Double,List<Integer>> entry: resultSearch.entrySet()){
			for(Integer docNo: entry.getValue()){
				numDocsRetrieved++;
				
				// print document retrieved
				result += numDocsRetrieved + ". " + docNo + 
						" " + util.docs.get(docNo).title + "\n";
				
				// get precision if document retrieved is relevant
				if(isRelevantDoc(queryNo,docNo)){
					numRlvDocsRetrieved++;
					precisionAtRlvDoc.add(getPrecision());
				}
			}
		}
		
		return result;
	}
	
	//print the result of experiment judgement
	private String judgeRelevance(int queryNo){
		String result = "";

		// count number of total relevant documents of query
		int totalRlvDocs = countAllRelevantDocs(queryNo);
		
		// print precision, recall, NIAP
		result += "Precision = " + getPrecision() + "\n";
		result += "Recall = " + getRecall(totalRlvDocs) + "\n";
		result += "NIAP = " + getNIAP(totalRlvDocs) + "\n";
		
		// add to batch to count the average final
		precision += getPrecision();
		recall += getRecall(totalRlvDocs);
		niap += getNIAP(totalRlvDocs);
		
		return result;
	}
	
	// count similarity between query and document
	private double similarity(Vector query, Vector doc){
		double sum = 0;
		for(Entry<String,double[]> entry: query.terms.entrySet()){
			sum += entry.getValue()[1] * invFile.getWeight(entry.getKey(), doc.no);
		}
		return sum;
	}
	
	// do searching for 1 query
	private String search(Vector query){
		double result;
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			result = similarity(query,doc.getValue());
			if(result > SIM_THRESHOLD){
				if(!resultSearch.containsKey(result)){
					resultSearch.put(result, new ArrayList<>());
				}
				resultSearch.get(result).add(doc.getKey());
			}
		}
		
		return getDocResult(query.no);
	}
	
	public String searchInteractive(String queryInput, String locStopwords, String locDocuments){
		String result;

		// read inverted file
		invFile.read();
		
		// read idf file
		util.getIdfFile(invFile.getIdfLocation());
		
		// load documents
		util.getDocuments(locDocuments);
		
		// do preprocessing for documents
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}
		
		// get query input
		Vector query = new Vector();
		query.content = queryInput;
		
		// do preprocessing for query
		query.preProcessed(locStopwords,bStemming);
		
		// do term-weighting for query
		util.termWeighting(query,tfMethod,bIdf,bNormalization);
		
		// do searching
		result = search(query);
		result = result.substring(result.indexOf("\n")); // to delete query number line
		
		// clear the data
		clear();
		
		return result;
	}
	
	public String searchExperiment(String locRlvJudge, String locQueries, String locStopwords, String locDocuments){
		precision = 0;
		recall = 0;
		niap = 0;

		// get files
		util.getQueries(locQueries);
		util.getRelevanceJudgement(locRlvJudge);
		util.getDocuments(locDocuments);
		
		// do preprocessing
		System.out.println("preprocessing..");
		for(Entry<Integer,Vector> doc: util.docs.entrySet()){
			doc.getValue().preProcessed(locStopwords,bStemming);
		}
		for(Entry<Integer,Vector> query: util.queries.entrySet()){
			query.getValue().preProcessed(locStopwords,bStemming);
		}
		
		// read inverted file
		System.out.println("getting inverted file..");
		invFile.read();
		
		// read idf file
		util.getIdfFile(invFile.getIdfLocation());
		
		// do searching for each query
		System.out.println("searching..");
		String result = "";
		for(Entry<Integer,Vector> query: util.queries.entrySet()){
			if(doesHaveRelevantDoc(query.getKey())){
				// do term-weighting for query
				util.termWeighting(query.getValue(),tfMethod,bIdf,bNormalization);
				
				// searching
				result += search(query.getValue());
				
				// get relevance judgement result
				result += judgeRelevance(query.getValue().no);
				
				// clear the data
				clear();
			}
		}
		
		// count the average final
		result = "Precision = " + (precision/util.queries.size()) + "\n" +
				"Recall = " + (recall/util.queries.size()) + "\n" +
				"NIAP = " + (niap/util.queries.size()) + "\n" +
				"The result of each query:" + "\n" + result;
		
		return result;
	}
	
	// set the configuration for query
	public void setQuerySetting(String tfMethod, boolean bIdf, 
			boolean bNormalization, boolean bStemming){
		this.tfMethod = tfMethod;
		this.bIdf = bIdf;
		this.bNormalization = bNormalization;
		this.bStemming = bStemming;
	}
	
	/** BAGIAN FELI **/
	
	/**
	 * Menghitung bobot term pada query menggunakan metode rocchio
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double rocchio(Vector query, String term){
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		// countWeightIrrelevantDoc(term)
		return 0;
	}
	
	/**
	 * Menghitung bobot term pada query menggunakan metode ide reguler
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double ideReguler(Vector query, String term){
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		// countWeightIrrelevantDoc(term)
		return 0;
	}
	
	/**
	 * Menghitung bobot term pada query menggunakan metode dec hi
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double decHi(Vector query, String term){
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		return 0;
	}
	
	/**
	 * Menghitung jumlah bobot term pada dokumen-dokumen relevan
	 * @param term
	 * @return total bobot term
	 */
	double countWeightRelevantDoc(String term){
	
		return 0;
	}
	
	/**
	 * Menghitung jumlah bobot term pada dokumen-dokumen irrelevant
	 * @param term
	 * @return total bobot term
	 */
	double countWeightIrrelevantDoc(String term){
		
		return 0;
	}

	/**
	 * Menghapus dokumen yang pernah di-retrieve pada document collections
	 * Jika user menginginkan tidak menggunakan document collections yang sama
	 * Prereq: !bSameDocs
	 */
	void deleteDocs(){
		// delete docs dari searchResult
	}
	
	/**
	 * Menentukan dokumen mana yang relevan dan tidak 
	 * dari dokumen-dokumen yang sudah di-retrieve
	 */
	void determineRelevantDocs(){
		// isi relevantDocs dan irrelevantDocs dari nentuin searchResult
	}
	
	/** BAGIAN FELI - END **/
}
