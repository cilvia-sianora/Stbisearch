package stbisearch;

/**
 *
 * @author 
 */
public class Vector {
	public String[] terms;
	public int[] tf;
	public float[] weight;
	
	// get from raw document
	public Vector(String doc){
		
	}
	
	public void normalization(){
		
	}
	
	public int getTF(String term){
		boolean found = false;
		int i = 0;
		while(!found && i<terms.length){
			if(terms[i].equals(term)){
				found = true;
			} else {
				i++;
			}
		}
		if(found){
			return tf[i];
		} else {
			return 0;
		}
	}
        
        public int getMaxTF(){
            int max=0;
            for(int i = 0 ;i<tf.length;i++){
                if(tf[i]>max){
                    max = tf[i];
                }
            }
            return max;
        }
	
	public float getLength(){
		return 0f;
	}
}
