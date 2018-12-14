package com.System;

import java.util.ArrayList;
import java.util.List;

public class score {
	private List<Double> content;
	private List<Double> location;
	private List<String> url;
	
	score(){
		content = new ArrayList<Double>();
		location = new ArrayList<Double>();
		url = new ArrayList<String>();
	}
	
	public void addElement(String url, double con, double loc) {
		if(hasURL(url)) {
			int index = this.url.indexOf(url);
			content.set(index, content.get(index) + con);
			location.set(index, location.get(index) + loc);
		}else {
		
			addScoreURL(url);
			addConScore(con);
			addLocScore(loc);
		}
	}
	
	private void addConScore(double score) {
		content.add(score);
	}
	
	private void addLocScore(Double score) {
		location.add(score);
	}
	
	private void addScoreURL(String url) {
		this.url.add(url); 
	}
	
	public int getIndexOfURL(String url) {
		return this.url.indexOf(url);
	}
	
	public boolean hasURL(String url) {
		return this.url.contains(url);
	}
	
	public void normalize() {
		double 	max = 0,
				min = 100000;
		
		for(double m : content) {
			max = Math.max(max, m);
		}
		
		for(double m : location) {
			min = Math.min(min, m);
		}
		
		for(int i = 0; i < content.size();i++) {
			content.set(i, content.get(i)/max);
			//System.out.println(content.get(i));
		}
		
		for(int i = 0; i < location.size();i++) {
			location.set(i, min/Math.max(location.get(i), 0.00001));
			//System.out.println(location.get(i));
		}
	}
	
	public double getCon( int index) {
		return content.get(index);
	}
	
	public double getLoc(int index) {
		return location.get(index);
	}
}
