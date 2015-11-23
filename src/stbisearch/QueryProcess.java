package stbisearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	public Util util;
	public InvertedFile invFile;
	public double precision, recall, niap; // to count the average final
	private int numRlvDocsRetrieved,numDocsRetrieved;
	public Vector query;
	
	private Map<Double,List<Integer>> resultSearch;
	private List<Double> precisionAtRlvDoc;

	// for relevance feedback
	private List<Integer> relevantDocs;
	private List<Integer> irrelevantDocs;

	// for configuration
//	private String tfMethod;
//	private boolean bIdf, bNormalization, bStemming;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		resultSearch = new TreeMap<>(Collections.reverseOrder());
		precisionAtRlvDoc = new ArrayList<>();
		relevantDocs = new ArrayList<>();
		irrelevantDocs = new ArrayList<>();
		query = new Vector();
//		tfMethod = "no";
//		bIdf = false;
//		bNormalization = false;
//		bStemming = false;
		precision = 0;
		recall = 0;
		niap = 0;
	}

	private void clear() {
		resultSearch.clear();
		precisionAtRlvDoc.clear();
		relevantDocs.clear();
		irrelevantDocs.clear();
	}

	// return true if document docNo is relevant for query queryNo
	private boolean isRelevantDoc(int queryNo, int docNo) {
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if (temp != null) {
			return temp.contains(docNo);
		} else { // if query queryNo has no relevant document
			return false;
		}
	}

	// return number of relevant documents of a query
	// return -1 if there is no relevant document
	private int countAllRelevantDocs(int queryNo) {
		Set<Integer> temp = util.rlvJudgement.get(queryNo);
		if (temp != null) {
			return temp.size();
		} else {
			return -1;
		}
	}

	// return true if query queryNo has relevant document
	public boolean doesHaveRelevantDoc(int queryNo){
		return (countAllRelevantDocs(queryNo) != -1);
	}

	// compute recall
	private double getRecall(int totalRlvDocs) {
		return (double) numRlvDocsRetrieved / (double) totalRlvDocs;
	}

	//compute precision
	private double getPrecision(){
		if(numDocsRetrieved > 0){
			return (double)numRlvDocsRetrieved/(double)numDocsRetrieved;
		} else {
			return 0;
		}
	}

	// compute Non-Interpolated Average Precision
	private double getNIAP(int totalRlvDocs) {
		double sum = 0;
		for (double p : precisionAtRlvDoc) {
			sum += p;
		}
		return sum / (double) totalRlvDocs;
	}

	// print the rank of document
	private String getDocResult(int queryNo) {
		numRlvDocsRetrieved = 0;
		numDocsRetrieved = 0;

		String result = "";
		result += "Query Number = " + queryNo + "\n";
		result += "Result:\n";

		for (Entry<Double, List<Integer>> entry : resultSearch.entrySet()) {
			for (Integer docNo : entry.getValue()) {
				numDocsRetrieved++;

				// print document retrieved
				result += numDocsRetrieved + ". " + entry.getKey() + " - "
				    + docNo + " " + util.docs.get(docNo).title + "\n";

				// get precision if document retrieved is relevant
				if (isRelevantDoc(queryNo, docNo)) {
					numRlvDocsRetrieved++;
					precisionAtRlvDoc.add(getPrecision());
				}
			}
		}

		return result;
	}

	//print the result of experiment judgement
	public String judgeRelevance(){
		String result = "";

		// count number of total relevant documents of query
		int totalRlvDocs = countAllRelevantDocs(query.no);

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
	private double similarity(Vector query, Vector doc) {
		double sum = 0;
		for (Entry<String, double[]> entry : query.terms.entrySet()) {
			sum += entry.getValue()[1] * invFile.getWeight(entry.getKey(), doc.no);
		}
		return sum;
	}

	// do searching for 1 query
	public String search(){
		double result;
		for (Entry<Integer, Vector> doc : util.docs.entrySet()) {
			result = similarity(query, doc.getValue());
			if (result > SIM_THRESHOLD) {
				if (!resultSearch.containsKey(result)) {
					resultSearch.put(result, new ArrayList<>());
				}
				resultSearch.get(result).add(doc.getKey());
			}
		}

		return getDocResult(query.no);
	}
	
	/** BAGIAN FELI **/
	
	void reweighting(boolean bQueryExpansion, String algo){
		
		// itung ulang weight berdasarkan algo
		
		if(bQueryExpansion){ // if using query expansion
			// iterasi seluruh term dari inverted file
			// update weight tiap term dari vector query
		} else {
			// iterasi seluruh term dari term yang ada di vector query
			// update weight tiap term dari vector query
		}
	}
	
	/**
	 * Menghitung bobot term pada query menggunakan metode rocchio
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double rocchio(Vector query, String term) {
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		// countWeightIrrelevantDoc(term)

		return query.terms.get(term)[1] + (this.countWeightRelevantDoc(term)/relevantDocs.size()) - (this.countWeightIrrelevantDoc(term, irrelevantDocs.size())/irrelevantDocs.size());
	}

	/**
	 * Menghitung bobot term pada query menggunakan metode ide reguler
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double ideReguler(Vector query, String term) {
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		// countWeightIrrelevantDoc(term)
		return query.terms.get(term)[1] + this.countWeightRelevantDoc(term) - this.countWeightIrrelevantDoc(term, irrelevantDocs.size());
	}

	/**
	 * Menghitung bobot term pada query menggunakan metode dec hi
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double decHi(Vector query, String term) {
		// ngitungnya ada pake:
		// countWeightRelevantDoc(term)
		return query.terms.get(term)[1] + this.countWeightRelevantDoc(term) - this.countWeightIrrelevantDoc(term, 1);
	}

	/**
	 * Menghitung jumlah bobot term pada dokumen-dokumen relevan
	 *
	 * @param term
	 * @return total bobot term
	 */
	double countWeightRelevantDoc(String term) {
		double freq = 0;
		for (int i : relevantDocs) {
			freq += util.docs.get(i).terms.get(term)[1];
		}

		return freq;
	}

	/**
	 * Menghitung jumlah bobot term pada dokumen-dokumen irrelevant
	 *
	 * @param term
	 * @return total bobot term
	 */
	double countWeightIrrelevantDoc(String term, int topN) {
		double freq = 0;
		
		for (int i = 0; i < topN; i++) {
			freq += util.docs.get(irrelevantDocs.get(i)).terms.get(term)[1];
		}

		return freq;
	}

	/**
	 * Menghapus dokumen yang pernah di-retrieve pada document collections
	 * Jika user menginginkan tidak menggunakan document collections yang sama
	 * Prereq: !bSameDocs
	 */
	void deleteDocs() {
		// delete docs dari resultSearch
		Collection<List<Integer>> values = resultSearch.values();
		for (List<Integer> list : values) {
			for (int i : list) {
				util.docs.remove(i);
			}
		}
	}

	/**
	 * Menentukan dokumen mana yang relevan dan tidak dari dokumen-dokumen
	 * yang sudah di-retrieve. Untuk experimental query!
	 */
	void determineRelevantDocs(int noQuery) {
		// isi relevantDocs dan irrelevantDocs dari nentuin resultSearch
		//testing
//		this.tfMethod = "raw";
		util.getRelevanceJudgement("Test Collection\\ADI\\qrels.text");
		System.out.println(util.docs.size());
		util.getDocuments("Test Collection\\ADI\\adi.all");
		util.getQueries("Test Collection\\ADI\\query.text");
		System.out.println(util.docs.size());

		resultSearch.put(0.5, new ArrayList<>());
		for (int i = 1; i < 11; i++) {
			resultSearch.get(0.5).add(i);
		}

		//isi
		Collection<List<Integer>> values = resultSearch.values();
		for (List<Integer> list : values) {
			for (int i : list) {
				if (this.isRelevantDoc(noQuery, i)) {
					if (!relevantDocs.contains(i)) {
						relevantDocs.add(i);
					}
				} else {
					if (!irrelevantDocs.contains(i)) {
						irrelevantDocs.add(i);
					}
				}

			}
		}
		System.out.println("rel: ");
		for (int i : relevantDocs) {
			System.out.print(i + " ");
		}
		System.out.print("\nirrel: ");
		for (int i : irrelevantDocs) {
			System.out.print(i + " ");
		}

	}

	/**
	 * BAGIAN FELI - END *
	 */
}
