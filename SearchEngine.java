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
	
	@SuppressWarnings("unchecked")
	public boolean query(String value) {
		
		if(value.isEmpty()) //controll so input is not emty
			return false;
		
		String[] words = value.split(" ");
		
		int counter = 0;
		for(String w : words) {
			
			if(spider.realWord(w)) {
				for(listElement E : results) {
					scores.addElement(E.getURL(), frequencyMetric(spider.getPage(E.getURL()), w), locationMetric(spider.getPage(E.getURL()), w));
				}
			}else
				counter++;
		}
		
		if(counter == words.length)//if there are no words that exists in the pages.
			return false;
		
		scores.normalize();
		
		for(listElement p : results) {
			p.addScore(scores.getCon(scores.getIndexOfURL(p.getURL())) + (0.8 * scores.getLoc(scores.getIndexOfURL(p.getURL()))));
			//System.out.print(p.getURL() + "	");
			//System.out.print(scores.getCon(scores.getIndexOfURL(p.getURL())) + "	");
			//System.out.print((0.8 * scores.getLoc(scores.getIndexOfURL(p.getURL()))) + "	");
			//System.out.println(scores.getCon(scores.getIndexOfURL(p.getURL())) + (0.8 * scores.getLoc(scores.getIndexOfURL(p.getURL()))));
		}
		System.out.println();
		Collections.sort(results);
		
		return true;
	}
	
	private double frequencyMetric(Page page, String word) {
		double ret = 0;
		if(page.getURL().toLowerCase().contains(word))
			ret = 1.0;
		return ret + page.containNumberOfWords(spider.getIdForWord(word));
	}
	
	private double locationMetric(Page page, String word) {
		int score = 0,
			wordId = spider.getIdForWord(word),
			index = 1;
		
			//if(page.getURL().toLowerCase().contains(word)) {
			//	return 1;
				//score += index;
				//index++;
			//}
		
		for(Integer pw : page.getWords()) {
			if(pw == wordId)
				return index;
			index++;
				
		}
		if(score == 0)
			return 100000;
		
		return score;
	}
	
	public listElement getResultFromIndex(int index) {
		return results.get(index);
	}
	
	public score getScores() {
		return scores;
	}
}
