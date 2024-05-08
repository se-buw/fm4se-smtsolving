package de.buw.fm4se.smtsolving.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.*;

public class PuzzleTest {

	private static final Map<String, String> emojiMap = new HashMap<>();
	static {
		emojiMap.put(":smiley:", "\uD83D\uDE01");
		emojiMap.put(":heart:", "\u2764");
		emojiMap.put(":star:", 	"\u2B50");
		emojiMap.put(":sunny:", "\u2600");
		emojiMap.put(":cloud:", "\u2601");
		emojiMap.put(":umbrella:", "\u2614");
		emojiMap.put(":snowman:", "\u26C4");
		emojiMap.put(":gem:", "\uD83D\uDC8E");
		emojiMap.put(":soccer:", "\u26BD");
		emojiMap.put(":apple:", "\uD83C\uDF4E");
	}

	public static void main(String[] args) {
		// String puzzle = readPuzzleFromMd();
		// puzzle = puzzle.replaceAll(":", "");
		// List<String> p = convertEmoji(puzzle);
		// System.err.println(p);
		// List<String> smt = generateSmtAssert(puzzle);
		// System.out.println(smt.get(0));
	}

	/**
	 * Read the puzzle from the puzzle.md file
	 * @return puzzle as string
	 */
	public static String readPuzzleFromMd(){
		try{
			String file = "puzzle.md";
			BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
			StringBuilder puzzle = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				puzzle.append(line).append("\n");
			}
			reader.close();

			System.err.println(puzzle.toString());
			return puzzle.toString();

		}catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Generates SMT negation of the assertions for the puzzle
	 * 
	 * @param puzzle puzzle as string
	 * @return list of negation of the assertions
	 */
	public static List<String> generateSmtAssert(String puzzle){
		puzzle = puzzle.replaceAll("\\?", "secret");
		
		String wordPattern = "(\\w+)|(-?\\d+)";
		String operatorPattern = "[+\\-*]";
		Pattern wordRegex = Pattern.compile(wordPattern);
    Pattern operatorRegex = Pattern.compile(operatorPattern);

		List<String> smt = new ArrayList<>();
		String[] lines = puzzle.split("\n\n");
		for (String line : lines) {
			List<String> words = new ArrayList<>();
    	List<String> operators = new ArrayList<>();

			Matcher wordMatcher = wordRegex.matcher(line);
			Matcher operatorMatcher = operatorRegex.matcher(line);
			while (wordMatcher.find()) {
					words.add(wordMatcher.group());
			}
			while (operatorMatcher.find()) {
				operators.add(operatorMatcher.group());
			}
			
			String smtLine =  String.format("(assert (not (= %s (%s (%s %s %s) %s))))", 
					words.get(words.size()-1),
					operators.get(1),
					operators.get(0),
					words.get(0),
					words.get(1),
					words.get(2)
					);
			smt.add(smtLine);
		}
		System.err.println(smt);
		return smt;
	}
	
	/**
	 * Convert the github-like emojis in the puzzle to their unicode representation
	 * @param input puzzle with emojis (e.g., :smiley: :heart: :star: :sunny: :cloud: :umbrella: :snowman: :gem: :soccer: :apple:)
	 * @return List of puzzle lines with unicode emojis (e.g., 😁 ❤ ⭐ ☀ ☁ ☂ ⛄ 💎 ⚽ 🍎)
	 */
	public static List<String> convertEmoji(String input) {
		String pattern = ":\\w+:"; // Updated regular expression pattern

		Pattern emojiPattern = Pattern.compile(pattern);
		Matcher matcher = emojiPattern.matcher(input);

		
		StringBuffer output = new StringBuffer();

		while (matcher.find()) {
				String emojiCode = matcher.group(0);
				String emojiUnicode = emojiMap.get(emojiCode);
				if (emojiUnicode != null) {
						matcher.appendReplacement(output, emojiUnicode);
				}
		}

		matcher.appendTail(output);

		List<String> formula = Arrays.asList(output.toString().split("\n\n"));

		return formula;
	}
}
