package com.System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebCrawler { // generated data sets of all data
	
	private HashMap<String, Integer> wordToId;
	private List<Page> pages;
	
	public WebCrawler(String pathDirContent, String pathDirLinks) {
		pages = new ArrayList<Page>();
		wordToId = new HashMap<String, Integer>();
		
		loadFromFiles(pathDirContent);
		getAndInsertLinks(pathDirLinks);
	}
	
	private void loadFromFiles(String pathDir) {
		File folder = new File(pathDir);
		byte[] bytes;
		
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				loadFromFiles(pathDir + "//" + fileEntry.getName());
			}else {
				try {
					bytes = Files.readAllBytes(fileEntry.toPath());
					addPage("/wiki/" + fileEntry.getName(),new String(bytes,"UTF-8"));
				}catch(FileNotFoundException e) {
					System.err.println("file: " + fileEntry.getName() + "not found.");
				}catch(IOException e) {
					System.err.println("an IO exception when file: " + fileEntry.getName());
				}
			}
		}
	}
	
	private void addPage(String url, String content) {
		pages.add(new Page(url));
		
		String[] contentParts = content.split(" ");
		
		for(String w : contentParts) {
			pages.get(pages.size() - 1).insertWord(getIdForWord(w));;
		}
	}
	
	public int getIdForWord(String word) {
		if(wordToId.containsKey(word))
			return wordToId.get(word);
		
		int id = wordToId.size();
		wordToId.put(word, id);
		return id;
	}
	
	private void getAndInsertLinks(String pathDir) {
		File folder = new File(pathDir);
		byte[] bytes;
		
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				getAndInsertLinks(pathDir + "//" + fileEntry.getName());
			}else {
				try {
					bytes = Files.readAllBytes(fileEntry.toPath());
					addLinks("/wiki/" + fileEntry.getName(),new String(bytes,"UTF-8"));
				}catch(FileNotFoundException e) {
					System.err.println("file: " + fileEntry.getName() + "not found.");
				}catch(IOException e) {
					System.err.println("an IO exception when file: " + fileEntry.getName());
				}
			}
		}
	}
	
	private void addLinks(String url, String links) {
		String[] Links = links.split("\r");
		
		Page p = getPage(url);
		
		for(String L : Links) {
			p.insertURL(L);
		}
	}
	
	public List<Page> getPages(){
		return pages;
	}
	
	public Page getPage(String url) {
		for(Page p : pages) {
			if(p.isURL(url))
				return p;
		}
		return null;
	}
	
	public boolean realWord(String word) {
		if(wordToId.containsKey(word))
			return true;
		return false;
	}
	

}
