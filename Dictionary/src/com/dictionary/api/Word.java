package com.dictionary.api;

import java.util.HashSet;
import java.util.Set;

public class Word {

	private String value;
	private Integer level = 0;
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
	
	public Boolean hasAttributes(){
		return attributes.size() != 0;
	}

}
