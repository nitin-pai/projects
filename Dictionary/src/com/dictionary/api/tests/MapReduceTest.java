package com.dictionary.api.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dictionary.api.MapReduce;
import com.dictionary.api.Word;

public class MapReduceTest {
	
	private static MapReduce algorithm = new MapReduce();
	
	public static void testMap(){
		Map<String, Word> index = new HashMap<String, Word>();
		
		Word A = new Word();
		A.setValue("A");
		A.setLevel(0);
		
		Word B = new Word();
		B.setValue("B");
		B.setLevel(0);

		Word C = new Word();
		C.setValue("C");
		C.setLevel(0);
		
		A.getAttributes().add(B);
		A.getAttributes().add(C);
		
		String[] tokens = {"A", "B", "C"};
		
		algorithm.map(tokens, index);
		
		assert index.size() == 3;
		
		assert index.get("A").getAttributes().size() == 2;
		
		assert index.get("A").getAttributes().contains(B);
		
		assert index.get("A").getAttributes().contains(C);
		
		assert !index.get("B").getAttributes().contains(C);
		
		assert index.get("C").getAttributes().size() == 0;
				
		System.out.println("all tests passed");
	}
	
	public static void testReduce(){
		Map<String, Word> index = new HashMap<String, Word>();
		Map<Integer, Set<Word>> dictionary = new HashMap<Integer, Set<Word>>();
		
		Word A = new Word();
		A.setValue("A");
		A.setLevel(0);
		
		Word B = new Word();
		B.setValue("B");
		B.setLevel(0);

		Word C = new Word();
		C.setValue("C");
		C.setLevel(0);
		
		A.getAttributes().add(B);
		A.getAttributes().add(C);
		
		B.getAttributes().add(C);
		
		index.put("A", A);
		index.put("B", B);
		index.put("C", C);
		
		algorithm.reduce(A, dictionary);
		
		assert dictionary.containsKey(0);
		
		assert dictionary.containsKey(1);
		
		assert dictionary.containsKey(2);
		
		assert dictionary.get(2).size() == 1;
		
		assert dictionary.get(1).size() == 1;
		
		assert dictionary.get(0).size() == 1;
		
		assert dictionary.get(2).contains(A);
		
		assert !dictionary.get(2).contains(B);
		
		assert dictionary.get(0).contains(C);
				
		System.out.println("all tests passed");

	}
	
	public static void main(String args[]){
		testMap();
		testReduce();
	}
}
