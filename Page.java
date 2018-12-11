package com.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Page {
	private String url;
	private List<Integer> words;
	private List<String> urlOut;
	
	private HashMap<Integer, Integer> preLoadedData; //to check every word everytime to get number of words take long time. by saving it it goes faster eatch other time that is not the first.
	
	public Page(String url){
		this.url = url;
		words = new ArrayList<Integer>();
		preLoadedData = new HashMap<Integer, Integer>();
		urlOut = new ArrayList<String>();
	}
	
	public String getURL() {
		return url;
	}
	
	public boolean containWord(int id) {
		return words.contains(id);
	}
	
	public int containNumberOfWords(int id) {
		if(!containWord(id))
			return 0;
		
		if(preLoadedData.containsKey(id))
			return preLoadedData.get(id);
		
		int count = 0;
		for(int w : words) {
			if(id == w)
				count++;
		}
		
		preLoadedData.put(id, count);
		
		return count;
	}
	
	public void insertWord(int id) {
		words.add(id);
	}
	
	public boolean isURL(String url) {
		return this.url.equals(url);
	}
	
	public void insertURL(String url) {
		urlOut.add(url);
	}
	
	public List<Integer> getWords(){
		return words;
	}
	
}