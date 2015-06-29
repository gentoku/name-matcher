package namematcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class NameMatcher {
		
	/**
	 * Returns pairs of matched names in two group. They must be same size collections.  
	 * @param subjects
	 * @param candidates
	 * @return
	 */
	public static Map<String, String> groupToGroup (Set<String> subjects, Set<String> candidates) {
		NameMatcher nm = new NameMatcher();
		if (subjects.size() != candidates.size()) return nm.results;
		nm.subjects = subjects;
		nm.candidates = candidates;
		nm.matchGroups();
		return nm.results;
	}
	
	/**
	 * Returns matched names from candidates.
	 * MatcherTokens doesn't work well for one to group match.
	 * e.g. Man City matches Hull City, Stoke City, and Leicester City because of 'City'
	 * @param subject
	 * @param candidates
	 * @return
	 */
	public static Set<String> oneToGroup (String subject, Set<String> candidates) {
		NameMatcher nm = new NameMatcher();
		nm.subject = subject;
		nm.candidates = candidates;
		return Stream.of(	new MatcherExactly(), 
							new MatcherAbbrev(), 
							new MatcherDoubleMetaphone(0),
							new MatcherDoubleMetaphone(1))
			.map(nm::getMatches)
			.flatMap(Set::stream)
			.collect(Collectors.toSet());
	}
	
	/**
	 * a single subject.
	 */
	private String subject;
	
	/**
	 * list of subjects.
	 */
	private Set<String> subjects;
	
	/**
	 * list of candidates.
	 */
	private Set<String> candidates;
	
	/**
	 * Result as Map. Key is a subject and value is a candidate matched.   
	 */
	private Map<String, String> results = new HashMap<String, String>();

	/**
	 * Returns list of candidates matched by matcher.
	 * @param matcher
	 * @return
	 */
	private Set<String> getMatches (Matcher matcher) {
		return matcher.getMatches(this.subject, this.candidates);
	} 
	
	/**
	 * Finds names matching each other from two groups.
	 * 
	 * Once it finds out a pair of two names matching each other,  
	 * they will be removed from both groups respectively. Thus order of procedure matters.
	 * 	1.	removes a pair of names matching exactly each other.
	 * 	2.	removes a pair of names if one is an abbreviation of another.
	 * 	3.	removes a pair of names if two metaphones matching each other's.
	 * 	4.	removes a pair of names if levenshtein distance of metaphones is less than 1.
	 * 	5.	removes a pair of names if a part of one name matches a part of another name.
	 * 	6.	A last pair matches automatically. 
	 */
	private void matchGroups () {
		this.findMatches(new MatcherExactly());
		this.findMatches(new MatcherAbbrev());
		this.findMatches(new MatcherDoubleMetaphone(0));
		this.findMatches(new MatcherDoubleMetaphone(1));
		this.findMatches(new MatcherTokens());
		this.lastOneMatchesAutomatically();
	}
	
	/**
	 * Finds matches for every subjects to candidates by the matcher. 
	 * @param matcher
	 */
	private void findMatches (Matcher matcher) {
		String[] tempSubjects = this.subjects.toArray(new String[this.subjects.size()]);
		for (String subject : tempSubjects) {
			Set<String> matches = matcher.getMatches(subject, this.candidates);
			if (matches.size() == 1) this.setMatches(subject, matches.iterator().next());
		}
	}
	
	/**
	 * Sets matched names as a pair and removes them from subjects 
	 * and candidates respectively. 
	 * @param subject
	 * @param candidate
	 */
	private void setMatches (String subject, String candidate) {
		this.results.put(subject, candidate);
		this.subjects.remove(subject);
		this.candidates.remove(candidate);
	}
	
	/**
	 * Last names in subjects and candidates must match automatically.
	 */
	private void lastOneMatchesAutomatically () {
		if (this.subjects.size() == 1 && this.candidates.size() == 1) {
			this.results.put(this.subjects.iterator().next(), this.candidates.iterator().next());
		} 
	}
}
