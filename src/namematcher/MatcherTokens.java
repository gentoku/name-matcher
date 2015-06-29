package namematcher;

public class MatcherTokens implements Matcher {

	/**
	 * Returns TRUE if two text have at least a common word in literal or phonic way.
	 * 
	 * This should be as late in procedure as possible, after matchAbbrev in particular,
	 * because this returns TRUE for 'Manchester United' and 'Manchester City' with a
	 * common word of 'Manchester'. They have to be distinguished by other matchers first.
	 * 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	@Override
	public boolean match(String a, String b) {
		String[] aWords = this.split(a);
		String[] bWords = this.split(b);
		MatcherExactly me = new MatcherExactly();
		MatcherDoubleMetaphone mm = new MatcherDoubleMetaphone(0);
		for (String aWord : aWords) {
			for (String bWord : bWords) {
				if (me.match(aWord, bWord)
						|| mm.match(aWord, bWord)) return true;
			}
		}
		return false;
	}
	
	/**
	 * Splits the string by white space, a single one or continuous spaces ones, 
	 * as a separator after every comma converted into a white space.  
	 * @param text
	 * @return array of string
	 */
	private String[] split (String text) {
		text = text.replaceAll("\\.", " ");
		return text.split("\\s+", 0);
	}
	
	public static void main(String[] args) {
		String a = "Munich 1860";
		String b = "TSV 1860 Munchen";
		MatcherTokens mt = new MatcherTokens();
		System.out.println(mt.match(a, b) + " for " + a + ", " + b);
	}
}
