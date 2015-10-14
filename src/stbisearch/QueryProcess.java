package stbisearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cilvia
 */
public class QueryProcess {
	private Util util;
	private InvertedFile invFile;
	private List<Integer> resultDocNo;
	private List<Double> resultSim;
	
	public QueryProcess(){
		util = new Util();
		invFile = new InvertedFile();
		invFile.read();
		resultDocNo = new ArrayList<>();
		resultSim = new ArrayList<>();
	}
	
	public void searchInteractive(String queryInput){
		Vector query = new Vector();
		query.content = queryInput;
		
		util.stemming();
		util.stopWordRemoval();
		query.countFreq();
		util.termWeighting(query,"raw", true, true);
	}
	
	public void searchExperiment(String locQueries){
		util.getQueries(locQueries);
		
		util.stemming();
		util.stopWordRemoval();
		for(Vector query: util.queries){
			query.countFreq();
			util.termWeighting(query,"raw", true, true);
		}
		
	}
	
	public void search(Vector query){
		double result;
		for(Vector doc: util.docs){
			result = query.similarity(doc);
			
		}
	}
	
	// find the right index to rank document for the result
	public int findIndex(Double sim){
		int i = 0;
		boolean found = false;
		
		if(resultSim.size()>0){
			// mencari index yang cocok berdasarkan keterurutan term
			while(!found && i<resultSim.size()){
				if(sim >= resultSim.get(i)){
					found = true;
				} else {
					i++;
				}
			}
		}
		
		return i;
	}
	
}
