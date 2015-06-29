# Team Name Matcher

A library to match fuzzy, noisy, or various names representing a unique thing. For sports teams in particular.

## About

What do you call for that red football club in Manchester?
- Manchester United FC?
- Manchester United?
- Man United?
- Man Utd?
- Man U?

No correct or no wrong. Everyone calls as they like as long as it is easy to map to Manchester United and distinguish from the other light blue club in the same city. But it will be a bit troublesome when dealing with these names in computer programming because they are occasionally too fuzzy and lazy to work with regular expressions.

This library can be helpful for such a purpose, to find a proper and suitable team for a name which may be abbreviated variously or even a spelling mistake.

## Usage

#### To match group to group

Creates two Sets of names for a same group or league. Then invoke the method with them. You will get matching names as Map. Here is example for England Premier League.

```java
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
```
Result:
```
Tottenham Hotspur : Tottenham
Liverpool : Riverpool
Swansea City : Swansea
Aston Villa : Aston Villa
Manchester City : Man City
Stoke City : Stoke
Newcastle United : Newcastle
Sunderland : Sanderland
West Ham United : West Ham
Norwich City : Norwich
Crystal Palace : Crystal Palace
Southampton : Southampton
Watford : Watford
Leicester City : Leisester
West Bromwich Albion : West Brom
AFC Bournemouth : Bournemouth
Arsenal : Arsenal
Chelsea : Chelsea
Manchester United : Man United
Everton : Everton
```

#### To match one to group

To find out names matching the subject from candidates. Returns *all* names matching the subject.

```java
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
```
Result:
```
ManUtd
Mancester United
Manchester Utd
Man Utd
ManU
Manchestar United
```

## Requirements

- Java 1.8 or later

## Third Party Libraries and Dependencies

- [Apache Commons Codec](http://commons.apache.org/proper/commons-codec/)
- [Metaphone3.java](https://code.google.com/p/google-refine/source/browse/trunk/main/src/com/google/refine/clustering/binning/Metaphone3.java) (Optional. You may use Metaphone 3 instead of Double Metaphone. Note that the license is [complicated](https://github.com/threedaymonk/text/issues/21).)

## Lisence

Under the MIT license.
