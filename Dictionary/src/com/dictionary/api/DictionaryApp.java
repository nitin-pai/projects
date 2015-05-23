package com.dictionary.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DictionaryApp {
	
	private static String DELIMITER = " ";
	
	private static Logger logger = Logger.getLogger();
	
	private static Map<String, Word> index = new HashMap<String, Word>();
	
	private static Map<Integer, Set<Word>> dictionary = new TreeMap<Integer, Set<Word>>();

	public static void run(String source) {
		MapReduce algorithm = new MapReduce();
		File file = new File(source);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			logger.info("Processing file : " + file.getAbsolutePath());

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() == 0)
					continue;
				
				algorithm.map(line.split(DELIMITER), index);
			}
			
			logger.debug(index.toString());
			logger.info("Indexing complete");
			
			for(String key : index.keySet()){
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

	protected static void print() {
		logger.info("Printing Dictionary -> ");
		for(Integer key : dictionary.keySet()){
			Set<Word> words = dictionary.get(key);
			for(Word word : words){
				System.out.print("\t" + word.getValue());
			}
		}
	}

}
