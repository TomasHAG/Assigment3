package com.System;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;  
import javax.servlet.http.HttpServletResponse; 
import javax.ws.rs.Consumes; 
import javax.ws.rs.DELETE; 
import javax.ws.rs.FormParam; 
import javax.ws.rs.GET; 
import javax.ws.rs.OPTIONS; 
import javax.ws.rs.POST; 
import javax.ws.rs.PUT; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.Context; 
import javax.ws.rs.core.MediaType;  
@Path("/UserService") 

public class UserService { 
	
	/*private String 	pathWords = "wikipedia\\Words",
					pathLinks = "wikipedia\\Links";*/
	
	private String 	pathWords = "C:\\Users\\Tomas\\Desktop\\web Intelegence\\assignment 3\\wikipedia\\Words",
			pathLinks = "C:\\Users\\Tomas\\Desktop\\web Intelegence\\assignment 3\\wikipedia\\Links";
	
	private SearchEngine SE = new SearchEngine(pathWords, pathLinks);
	
	@GET
	@Path("/querySearch/{input}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String querySearch(@PathParam("input") String input) {
		if(SE.query(input))
			return "1";
		return "0";
	}
	
	@GET
	@Path("/getFromTopWithIndex/URL/{input}/{index}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getFromTopWithIndexURL(@PathParam("input") String input, @PathParam("index") String index) {
		String out = "";
		try {
			int ind = Integer.parseInt(index);
			out = SE.getResultFromIndex(ind).getURL();
		}catch(NumberFormatException e) {
			out = "0";
		}
		return out;
	}
	
	@GET
	@Path("/getFromTopWithIndex/score/{input}/{index}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getFromTopWithIndexScore(@PathParam("input") String input,@PathParam("index") String index) {
		
		String out = "";
		try {
			int ind = Integer.parseInt(index);
			out = String.valueOf(SE.getResultFromIndex(ind).getScore());
		}catch(NumberFormatException e) {
			out = "0";
		}
		return out;
	}
	
	@GET
	@Path("/getTopFive/{input}") //this is the only one used in index!
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getTopFive(@PathParam("input") String input) {
		
		if(querySearch(input).equals("0"))
			return "error";
		
		String out = "";
		String row = "";
		
		for(int i = 0; i < 5;i++) {
			out += SE.getResultFromIndex(i).getURL() + "\n" + SE.getResultFromIndex(i).getScore() + "\n";
		}
		
		return out;
	}
   
}
