package namematcher;

import org.apache.commons.codec.language.DoubleMetaphone;

public class MatcherDoubleMetaphone implements Matcher {

	/**
	 * Max length of metaphone code.
	 */
	private static final int MAX_LENGTH = 64;
	
	private final int threshold;
	
	public MatcherDoubleMetaphone (int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public boolean match(String a, String b) {
		DoubleMetaphone dm = new DoubleMetaphone();
		dm.setMaxCodeLen(MAX_LENGTH);
		if (this.threshold < 1) {
			return dm.isDoubleMetaphoneEqual(a, b);
		} else {
			Matcher m = new MatcherLevenshtein(this.threshold);
			return m.match(dm.doubleMetaphone(a), dm.doubleMetaphone(b));
		}
	}
	
	public static void main(String[] args) {
		String a;
		String b;
		String ma;
		String mb;
		MatcherDoubleMetaphone mdm0 = new MatcherDoubleMetaphone(0);
		MatcherDoubleMetaphone mdm1 = new MatcherDoubleMetaphone(1);
		DoubleMetaphone dm = new DoubleMetaphone();
		dm.setMaxCodeLen(MAX_LENGTH);
		
		a = "Manchester United";
		b = "Nanchestar United"; // intentional two spelling mistakes, M -> N and e -> a
		ma = dm.doubleMetaphone(a);
		mb = dm.doubleMetaphone(b);
		System.out.println(String.format("%s for [%s(%s)]=[%s(%s)]", mdm0.match(a, b), a, ma, b, mb));
		System.out.println(String.format("%s for [%s(%s)]=[%s(%s)]", mdm1.match(a, b), a, ma, b, mb));
	}
}
