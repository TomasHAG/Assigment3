package com.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchEngine {
	
	private List<listElement> results;
	
	private score scores;
	
	private WebCrawler spider;
	
	public SearchEngine(String pathWords, String pathLinks){
		results = new ArrayList<listElement>();
		spider = new WebCrawler(pathWords, pathLinks);
		
		scores = new score();
		
		for(Page p : spider.getPages()) {
			results.add(new listElement(p.getURL()));
		}
	}
	
	public boolean query(String value) {
		
		if(value.isEmpty()) //controll so input is not emty
			return false;
		
		String[] words = value.split(" ");
		
		int counter = 0;
		for(String w : words) {
			
			if(spider.realWord(w)) {
				for(listElement E : results) {
					scores.addConScore(frequencyMetric(spider.getPage(E.getURL()), w));
					scores.addLocScore(locationMetric(spider.getPage(E.getURL()), w));
				}
			}else
				counter++;
		}
		
		if(counter == words.length)//if there are no words that exists in the pages.
			return false;
		
		scores.normalize();
		
		for(int w = 0; w < words.length;w++) {
			for(int i = w * results.size(); i < (w + 1) * results.size();i++) {
				results.get(i - (w * results.size())).addScore(scores.getCon(i) + 0.8 * scores.getLoc(i));
			}
		}
		
		Collections.sort(results);
		
		return true;
	}
	
	private double frequencyMetric(Page page, String word) {
		return page.containNumberOfWords(spider.getIdForWord(word));
	}
	
	private double locationMetric(Page page, String word) {
		int score = 0,
			wordId = spider.getIdForWord(word),
			index = 1;
		
		for(Integer pw : page.getWords()) {
			if(pw == wordId)
				score += index;
			index++;
				
		}
		if(score == 0)
			return 100000;
		
		return score;
	}
	
	public listElement getResultFromIndex(int index) {
		return results.get(index);
	}
}
