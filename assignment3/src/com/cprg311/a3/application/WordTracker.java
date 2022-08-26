package com.cprg311.a3.application;

import com.cprg311.a3.adt.IteratorADT;
import com.cprg311.a3.exceptions.TreeException;
import com.cprg311.a3.utilities.BSTReferencedBased;
import com.cprg311.a3.utilities.Word;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tracks the number of words used within a given file and builds a binary tree
 * to store unique data.
 */
public class WordTracker {
	private final String REPO_FILE_NAME = "repository.ser";
	private BSTReferencedBased<Word> tree = null;

	public WordTracker() throws IOException, ClassNotFoundException {
		loadRepository();
	}

	/**
	 * Processes and parses a text file one line at a time
	 * @param fileName The input file being read in
	 * @throws IOException
	 * @throws TreeException
	 */
	public void processTextFile(String fileName) throws IOException, TreeException {
		File file = new File(fileName);
		Reader reader = new FileReader(file);
		try (LineNumberReader lineNumberReader = new LineNumberReader(reader)) {
			String line = lineNumberReader.readLine();
			while (line != null) {
				// Remove everything but letters from the line
				String[] words = line.replaceAll("[--]", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				parseWords(words, lineNumberReader.getLineNumber(), file.getName());
				line = lineNumberReader.readLine();
			}
		}
		saveRepository();
	}

	/**
	 * Parses words within an input file and adds them to a binary search tree
	 * @param words Words being parsed
	 * @param lineNumber The line the words are on
	 * @param fileName The input file
	 * @throws TreeException
	 */
	private void parseWords(String[] words, int lineNumber, String fileName) throws TreeException {
		if (words.length > 0) {
			for (String entry : words) {
				if (!entry.equals("")) {
					// If the word is already in the tree increase the total count otherwise, add a new word
					if (tree.contains(new Word(entry, lineNumber, fileName))) {
						Word word = tree.search(new Word(entry, lineNumber, fileName)).getData();
						word.addLocation(lineNumber, fileName);
					} else {
						tree.add(new Word(entry, lineNumber, fileName));
					}
				}
			}
		}
	}

	/**
	 * Saves the repository
	 * @throws IOException
	 */
	private void saveRepository() throws IOException {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(REPO_FILE_NAME);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.tree);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			throw new IOException("Unable to save repository file.", e);
		}
	}

	/**
	 * Loads the repository
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void loadRepository() throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(REPO_FILE_NAME);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			this.tree = (BSTReferencedBased<Word>) objectInputStream.readObject();
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			// No file found: create new instance of tree
			this.tree = new BSTReferencedBased<>();
		} catch (IOException e) {
			throw new IOException("Unable to open repository file.", e);
		} catch (ClassNotFoundException e) {
			// Serializable error: class has been changed
			throw new ClassNotFoundException("Repository class version has changed.", e);
		}
	}

	/**
	 * Outputs the data within the tree
	 * @param printType The output format for the words
	 * @param fileName The name of the output file
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void outputTree(String printType, String fileName) throws IllegalArgumentException, IOException {
		if (printType == null || printType.equals(""))
			throw new IllegalArgumentException("Print type cannot be empty or null.");

		if (tree.isEmpty()) {
			System.out.printf("Tree is empty.\n");
			return;
		}

		StringBuilder output = new StringBuilder();
		IteratorADT<Word> iterator = tree.inorderIterator();

		switch (printType) {
			case "f":
				output.append(String.format("┃%-15s┃%-25s\n┣%s╋%s\n", "WORD", "FILE", "━".repeat(15), "━".repeat(25)));
				break;
			case "l":
				output.append(String.format("┃%-15s┃%-25s┃%s\n┣%s╋%s╋%s\n", "WORD", "FILE", "LINES", "━".repeat(15), "━".repeat(25), "━".repeat(100)));
				break;
			case "o":
				output.append(String.format("┃%-15s┃%-10s┃%-25s┃%s\n┣%s╋%s╋%s╋%s\n", "WORD", "TOTAL", "FILE", "LINES", "━".repeat(15), "━".repeat(10), "━".repeat(25), "━".repeat(100)));
				break;
		}

		int occurrences = 0;
		while (iterator.hasNext()) {
			Word word = iterator.next();
			occurrences += word.getOccurrences();
			String temp = "";
			String files[] = word.getFiles();
			for (String file : files) {
				Integer[] lines = word.getLineNumbers(file);
				String lineNumbers = Stream.of(lines).map(String::valueOf).collect(Collectors.joining(","));
				switch (printType) {
					case "f":
						temp += (temp.equals("")) ? String.format("┃%-15s┃%-25s", word, file) : String.format("\n┃%-15s┃%-25s", "", file);
						break;
					case "l":
						temp += (temp.equals("")) ? String.format("┃%-15s┃%-25s┃%s", word, file, lineNumbers) : String.format("\n┃%-15s┃%-25s┃%s", "", file, lineNumbers);
						break;
					case "o":
						temp += (temp.equals("")) ? String.format("┃%-15s┃%10s┃%-25s┃%s", word, word.getOccurrences(), file, lineNumbers) : String.format("\n┃%-15s┃%10s┃%-25s┃%s", "", word.getOccurrences(), file, lineNumbers);
						break;
				}
			}
			output.append(String.format("%s\n", temp));
		}
		output.append(String.format("\nTotal: %d words, %d occurrences. \n", tree.size(), occurrences));

		// Display to console or write to file
		if (fileName != null && !fileName.equals("")) {
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(output);
			printWriter.flush();
			printWriter.close();
		} else {
			System.out.println(output);
		}
	}
}


