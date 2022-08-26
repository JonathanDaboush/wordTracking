package com.cprg311.a3.application;

import com.cprg311.a3.exceptions.TreeException;

import java.io.IOException;

public class Main {
	private static String printType = "";
	private static String inputFilename = "";
	private static String outputFilename = "";

	public static void main(String[] args) throws IOException, ClassNotFoundException, TreeException {

		System.out.printf("\n\n");

		if (args.length == 0) {
			// no args: display the stored tree data from the repository
			WordTracker wordTracker = new WordTracker();
			wordTracker.outputTree("o", null);
		} else if (parseArgs(args)) {
			WordTracker wordTracker = new WordTracker();
			wordTracker.processTextFile(inputFilename);
			wordTracker.outputTree(printType, outputFilename);
		} else {
			System.out.printf("\nUsage: java -jar word-tracker.jar [OPTION] <input file...>\n\n");
			System.out.printf("\t-pf,\t\toutput format: words (alphabetic order) with files\n");
			System.out.printf("\t-pl,\t\toutput format: words (alphabetic order) with files and line numbers\n");
			System.out.printf("\t-po,\t\toutput format: words (alphabetic order) with files, line numbers and occurrences\n");
			System.out.printf("\t-f<file>\t[optional] output file\n\n");
		}
	}

	/**
	 * Parses command line args
	 * @param args Command line args
	 * @return True if args successfully parsed otherwise, false
	 */
	private static boolean parseArgs(String[] args) {
		boolean result = true;

		for (String arg : args) {
			switch (arg.substring(1, 2).toLowerCase()) {
				case "f":       // filename
					// file for output
					outputFilename = arg.substring(2).toLowerCase();
					break;
				case "p":       // sort type
					switch (arg.substring(2).toLowerCase()) {
						case "f":
							// all words in alphabetic order with a list of files in which words occur
							printType = "f";
							break;
						case "l":
							// all words in alphabetic order with a list of files and line numbers in which words occur
							printType = "l";
							break;
						case "o":
							// all words in alphabetic order with a list of files and line numbers in which words occur and the frequency of occurrence of words
							printType = "o";
							break;
						default:
							System.out.println("[" + arg.substring(2) + "] is an invalid sort type.");
							result = false;
							break;
					}
					break;
				default:
					// Anything left has to be the filename
					inputFilename = arg;
					break;
			}
		}
		return (result && inputFilename != null && !inputFilename.equals(""));
	}

}
