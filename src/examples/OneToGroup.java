package examples;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import namematcher.NameMatcher;

public class OneToGroup {

	public static void main(String[] args) {
		
		String subject = "Manchester United";
		Set<String> candidates = Stream.of( 
			"Man Utd",
			"Manchester Utd",
			"ManUtd",
			"ManU",
			"Manchestar United", // intentional spelling mistake
			"Mancester United", // intentional spelling mistake
			"Manchester City",
			"Newcastle United",
			"Leicester City",
			"Arsenal",
			"Chelsea")
			.collect(Collectors.toSet());				
		
		NameMatcher.oneToGroup(subject, candidates)
			.forEach(System.out::println);
	}
}
