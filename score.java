package com.System;

import java.util.ArrayList;
import java.util.List;

public class score {
	private List<Double> content;
	private List<Double> location;
	
	score(){
		content = new ArrayList<Double>();
		location = new ArrayList<Double>();
	}
	
	public void addConScore(double score) {
		content.add(score);
	}
	
	public void addLocScore(Double score) {
		location.add(score);
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
		}
		
		for(int i = 0; i < location.size();i++) {
			location.set(i, min/Math.max(location.get(i), 0.00001));
		}
	}
	
	public double getCon( int index) {
		return content.get(index);
	}
	
	public double getLoc(int index) {
		return location.get(index);
	}
}
