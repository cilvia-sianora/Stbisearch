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
	private int numRlvDocsRetrieved, numDocsRetrieved;
	public Vector query;

	private Map<Double, List<Integer>> resultSearch;
	private List<Double> precisionAtRlvDoc;

	// for relevance feedback
	public List<Integer> relevantDocs;
	public List<Integer> irrelevantDocs;

	// for configuration
//	private String tfMethod;
//	private boolean bIdf, bNormalization, bStemming;
	public QueryProcess() {
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
	public boolean doesHaveRelevantDoc(int queryNo) {
		return (countAllRelevantDocs(queryNo) != -1);
	}

	// compute recall
	private double getRecall(int totalRlvDocs) {
		return (double) numRlvDocsRetrieved / (double) totalRlvDocs;
	}

	//compute precision
	private double getPrecision() {
		if (numDocsRetrieved > 0) {
			return (double) numRlvDocsRetrieved / (double) numDocsRetrieved;
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
	private List<String> getDocResult(int queryNo) {
		numRlvDocsRetrieved = 0;
		numDocsRetrieved = 0;
		
		List<String> result = new ArrayList<>();
		result.add("Result:\n");

		for (Entry<Double, List<Integer>> entry : resultSearch.entrySet()) {
			for (Integer docNo : entry.getValue()) {
				numDocsRetrieved++;

				// print document retrieved
				result.add(numDocsRetrieved + ". " + entry.getKey() + " - "
				    + util.docs.get(docNo).title + " " + docNo + "\n");

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
	public String judgeRelevance() {
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
//			System.out.println("");
		}
		return sum;
	}

	// do searching for 1 query
	public List<String> search(int numRetrieved){
		double result;
		resultSearch.clear();
		Map<Double,List<Integer>> temp = new TreeMap<>(Collections.reverseOrder());
		for (Entry<Integer, Vector> doc : util.docs.entrySet()) {
			result = similarity(query, doc.getValue());
			if (result > SIM_THRESHOLD) {
				if (!temp.containsKey(result)) {
					temp.put(result, new ArrayList<>());
				}
				temp.get(result).add(doc.getKey());
			}
		}
		
		// ambil top numRetrieved dokumen
		if(numRetrieved != -1){
			int i = 0;
			for(Entry<Double,List<Integer>> entry: temp.entrySet()){
				if( i+entry.getValue().size() < numRetrieved){
					resultSearch.put(entry.getKey(), new ArrayList<>(entry.getValue()));
					i += entry.getValue().size();
				} else {
					resultSearch.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(0,numRetrieved-i)));
					break;
				}
			}
		} else {
			resultSearch.putAll(temp);
		}

		return getDocResult(query.no);
	}
	

	/**
	 * BAGIAN FELI *
	 */
	void reweighting(boolean bQueryExpansion, String algo) {
//		System.out.println("-REWEIGHTING-");
		Set<String> keySet = query.terms.keySet();
//		System.out.println(keySet.size());
		
		// itung ulang weight berdasarkan algo
		if (bQueryExpansion) { // if using query expansion
			// iterasi seluruh term dari inverted file
			// update weight tiap term dari vector query
			Set<String> allTerms = this.invFile.file.keySet();
			for (String aTerm : allTerms) {
				if (!keySet.contains(aTerm)) { // term yang gak ada di query
					//hitung w baru
					double res = 0;
					switch (algo) {
						case "rocchio":
							res = this.rocchio(aTerm);
						case "ide":
							res = this.ideReguler(aTerm);
						case "dechi":
							res = this.decHi(aTerm);
					}
					
					if (res > 0){ // masukin kalo weight lebih dari 0
						double[] resArr;
						resArr = new double[2];
						resArr[0] = 0; resArr[1] = res;
						query.terms.put(aTerm, resArr);
					}
				}	
			}

		}
		
		// if not using query expansion
		// iterasi seluruh term dari term yang ada di vector query
		// update weight tiap term dari vector query
		switch (algo) {
			case "rocchio":
				System.out.println("-rocchio-");
				for (String theTerm : keySet) {
//					System.out.println(theTerm+" "+rocchio(theTerm));
					if (rocchio(theTerm) > 0) // if weight result > 0
						query.terms.get(theTerm)[1] = rocchio(theTerm);
					else 
						query.terms.get(theTerm)[1] = 0;
				}
			case "ide":
				for (String theTerm : keySet) {
					if (ideReguler(theTerm) > 0)
						query.terms.get(theTerm)[1] = ideReguler(theTerm);
					else 
						query.terms.get(theTerm)[1] = 0;
				}
			case "dechi":
				for (String theTerm : keySet) {
					if (decHi(theTerm) > 0)
						query.terms.get(theTerm)[1] = decHi(theTerm);
					else 
						query.terms.get(theTerm)[1] = 0;
				}
		}
	}

	/**
	 * Menghitung bobot term pada query menggunakan metode rocchio
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double rocchio(String term) {
		double valueRlv;
		if(relevantDocs.isEmpty()){
			valueRlv = 0;
		} else {
			valueRlv = this.countWeightRelevantDoc(term) / relevantDocs.size();
		}
		
		double valueIrrlv;
		if(irrelevantDocs.isEmpty()){
			valueIrrlv = 0;
		} else {
			valueIrrlv = this.countWeightIrrelevantDoc(term, irrelevantDocs.size()) / irrelevantDocs.size();
		}
		
		double weightTerm;
		if(query.terms.containsKey(term)){
			weightTerm = query.terms.get(term)[1];
		} else {
			weightTerm = 0;
		}
		
		return weightTerm + valueRlv - valueIrrlv;
	}

	/**
	 * Menghitung bobot term pada query menggunakan metode ide reguler
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double ideReguler(String term) {
		double weightTerm;
		if(query.terms.containsKey(term)){
			weightTerm = query.terms.get(term)[1];
		} else {
			weightTerm = 0;
		}
		
		return weightTerm + this.countWeightRelevantDoc(term) - this.countWeightIrrelevantDoc(term, irrelevantDocs.size());
	}

	/**
	 * Menghitung bobot term pada query menggunakan metode dec hi
	 *
	 * @param query
	 * @param term
	 * @return bobot term yang baru
	 */
	double decHi(String term) {
		double weightTerm;
		if(query.terms.containsKey(term)){
			weightTerm = query.terms.get(term)[1];
		} else {
			weightTerm = 0;
		}
		
		return weightTerm + this.countWeightRelevantDoc(term) - this.countWeightIrrelevantDoc(term, 1);
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
			if (util.docs.get(i).terms.containsKey(term)) {
				freq += util.docs.get(i).terms.get(term)[1];
			}
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
		int count = topN;
		if (irrelevantDocs.size() < topN) {
			count = irrelevantDocs.size();
		}

		for (int i = 0; i < count; i++) {
			if (util.docs.get(irrelevantDocs.get(i)).terms.containsKey(term)) {
				freq += util.docs.get(irrelevantDocs.get(i)).terms.get(term)[1];
			}
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
	void determineRelevantDocs(int numTopRlvDocs) {
		// isi relevantDocs dan irrelevantDocs dari nentuin resultSearch
		//testing
//		this.tfMethod = "raw";
//		System.out.println(util.docs.size());
//		util.getRelevanceJudgement("Test Collection\\ADI\\qrels.text");
//		util.getDocuments("Test Collection\\ADI\\adi.all");
//		util.getQueries("Test Collection\\ADI\\query.text");
//		System.out.println(util.docs.size());
//
//		query = util.queries.get(noQuery);
//		util.termWeighting(query, "raw", true, false, false);
//
//		this.search();
//		
//		resultSearch.put(0.5, new ArrayList<>());
//		for (int i = 1; i < 20; i++) {
//			resultSearch.get(0.5).add(i);
//		}
		
		if(numTopRlvDocs == -1){
			//isi
			Collection<List<Integer>> values = resultSearch.values();
			for (List<Integer> list : values) {
				for (int i : list) {
					if (this.isRelevantDoc(query.no, i)) {
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
		} else { // pseudo
			int i = 0;
			for(Entry<Double,List<Integer>> entry: resultSearch.entrySet()){
				if( i+entry.getValue().size() < numTopRlvDocs){ // jika di entry masih bisa dimasukkan semua ke relevan
					relevantDocs.addAll(entry.getValue());
					i += entry.getValue().size();
				} else if (i < numTopRlvDocs){ // jika di entry sudah gak bisa dimasukkan semua ke relevan
					relevantDocs.addAll(entry.getValue().subList(0,numTopRlvDocs-i));
					break;
//					i += entry.getValue().size();
//					if(i > numTopRlvDocs){ // jika di entry masih ada sisa yang harusnya dimasukkan ke irrelevan
//						irrelevantDocs.addAll(entry.getValue().subList(numTopRlvDocs-i,entry.getValue().size()-1));
//					}
				} 
//				else { // jika sisa tinggal dimasukkan ke irrelevan
//					irrelevantDocs.addAll(entry.getValue());
//				}
			}
		}
		
		System.out.print("rel: ");
		for (int i : relevantDocs) {
			System.out.print(i + " ");
		}
		System.out.print("\nirrel: ");
		for (int i : irrelevantDocs) {
			System.out.print(i + " ");
		}
		System.out.println("");

	}

	/**
	 * BAGIAN FELI - END *
	 */
}
