package com.dictionary.api;

import java.util.HashSet;
import java.util.Set;

/**
 * @author nitinpai
 * 
 *         Data structure used to store all the words which would be a part of
 *         the dictionary created by {@link DictionaryApp}
 */
public class Word {

	/**
	 * String value of the token
	 */
	private String value;

	/**
	 * Level assigned for the Word which is a part of a hierarchy in a
	 * dictionary
	 */
	private Integer level = 0;

	/**
	 * Set of all words which define the key word
	 */
	private Set<Word> attributes = new HashSet<Word>();

	@Override
	public String toString() {
		return "Word [value=" + value + ", level=" + level + ", attributes="
				+ attributes + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<Word> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Word> attributes) {
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void incrementLevel() {
		this.level += 1;
	}

	public Boolean hasAttributes() {
		return attributes.size() != 0;
	}

}
