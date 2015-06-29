package namematcher;

import java.util.Set;
import java.util.TreeSet;

public class MatcherAbbrev implements Matcher {

	/**
	 * Checks if the abbreviation stands for the text bi-directionally.
	 * @param abbrev
	 * @param text
	 * @return boolean
	 */
	@Override
	public boolean match(String a, String b) {
		return this.isAbbreviation(a, b) || this.isAbbreviation(b, a);
	}

	/**
	 * Checks if the abbreviation is for the text.
	 * @param abbrev
	 * @param text
	 * @return boolean
	 */
	public boolean isAbbreviation (String abbrev, String text) {
		// normalize strings.
		abbrev = this.cut(abbrev);
		text = this.cut(text);
		
		/*
		 * If every single character in the given abbreviation has been consumed
		 * without any problems, then this function can give TRUE.
		 */
		if (abbrev.length() == 0) return true;
		
		/*
		 * These are conditions which can conclude clearly the pair is not matched.
		 * 	1. The text has been consumed all before the abbreviation has been consumed.
		 * 	2. Two initial letters (of the abbreviation and the text) doesn't match.
		 */
		if (abbrev.length() > 0 && text.length() == 0) return false;
		if (abbrev.charAt(0) != text.charAt(0)) return false;
		
		/*
		 * Recurses to check if the next words in the text matches the second letter in
		 * the abbreviation when 
		 * 	1. the initial letter of the first word in the text given MATCHES the initial
		 * letter of the abbreviation.
		 * 	2. there are still rest of words in the text.
		 * 	3. there are still rest of letters in the abbreviation.
		 */
		return this.isAbbreviation(abbrev.substring(1), this.getRestWords(text))
				|| this.isAbbrevRecursive(abbrev.substring(1), text, this.getFirstWordLength(text));
	}
	
	/**
	 * Converts string to lower case and replace every comma into white space
	 * 
	 * @param text
	 * @return
	 */
	private String cut (String text) {
		text = text.toLowerCase();
		return text.replaceAll("\\.", " ").trim();
	}
	
	/**
	 * Splits the text given into two parts after converting every comma into white space.
	 * words[0] is the first word and words[1] is the rest of words.
	 * e.g. "This is a recursive function." 
	 * 	-> words[0] = "This", words[1] = "is a recursive function";  
	 * @param text
	 * @return array of string whose length is always two.
	 */
	private String[] splitIntoFirstAndRest (String text) {
		text.replaceAll("\\.", " ");
		return text.trim().split("\\s+", 2);
	}
	
	/**
	 * No need the first word itself but the length of it.  
	 * @param text
	 * @return length of the first word
	 */
	private int getFirstWordLength (String text) {
		String[] words = this.splitIntoFirstAndRest(text);
		return words[0].length();
	} 
	
	/**
	 * To avoid null when words[1] doesn't exist.
	 * @param text
	 * @return words[1] or ""
	 */
	private String getRestWords (String text) {
		String[] words = this.splitIntoFirstAndRest(text);
		if (words.length > 1) {
			return words[1];
		} else {
			return "";
		}
	} 

	/**
	 * Alternative function for Python code.
	 * 	any(is_abbreviation(abbrev[1:],text[i+1:])
	 * 		for i in range(len(words[0]))))
	 * @param abbrev
	 * @param text
	 * @param length
	 * @return boolean
	 */
	private boolean isAbbrevRecursive (String abbrev, String text, int length) {
		boolean b = false;
		for (int i = 0; i < length; i ++) {
			b = b || this.isAbbreviation(abbrev, text.substring(i + 1));
		}
		return b;
	}
	
	public static void main(String[] args) {
		String a = "Manchester United";
		String b = "Man Utd";
		MatcherAbbrev ma = new MatcherAbbrev();
		System.out.println(ma.match(a, b) + " for [" + a + "] and [" + b + "]");
		
		a = "Leicester City";
		b = "Leicester";
		System.out.println(ma.match(a, b) + " for [" + a + "] and [" + b + "]");
		
		a = "Queens Rangers Park";
		b = "Q.R.P.";
		System.out.println(ma.match(a, b) + " for [" + a + "] and [" + b + "]");
		
		String subject = "Man Utd";
		Set<String> candidates = new TreeSet<>(); 
		candidates.add("Manchester United");		
		candidates.add("Manchester City");
		candidates.add("Arsenal");
		candidates.add("Chelsea");
		ma.getMatches(subject, candidates).forEach(System.out::println);
	}
}
