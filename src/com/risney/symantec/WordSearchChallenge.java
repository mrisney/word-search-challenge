package com.risney.symantec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

enum Orientation {
	N, S, E, W, NE, SE, SW, NW;

	private String direction;

	public String getDirection() {
		switch (this) {
		case N:
			direction = "Bottom to Top";
			break;
		case S:
			direction = "Top to Bottom";
			break;
		case E:
			direction = "Right to Left";
			break;
		case W:
			direction = "Left to Right";
			break;
		case NE:
			direction = "Diagonally Upwards, Left to Right";
			break;
		case SE:
			direction = "Diagonally Downwards, Left to Right";
			break;
		case NW:
			direction = "Diagonally Upwards, Right to Left";
			break;
		case SW:
			direction = "Diagonally Downwards, Left to Right";
			break;
		}
		return direction;
	}
}

public class WordSearchChallenge {
	public WordSearchChallenge() {

	}
	private char[][] twoDimensionalArray;
	private Scanner scanner;
	private Orientation orientation;

	public ArrayList<String> getWordsFromFile() throws FileNotFoundException,
			IOException {

		ArrayList<String> wordList = new ArrayList<String>();

		try (InputStream countriesInput = this.getClass().getResourceAsStream(
				"/files/countries.txt");
				InputStreamReader is = new InputStreamReader(countriesInput);
				BufferedReader reader = new BufferedReader(is);) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				wordList.add(line);
			}
		}
		return wordList;
	}

	public void create2DArrayFromFile() throws FileNotFoundException,
			IOException {

		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("files/matrix.txt");
		File file = new File(url.getPath());
		scanner = new Scanner(file);

		String firstLine = scanner.nextLine();
		int size = firstLine.length();
		twoDimensionalArray = new char[size][size];
		for (int col = 0; col < size; col++) {
			twoDimensionalArray[0][col] = firstLine.charAt(col);
		}

		// Populate array, from bottom to top
		int row = 1;
		while (scanner.hasNextLine() && row < size) {
			String line = scanner.nextLine();
			for (int col = 0; col < size; col++) {
				twoDimensionalArray[row][col] = line.charAt(col);
			}
			row++;
		}
	}

	// look for first letter of matching word,
	// if found, continue by using wordSearch method
	public void findWord(String word) {
		boolean wordFound = false;
		int size = twoDimensionalArray[0].length;

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {

				if (wordFound == false
						&& twoDimensionalArray[row][column] == word.charAt(0)) {
					wordFound = wordSearch(word, row, column);
				}
			}
		}
	}

	// Searches all eight directions, pass orientation
	public boolean wordSearch(String word, int row, int column) {
		boolean match = false;
		int len = word.length();

		// West (right to left), horizontally
		if ((column + len) <= twoDimensionalArray[0].length) {
			int wordPos = 0;
			for (int i = column; i <= (column + len) - 1; i++) {
				if (word.charAt(wordPos) != twoDimensionalArray[row][i]) {
					break;
				}
				if (i == (column + len) - 1) {
					match = true;
					printMatch(word, row, column, row, i, orientation.W);
				}
				wordPos++;
			}
		}
		// East (left to right), horizontally
		if ((column - len) >= -1) {
			int wordPos = 0;
			for (int i = column; i >= (column - len) + 1; i--) {
				if (word.charAt(wordPos) != twoDimensionalArray[row][i]) {
					break;
				}
				if (i == (column - len) + 1) {
					match = true;
					printMatch(word, row, column, row, i, orientation.E);
				}
				wordPos++;
			}
		}

		// South (top to bottom), vertically
		if ((row + len) <= twoDimensionalArray[0].length) {
			int wordPos = 0;
			for (int i = row; i <= (row + len) - 1; i++) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][column]) {
					break;
				}
				if (i == (row + len) - 1) {
					match = true;
					printMatch(word, row, column, i, column, Orientation.S);
				}
				wordPos++;
			}
		}

		// North (bottom to top), vertically
		if ((row - len) >= -1) {
			int wordPos = 0;
			for (int i = row; i >= (row - len) + 1; i--) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][column]) {
					break;
				}
				if (i == (row - len) + 1) {
					match = true;
					printMatch(word, row, column, i, column, orientation.N);
				}
				wordPos++;
			}
		}

		// SE (down and to the right) , diagonally
		if ((row + len) <= twoDimensionalArray[0].length
				&& (column + len) <= twoDimensionalArray[0].length) {
			int wordPos = 0;
			int j = column;
			for (int i = row; i <= (row + len) - 1; i++) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][j]) {
					break;
				}
				if (i == (row + len) - 1) {
					match = true;
					printMatch(word, row, column, i, j, orientation.SE);
				}
				wordPos++;
				j++;
			}
		}
		// SW (down and to the left) , diagonally
		if ((row + len) <= twoDimensionalArray[0].length
				&& (column - len) >= -1) {
			int wordPos = 0;
			int j = column;
			for (int i = row; i <= (row + len) - 1; i++) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][j]) {
					break;
				}
				if (i == (row + len) - 1) {
					match = true;
					printMatch(word, row, column, i, j, orientation.SW);
				}
				wordPos++;
				j--;
			}
		}

		// NW (up and to the left) , diagonally
		if ((row - len) >= -1 && (column - len) >= -1) {
			int wordPos = 0;
			int j = column;
			for (int i = row; i >= (row - len) + 1; i--) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][j]) {
					break;
				}
				// match was found
				if (i == (row - len) + 1) {
					match = true;
					printMatch(word, row, column, i, j, orientation.NW);
				}
				wordPos++;
				j--;
			}
		}
		// NE (up and to the right) , diagonally
		if ((row - len) >= -1
				&& (column + len) <= twoDimensionalArray[0].length) {
			int wordPos = 0;
			int j = column;
			for (int i = row; i >= (row - len) + 1; i--) {
				if (word.charAt(wordPos) != twoDimensionalArray[i][j]) {
					break;
				}
				// match was found
				if (i == (row - len) + 1) {
					match = true;
					printMatch(word, row, column, i, j, orientation.NE);
				}
				wordPos++;
				j++;
			}
		}
		return match;
	}

	public void printMatch(String word, int startRow, int startColumn,
			int endRow, int endColumn, Orientation orientation) {

		StringBuffer sb = new StringBuffer("word found : '" + word + "' - "
				+ orientation.getDirection());
		sb.append(", starting at [");
		sb.append(startRow + "," + startColumn + "]");
		sb.append(" ending at [");
		sb.append(endRow + "," + endRow + "].");
		System.out.println(sb.toString());

	}

	public static void main(String[] args) {
		try {
			WordSearchChallenge wordSearchChallenge = new WordSearchChallenge();
			wordSearchChallenge.create2DArrayFromFile();
			for (String word : wordSearchChallenge.getWordsFromFile()) {
				wordSearchChallenge.findWord(word.toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
