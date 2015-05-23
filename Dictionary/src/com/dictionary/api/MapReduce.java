package com.dictionary.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapReduce {
	
	private static Logger logger = Logger.getLogger();

	public void map(String[] tokens, Map<String, Word> index) {

		String keyToken = tokens[0];
		
		Word keyWord = createWord(index, keyToken);		

		for (int i = 1; i < tokens.length; i++) {
			String token = tokens[i];
			if (tokens[i].startsWith("#")) {
				break;
			}

			if (token.length() != 0 && !token.trim().equals(".")) {
				Word attribute = createWord(index, token);
				keyWord.getAttributes().add(attribute);
			}
		}
	}

	protected Word createWord(Map<String, Word> index, String token) {
		Word word = index.get(token);
		if(word == null){
			word = createWord(token);
			index.put(token, word);
		}
		return word;
	}

	public Integer reduce(Word word, Map<Integer, Set<Word>> dictionary) {
		logger.debug("Reduce -> " + word.getValue());
		
		if (word.hasAttributes()) {
			for (Word attribute : word.getAttributes()) {
				logger.debug("Got Attribute -> " + attribute.getValue());
				Integer attributeLevel = reduce(attribute, dictionary);
				logger.debug("Attribute " + attribute.getValue() + " processed and is at " + attributeLevel);				
				logger.debug(" Checking ... Word " + word.getValue() + " level " + word.getLevel());
				
				if(word.getLevel() <= attributeLevel){
					word.setLevel(attributeLevel+1);
					logger.debug("updated " + word.getValue() + " to " + word.getLevel());
				}	
			}
		}		
		
		Integer wordLevel = word.getLevel();
			
		if(dictionary.get(wordLevel) == null ){
			dictionary.put(wordLevel, new HashSet<Word>());
		}
		dictionary.get(wordLevel).add(word);
		
		return wordLevel;
	}

	protected Word createWord(String value) {
		Word word = new Word();
		word.setValue(value);
		word.setLevel(0);
		return word;
	}

}
