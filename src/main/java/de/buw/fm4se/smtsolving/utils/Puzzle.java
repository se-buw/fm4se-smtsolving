package de.buw.fm4se.smtsolving.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import org.json.JSONObject;
import java.io.File;

public class Puzzle {

	private static final Map<String, String> emojiMap = new HashMap<>();
	static {
		emojiMap.put(":smiley:", "\uD83D\uDE01");
		emojiMap.put(":heart:", "\u2764");
		emojiMap.put(":star:", "\u2B50");
		emojiMap.put(":sunny:", "\u2600");
		emojiMap.put(":cloud:", "\u2601");
		emojiMap.put(":umbrella:", "\u2614");
		emojiMap.put(":snowman:", "\u26C4");
		emojiMap.put(":gem:", "\uD83D\uDC8E");
		emojiMap.put(":soccer:", "\u26BD");
		emojiMap.put(":apple:", "\uD83C\uDF4E");
	}

	private static String[] emojis = { ":smiley:", ":heart:", ":star:", ":sunny:", ":cloud:", ":umbrella:", ":snowman:",
			":gem:", ":soccer:", ":apple:" };

	private static String[] operators = { "+", "-", "*" };

	public static void main(String[] args) {
		String[] puzzle = generatePuzzle(4);
		generateMarkdown(puzzle[0]);
		String permalink = savePuzzleToFmPlay(puzzle[0], puzzle[1].replaceAll(":", ""));
		addPuzzleLinkToReadme(permalink);
	}

	/**
	 * Generates a puzzle with the given size
	 * 
	 * @param i number of symbols
	 */
	private static String[] generatePuzzle(int i) {
		if (i > emojis.length) {
			throw new IllegalArgumentException("Puzzle size must not be greater than the number of emojis");
		}
		// pick i random emojis
		Set<Integer> taken = new HashSet<Integer>();
		List<Integer> symbols = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			int random = (int) (Math.random() * emojis.length);
			while (taken.contains(random)) {
				random = (int) (Math.random() * emojis.length);
			}
			taken.add(random);
			symbols.add(random);
			// assign value between 0 and 19
			values.add((int) (Math.random() * 20));
		}

		// print puzzle
		String puzzle = "";
		String smt = "";
		for (int j = 0; j < i; j++) {
			smt += String.format("(declare-const %s Int)\n", emojis[symbols.get(j)]);
		}
		smt += String.format("(declare-const secret Int)\n");
		for (int j = 0; j < i; j++) {
			// generate one line of the puzzle
			int operand1 = (int) (Math.random() * symbols.size());
			int operator1 = (int) (Math.random() * operators.length);
			int operand2 = (int) (Math.random() * symbols.size());
			int operator2 = (int) (Math.random() * operators.length);
			int operand3 = (int) (Math.random() * symbols.size());

			String evalLine = String.format("(%s %s %s) %s %s", values.get(operand1), operators[operator1],
					values.get(operand2), operators[operator2], values.get(operand3));

			String puzzleLine = String.format("(%s %s %s) %s %s = %s", emojis[symbols.get(operand1)],
					operators[operator1], emojis[symbols.get(operand2)], operators[operator2],
					emojis[symbols.get(operand3)], (j == i - 1) ? "?" : eval(evalLine));

			// print

			puzzle += puzzleLine + "\n";

		}
		String[] result = { puzzle, smt };

		return result;
	}

	private static String eval(String line) {
		DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
		try {
			return doubleEvaluator.evaluate(line).intValue() + "";
		} catch (Exception e) {
			e.printStackTrace();
			return "?";
		}
	}

	/**
	 * Generates a markdown file with the given puzzle
	 * 
	 * @param puzzle
	 */
	private static void generateMarkdown(String puzzle) {
		puzzle = puzzle.replaceAll("\n", "\n\n");
		try {
			String file = "puzzle.md";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(puzzle);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the puzzle to FM Play and returns the permalink
	 * 
	 * @param puzzle generated puzzle
	 * 
	 * @param smt generated smt initialisation template
	 * 
	 * @return permalink to the puzzle
	 */
	private static String savePuzzleToFmPlay(String puzzle, String smt) {
		puzzle = convertEmoji(puzzle);
		String[] puzzles = puzzle.split("\n");
		StringBuilder puzzleComments = new StringBuilder();
		puzzleComments.append(";; Exercise 3 Task 2\n");
		puzzleComments.append(";Encode the following Puzzle as a model for Z3\n");
		for (String line : puzzles) {
			puzzleComments.append(";").append(line).append("\n");
		}
		String comment = puzzleComments.toString();
		String smtWithComment = comment + smt;

		System.out.println(smtWithComment);

		try {
			URL url = new URL("https://play.formal-methods.net/api/save");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			// con.setDoInput(true);
			con.setDoOutput(true);

			JSONObject json = new JSONObject();
			json.put("parent", "");
			json.put("check", "SMT");
			json.put("code", smtWithComment);
			String reqBody = json.toString();

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(reqBody.getBytes());
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject jsonResponse = new JSONObject(response.toString());
			String permalink = "https://play.formal-methods.net/?check=SMT&p=" + jsonResponse.getString("permalink");

			System.out.println(permalink);

			con.disconnect();
			return permalink;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Add permalink to the README.md
	 * Looks for the line starting with "Puzzle:" and add the permalink in front of
	 * it
	 * 
	 * @param permalink permalink to add
	 */
	private static void addPuzzleLinkToReadme(String permalink) {
		try {
			String file = "README.md";
			File inputFile = new File(file);
			BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
			StringBuilder content = new StringBuilder();

			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			reader.close();

			String linkToSave = String.format("Puzzle: [%s](%s)", permalink, permalink);
			String updatedContent = content.toString().replace("Puzzle:", linkToSave);
			BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
			writer.write(updatedContent);
			writer.close();

			System.out.println("Link added successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Converts the github-like emojis in the puzzle to their unicode representation
	 * 
	 * @param input puzzle with emojis
	 * 
	 * @return puzzle with unicode emojis
	 */
	public static String convertEmoji(String input) {
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

		return output.toString();
	}
}
