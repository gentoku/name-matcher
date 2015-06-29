package namematcher;

public class MatcherLevenshtein implements Matcher {

	/**
	 * Tolerable distance of LevenShtein and its setter. No need for getter.
	 */
	private final int threshold;
	
	/**
	 * Constructor sets threshold distance.
	 * @param threshold
	 */
	public MatcherLevenshtein (int threshold) {
		this.threshold = threshold;
	}
		
	/**
	 * Returns TRUE if the LevenShtein distance between two strings is less than
	 * threshold defined in this class. FALSE for an equal value.
	 * @param a
	 * @param b
	 * @return boolean
	 */
	@Override
	public boolean match(String a, String b) {
		return !(this.getLevenshteinDistance(a, b) > this.threshold);
	}

	/**
	 * Returns a LevenShtein distance between two words given. Farther means
	 * more different words. This is a copy from wikibooks.org.
	 * @param a
	 * @param b
	 * @return distance as float, it is likely to be integer though.
	 * @see http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
	 */
	public int getLevenshteinDistance (String a, String b) {                          
	    int lengthA = a.length() + 1;                                                     
	    int lengthB = b.length() + 1;                                                     
	 
	    // the array of distances                                                       
	    int[] cost = new int[lengthA];                                                     
	    int[] newCost = new int[lengthA];                                                  
	 
	    // initial cost of skipping prefix in String a                                 
	    for (int i = 0; i < lengthA; i++) cost[i] = i;                                     
	 
	    // dynamically computing the array of distances                                  
	 
	    // transformation cost for each letter in b                                    
	    for (int j = 1; j < lengthB; j++) {                                                
	        // initial cost of skipping prefix in String b                             
	        newCost[0] = j;                                                             
	 
	        // transformation cost for each letter in a                                
	        for(int i = 1; i < lengthA; i++) {                                             
	            // matching current letters in both strings                             
	            int match = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;             
	 
	            // computing cost for each transformation                               
	            int costReplace = cost[i - 1] + match;                                 
	            int costInsert  = cost[i] + 1;                                         
	            int costDelete  = newCost[i - 1] + 1;                                  
	 
	            // keep minimum cost                                                    
	            newCost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
	        }                                                                           
	 
	        // swap cost/newCost arrays                                                 
	        int[] swap = cost; cost = newCost; newCost = swap;                          
	    }                                                                               
	 
	    // the distance is the cost for transforming all letters in both strings        
	    return cost[lengthA - 1];                                                          
	}
	
	public static void main(String[] args) {
		String a = "Manchester United";
		String b = "Manchester City";
		MatcherLevenshtein ml = new MatcherLevenshtein(2);
		System.out.println(ml.getLevenshteinDistance(a, b) + " for " + a + ", " + b);
	}
}
