package com.dictionary.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author nitinpai
 *
 *This is the core logic for creation of the dictionary used by {@link DictionaryApp}. It consists of 2 passes to get the dictionary created
 *<ol>
 *	<li>MAP - the map logic divides the text files into tokens, converts them to {@link Word} and creates an index consisting of all words</li>
 *	<li>REDUCE - the reduce logic converts the indexed map of {@link Word} to a hierarchy map having set of {@link Word} assigned at a particular level, resulting in a dictionary</li>
 *</ol> 
 *
 *
 */

public class MapReduce {

	/**
	 * Singleton reference to {@link Logger} to log information 
	 */
	private static Logger logger = Logger.getLogger();

	/**
	 * Gets inputs as string tokens from a line in a file and creates @see Word
	 * The index is updated with the newly created words If a comment is
	 * encountered either at the beginning or in the middle the entire line post
	 * the # is ignored If a token with a primitive (.) is encountered it
	 * results in creation of a {@link Word} which has no attributes
	 * 
	 * @param tokens - Split string tokens from each line in a file
	 * @param index - The index which would include all the tokens in the form of {@link Word}
	 */
	public void map(String[] tokens, Map<String, Word> index) {

		String keyToken = tokens[0];

		if (isComment(keyToken)) {
			return;
		}

		Word keyWord = createWord(index, keyToken);

		for (int i = 1; i < tokens.length; i++) {
			String token = tokens[i];
			if (isComment(token)) {
				return;
			}

			if (token.length() != 0 && !token.trim().equals(".")) {
				Word attribute = createWord(index, token);
				keyWord.getAttributes().add(attribute);
			}
		}
	}

	/**
	 * Given a string token it verifies if its a comment. A comment starts with a hash (#)
	 * 
	 * @param token - A string text
	 * @return - True it is a comment. False otherwise
	 */
	private boolean isComment(String token) {
		return token.startsWith("#");
	}

	/**
	 * Creates a new @see Word for a given string token and puts it in the
	 * index. If a word already was created before then it returns the earlier
	 * created Word
	 * 
	 * @param index - The index which contains all {@link Word}
	 * @param token - The token from which a {@link Word} is to be created
	 * @return - Initialized {@link Word}
	 */
	protected Word createWord(Map<String, Word> index, String token) {
		Word word = index.get(token);
		if (word == null) {
			word = createWord(token);
			index.put(token, word);
		}
		return word;
	}

	/**
	 * The reduce logic takes in a {@link Word} and recursively processes its attributes
	 * to build a hierarchy. The hierarchy is built by assigning a level to each
	 * Word the logic processes For example: if B and C define A then B and C
	 * will be at level 0 and A will be at level 1 In the above example if C
	 * defines B then C will be a 0 and B will be a 1. This in turn put A at
	 * level 2 After a hierarchy is defined for a {@link Word} it is inserted into the
	 * dictionary at the level designated to it
	 * 
	 * @param word - A {@link Word} which has to be processed for determining its hierarchy
	 * @param dictionary - Map to hold references to each level of the processed {@link Word}
	 * @return Level of the {@link Word} being processed
	 */
	public Integer reduce(Word word, Map<Integer, Set<Word>> dictionary) {
		logger.debug("Reduce -> " + word.getValue());

		if (word.hasAttributes()) {
			for (Word attribute : word.getAttributes()) {
				logger.debug("Got Attribute -> " + attribute.getValue());
				Integer attributeLevel = reduce(attribute, dictionary);
				logger.debug("Attribute " + attribute.getValue()
						+ " processed and is at " + attributeLevel);
				logger.debug(" Checking ... Word " + word.getValue()
						+ " level " + word.getLevel());

				if (word.getLevel() <= attributeLevel) {
					word.setLevel(attributeLevel + 1);
					logger.debug("updated " + word.getValue() + " to "
							+ word.getLevel());
				}
			}
		}

		Integer wordLevel = word.getLevel();

		if (dictionary.get(wordLevel) == null) {
			dictionary.put(wordLevel, new HashSet<Word>());
		}
		dictionary.get(wordLevel).add(word);

		return wordLevel;
	}

	/**
	 * Helper method to instantiate a {@link Word} for an input String token
	 * 
	 * @param value - A String text
	 * @return - {@link Word} instance created from the string text input
	 */
	protected Word createWord(String value) {
		Word word = new Word();
		word.setValue(value);
		word.setLevel(0);
		return word;
	}

}
