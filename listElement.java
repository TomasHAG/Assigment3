package com.System;

public class listElement implements Comparable{
	private String url;
	private double score;
	
	listElement(String url){
		this.url = url;
		score = 0;
	}
	
	public String getURL() {
		return url;
	}
	
	public double getScore() {
		return score;
	}

	@Override
	public int compareTo(Object arg0) {
		
		if(this.score > ((listElement) arg0).getScore())
			return -1;
		if(this.score < ((listElement) arg0).getScore())
			return 1;
		return 0;
	}
	
	public void addScore(double score) {
		this.score += score;
	}
	
	
}
