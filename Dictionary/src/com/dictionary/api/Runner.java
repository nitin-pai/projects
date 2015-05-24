package com.dictionary.api;

import java.io.File;

/**
 * @author nitinpai
 * 
 *         Class used to start the program. It requires a single argument which
 *         is the folder where the test files are present
 * 
 */
public class Runner {
	
	private static Logger logger = Logger.getLogger();

	/**
	 *  * @param args -
	 *            [] one argument specifying the folder location
	 */
	public static void main(String args[]) {

		if (args.length == 0)
			return;

		if (args[0] == null)
			return;

		processFile(new File(args[0]));

	}

	/**
	 * Based on the directory input this method starts to process each file in the directory.
	 * For each file it starts the {@link DictionaryApp}
	 * @param dir - directory where the files to be processed reside
	 */
	public static void processFile(File dir) {
		try{
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {
					continue;
				} else {
					new DictionaryApp(file).run();
				}
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
			logger.info("Cannot proceed with the directory provided");
		}
	}

}
