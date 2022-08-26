package com.cprg311.a3.utilities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a Word (within a file)
 */
public class Word implements Comparable<Word>, Serializable {
	private String word = null;
	private int count = 0;
	private ArrayList<WordLocation> locations = null;

	public Word(String word, int lineNumber, String fileName) {
		this.word = word.trim().toLowerCase();
		this.locations = new ArrayList<>();
		addLocation(lineNumber, fileName);
	}

	/**
	 * Gets the file name each word location belongs to
	 * Gets the names of files that have word locations
	 * @return An array of Strings containing the names of files previously accessed
	 */
	public String[] getFiles() {
		ArrayList<String> result = new ArrayList<>();
		String file = "";
		for (WordLocation location : locations) {
			if (file.equals("") || !file.equals(location.file)) {
				file = location.file;
				result.add(file);
			}
		}
		return result.toArray(new String[result.size()]);
	}

	public int getOccurrences() {
		return this.count;
	}

	/**
	 * Gets the line numbers for all stored word locations
	 * @param file The file the word is in
	 * @return An Integer array containing a list of line numbers for word locations
	 * @throws IllegalArgumentException
	 */
	public Integer[] getLineNumbers(String file) throws IllegalArgumentException {
		if (file == null || file.equals(""))
			throw new IllegalArgumentException("The file name is missing.");

		ArrayList<Integer> result = new ArrayList<>();
		for (WordLocation location : locations) {
			if (location.file.equals(file)) {
				result.add(location.line);
			}
		}
		return result.toArray(new Integer[result.size()]);
	}


	/**
	 * Adds the location of a unique word to the 'locations' ArrayList
	 * @param lineNumber The line on which the word occurs
	 * @param fileName The file the word is in
	 */
	public void addLocation(int lineNumber, String fileName) {
		// Don't add duplicate locations but always increment number of occurrences
		WordLocation location = new WordLocation(lineNumber, fileName);
		if (!this.locations.contains(location)) {
			this.locations.add(location);
		}
		this.count++;
	}

	@Override
	public String toString() {
		return (this.word);
	}

	@Override
	public int compareTo(Word anotherWord) {
		return (this.word.compareTo(anotherWord.word));
	}

	/**
	 * Represents the location of a word (within a file)
	 */
	private class WordLocation implements Serializable {
		public int line = 0;
		public String file = null;

		public WordLocation(int line, String file) {
			this.line = line;
			this.file = file;
		}

		@Override
		public boolean equals(Object o) {
			WordLocation location = (WordLocation) o;
			return (this.line == location.line && this.file.equals(location.file));
		}
	}

}

