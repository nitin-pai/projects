package com.dictionary.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author nitinpai
 * 
 *         Class which builds a dictionary for the input file. It can be either
 *         run as a single instance or in a threaded fashion
 * 
 */

public class DictionaryApp implements Runnable {

	/**
	 * used to split the line into tokens
	 */
	private String DELIMITER = " ";

	/**
	 * Singleton reference to {@link Logger} to log information 
	 */
	private static Logger logger = Logger.getLogger();

	/**
	 * Map to hold all the {@link Word} created by the app. Key is the String token and
	 * value is the {@link Word} created out of it
	 */
	private Map<String, Word> index = new TreeMap<String, Word>();

	/**
	 * Map to hold the hierarchy of Words in the order of levels assigned to the
	 * {@link Word} in the index. Key is level and value is the Set of {@link Word} falling at
	 * the level
	 */
	private Map<Integer, Set<Word>> dictionary = new TreeMap<Integer, Set<Word>>();

	/**
	 * File from which the dictionary is to be built
	 */
	private File source;

	/**
	 * Class constructor specifying which file it has to create the dictionary
	 * for
	 * 
	 * @param source
	 */
	public DictionaryApp(File source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/**
	 * The run method in turn starts the {@link MapReduce} algorithm and prints the dictionary
	 * post its creation
	 */
	public void run() {
		MapReduce algorithm = new MapReduce();

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(source);
			bufferedReader = new BufferedReader(fileReader);

			logger.info("Processing file : " + source.getAbsolutePath());

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() == 0)
					continue;

				algorithm.map(line.split(DELIMITER), index);
			}

			logger.debug(index.toString());
			logger.info("Indexing complete");

			for (String key : index.keySet()) {
				algorithm.reduce(index.get(key), dictionary);
			}

			logger.debug(index.toString());
			logger.info("Dictionary created");

			print();

		} catch (IOException io) {
			logger.debug(io.getMessage());
			logger.info("File could not be read");
		} finally {
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				logger.debug(e.getMessage());
				logger.info("Some error occured while cleaning up resources");
			}
		}
	}

	/**
	 * Method to print the dictionary
	 */
	protected void print() {
		logger.info("Printing Dictionary -> ");
		for (Integer key : dictionary.keySet()) {
			Set<Word> words = dictionary.get(key);
			for (Word word : words) {
				System.out.print("\t" + word.getValue());
			}
		}

		System.out.println();
	}
}
