package com.csci5408.centdb.services.queryimplementation;

import com.csci5408.centdb.logging.QueryLogs;
import com.csci5408.centdb.services.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectQuery {

	public static void select(String inputQuery, String database) throws IOException {
		String query = inputQuery;
		String db = database;
		String columnString = "";
		String table = "";
		List<String> columns = new ArrayList<String>();
		List<String> columnNames = new ArrayList<String>();
		List<HashMap<String, String>> tableData = new ArrayList<HashMap<String, String>>();
		String condition = "";
		String conditionalColumn = "";
		String conditionalColumnValue = "";
		String[] s = null;
		List<String> displayColumns = new ArrayList<String>();
		Boolean columnFlag = true;
		Boolean where = false;
		String dbName = "";
		Integer count = 0;
		try {

			String regex = "select(.*?)from(.*?)";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				columnString = (matcher.group(1).trim());
			}

			regex = "from(.*?)where(.*?)";
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(query);
			while (matcher.find()) {
				table = (matcher.group(1).trim());
			}

			String[] string_where = query.split("where");
			condition = string_where[1].trim();
			System.out.println("333" + " :" + condition + ":");
			if (condition.contains("=")) {
				string_where = condition.split("=");
				conditionalColumn = string_where[0].trim();
				conditionalColumnValue = string_where[1].trim();
			}

			File f = new File(database + "\\" + table + ".txt");
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(database + "\\" + table + ".txt"));
				String columnName = br.readLine();
				String row = br.readLine();

				while (row != null) {
					columns.add(row);
					row = br.readLine();
					;
				}
				if (columnName != null) {
					s = columnName.split("\\|");
					for (String column : s) {
						columnNames.add(column);
					}
				}
				if (columns.size() > 0) {
					for (String column : columns) {
						HashMap<String, String> rowData = new HashMap<String, String>();
						s = column.split("\\|");
						for (int i = 0; i < s.length; i++) {
							rowData.put(columnNames.get(i), s[i]);
						}
						tableData.add(rowData);
					}

				} else {
					System.out.println("No data in Table: " + table);
					QueryLogs queryLogs = new QueryLogs();
					queryLogs.createQueryLog(UserService.getUserName(), "select", "failure", dbName, table,
							"NA", "NA", condition);
				}
				br.close();
			} else {
				System.out.println(table + ": Table doesn't exist");
				QueryLogs queryLogs = new QueryLogs();
				queryLogs.createQueryLog(UserService.getUserName(), "select", "failure", dbName, table,
						"NA", "NA", condition);
			}
			if (columnNames.contains(conditionalColumn)) {
				where = true;
			} else {
				System.out.println("Invalid where column");
				QueryLogs queryLogs = new QueryLogs();
				queryLogs.createQueryLog(UserService.getUserName(), "select", "failure", dbName, table,
						"NA", "NA", condition);
			}
			if (columnString.equals("*")) {
				displayColumns = columnNames;
			} else {
				String selectedColumns[];
				selectedColumns = columnString.split(",");
				for (String col : selectedColumns) {
					if (columnNames.contains(col.trim()))
						displayColumns.add(col.trim());
					else
						columnFlag = false;
				}
			}
			if (where) {
				if (columnFlag) {
					for (String key : displayColumns) {
						System.out.print(key + "       ");
					}
					System.out.println();
					for (int i = 0; i < tableData.size(); i++) {
						if(tableData.get(i).get(conditionalColumn).equals(conditionalColumnValue)) {
						for (String key : displayColumns) {
							System.out.print(tableData.get(i).get(key) + "       ");
						}
						System.out.println();
						count++;
						}
					}
					QueryLogs queryLogs = new QueryLogs();
					dbName = database.split("\\\\")[2];
					System.out.println(dbName);
					queryLogs.createQueryLog(UserService.getUserName(), "select", "Success", dbName, table,
							"NA", "NA", condition);
				} else {
					System.out.println("Column name does not exist");
					QueryLogs queryLogs = new QueryLogs();
					queryLogs.createQueryLog(UserService.getUserName(), "select", "failure", dbName, table,
							"NA", "NA", condition);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			QueryLogs queryLogs = new QueryLogs();
			queryLogs.createQueryLog(UserService.getUserName(), "select", "failure", dbName, table,
					"NA", "NA", condition);
		}

	}

}
