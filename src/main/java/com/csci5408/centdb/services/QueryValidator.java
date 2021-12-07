package com.csci5408.centdb.services;
import java.util.regex.*;

public class QueryValidator {

	Pattern pattern = Pattern.compile("(select (.*?) from(.*?)(where.*)?)|"
			+ "(delete (.*?)from(.*?)(where.*)?)|"
			+ "(insert into(.*?) \\((.*?)\\) values \\((.*?))\\)|"
			+ "(update (.*?) set (.*?) where (.*?))|"
			+"(show dbs)|"
			+ "(create db (.*?))|"
			+ "(use (.*?))|"
			+ "(create table (.*?) \\((.*?)\\))|"
			+ "(begin)|"
			+ "(start)|"
			+ "(commit)|"
			+ "(rollback)", Pattern.CASE_INSENSITIVE);
    
	public boolean validateQuery(String query) {
		Matcher matcher = pattern.matcher(query);
	    boolean matchFound = matcher.find();
	    return matchFound;
		}	
}
