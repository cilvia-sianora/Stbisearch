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
		return 0;
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
