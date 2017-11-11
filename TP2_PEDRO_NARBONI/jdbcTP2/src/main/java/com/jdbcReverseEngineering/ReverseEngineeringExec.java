package com.jdbcReverseEngineering;

import java.io.IOException;

public class ReverseEngineeringExec {
	
	static String driver;
	static String url;
	static String username;
	static String password;
	static String db;
	
	public static void main(String[] args) throws IOException {
		try {
			new ReverseEngineering().doMain(args); 	
		} catch (IOException e) {
			System.out.println("Verify if your have all arguments");
		}
	}

}

/* To execute the code the command line is :
 * Sur windows : 
 *  mvn exec:java -Dexec.mainClass="com.jdbcReverseEngineering.ReverseEngineeringExec" -Dexec.args="com.mysql.jdbc.Driver localhost root root sakila"
 * Sur MAC : 
 * mvn exec:java -Dexec.mainClass="com.jdbcReverseEngineering.ReverseEngineeringExec" -Dexec.args="com.mysql.jdbc.Driver localhost:8889 root root sakila" 
 */

