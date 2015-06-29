package examples;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import namematcher.NameMatcher;

public class GroupToGroup {

	public static void main(String[] args) {
		
		Set<String> namesInESPN = Stream.of(
				"Arsenal",
				"Aston Villa",
				"AFC Bournemouth",
				"Chelsea",
				"Crystal Palace",
				"Everton",
				"Leicester City",
				"Liverpool",
				"Manchester City",
				"Manchester United",
				"Newcastle United",
				"Norwich City",
				"Southampton",
				"Stoke City",
				"Sunderland",
				"Swansea City",
				"Tottenham Hotspur",
				"Watford",
				"West Bromwich Albion",
				"West Ham United")
				.collect(Collectors.toSet());
		
		// these are set as an example, not used in practice.
		Set<String> namesInSkySports = Stream.of(
				"Arsenal",
				"Aston Villa",
				"Bournemouth", // short
				"Chelsea",
				"Crystal Palace",
				"Everton",
				"Leisester", // short and intentional spelling mistake
				"Riverpool", // intentional spelling mistake
				"Man City", // abbreviation
				"Man United", // abbreviation
				"Newcastle", // short
				"Norwich", // short
				"Southampton",
				"Stoke", // short
				"Sanderland", // intentional spelling mistake
				"Swansea", // short
				"Tottenham", // short
				"Watford",
				"West Brom", // abbreviation
				"West Ham") // short
				.collect(Collectors.toSet());
		
		NameMatcher.groupToGroup(namesInESPN, namesInSkySports)
			.forEach((k, v) -> System.out.println(k + " : " + v));
	}
}
