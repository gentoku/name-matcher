package namematcher;

@Deprecated
public class MatcherMetaphone3 implements Matcher {

	public MatcherMetaphone3 (int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * MetaPhone will analyze a word up to this length.
	 */
	private static final int MAX_LENGTH = 64;
	
	/**
	 * Tolerable distance of LevenShtein and its setter.
	 */
	private int threshold = 1;

	public void setThreshold (int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Returns TRUE if the LevenShtein distance between two Metaphone3 is less
	 * than threshold defined in the class. 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	@Override
	public boolean match(String a, String b) {
		a = this.getMetaphone3(a);
		b = this.getMetaphone3(b);
		Matcher m = new MatcherLevenshtein(this.threshold);
		return m.match(a, b);
	}

	/**
	 * Returns a converted value by Metaphone3  algorithm.
	 * @param text
	 * @return value as string with upper cases
	 */
	public String getMetaphone3 (String text) {
		Metaphone3 m3 = new Metaphone3();
		m3.SetWord(text);
		m3.SetKeyLength(MAX_LENGTH);
		m3.Encode();
		m3.SetEncodeExact(true);
		m3.SetEncodeVowels(true);
		return m3.GetMetaph();
		//m3.GetAlternateMetaph());
	}

	public static void main(String[] args) {
		String a = "Manchester United";
		String b = "Manchester City";
		MatcherMetaphone3 mm3 = new MatcherMetaphone3(1);
		System.out.println(mm3.match(a, b) + " for " + a + ", " + b);
		// returns wrong answer when threshold is set at 2+.
		mm3.setThreshold(0);
		System.out.println(mm3.match(a, b) + " for " + a + ", " + b);
		// covers spelling mistake.
		a = "Leicester";
		b = "Laicester";
		mm3.setThreshold(0);
		System.out.println(mm3.match(a, b) + " for " + a + ", " + b);
		
		a = "Manchestar United";
		b = "Manchester United";
		mm3.setThreshold(0);
		System.out.println(mm3.match(a, b) + " for " + a + ", " + b);

	}
}
