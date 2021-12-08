package com.csci5408.centdb.services;

import com.csci5408.centdb.services.queryimplementation.*;

import java.io.IOException;

public class CheckTypeOfQuery {
	String databaseName;
	public void checkTypeOfQuery(String query) throws IOException {
		String querySplitArray[] = query.split(" ");
		if(querySplitArray[0].equalsIgnoreCase("update")) {
			UpdateQuery updateQuery = new UpdateQuery();
			updateQuery.updateQuery(query, "resources\\Databases\\"+UseDatabase.getDatabaseName(), true);
		}
		else if(querySplitArray[0].equalsIgnoreCase("delete") && querySplitArray[1].equalsIgnoreCase("from")) {
			DeleteQuery deleteQuery = new DeleteQuery();
			deleteQuery.deleteQuery(query, "resources\\Databases\\"+UseDatabase.getDatabaseName(), true);
		}
		else if(querySplitArray[0].equalsIgnoreCase("use")) {
			UseDatabase useDatabase = new UseDatabase();
			useDatabase.use(query);
			System.out.println(databaseName);
		}
		else if(querySplitArray[0].equalsIgnoreCase("create") && querySplitArray[1].equalsIgnoreCase("database") ) {
			System.out.println("inside create");
			CreateDatabase createDatabase = new CreateDatabase();
			createDatabase.createDb(query);
			System.out.println(databaseName);
		}
		else if(querySplitArray[0].equalsIgnoreCase("create") && querySplitArray[1].equalsIgnoreCase("table") ) {
			System.out.println("inside create table");
			CreateTable createTable = new CreateTable();
			createTable.createTable(query);
		}
		else if(querySplitArray[0].equalsIgnoreCase("insert") && querySplitArray[1].equalsIgnoreCase("into") ) {
			System.out.println("inside insert table");
			InsertQuery.insert(query, "resources\\Databases\\"+UseDatabase.getDatabaseName());
		}
		else if(querySplitArray[0].equalsIgnoreCase("drop")) {
			System.out.println("inside drop table");
			InsertQuery.insert(query, "resources\\Databases\\"+UseDatabase.getDatabaseName());
		}
		else if(querySplitArray[0].equalsIgnoreCase("select")) {
			System.out.println("inside select table");
			SelectQuery.select(query, "resources\\Databases\\"+UseDatabase.getDatabaseName());
		}
	}
}