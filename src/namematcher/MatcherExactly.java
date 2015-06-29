package namematcher;

public class MatcherExactly implements Matcher {
	
	/**
	 * Returns TRUE if two strings match exactly after converting to lower case and 
	 * removing every comma and white space. 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	@Override
	public boolean match(String a, String b) {
		return this.getNaked(a).equals(this.getNaked(b));
	}

	/**
	 * Converts the string to lower cases and removes every comma and white space.
	 * @param text
	 * @return string
	 */
	private String getNaked (String text) {
		text = text.toLowerCase();
		text = text.replaceAll("\\.", "");
		return text.replaceAll("\\s+", "");
	}
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "ManchesterUnited";
		String b = "manchester united";
		Matcher m = new MatcherExactly();
		System.out.println(m.match(a, b) + " for " + a + ", " + b);
	}
}
