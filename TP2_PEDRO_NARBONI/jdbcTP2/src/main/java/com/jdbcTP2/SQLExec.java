package com.jdbcTP2;


public class SQLExec {
	static String driver;
	static String url;
	static String username;
	static String password;	
	static String request;
	public static void main(String[] args) throws Exception {
		if(args.length == 5) {
			url = args[0];
			driver = args[1];
			username = args[2];
			password = args[3];
			request = args[4];
		}
		Connexion dao = new Connexion(driver,url,username,password,request);
		dao.readData();
	}
}

/* To execute the code the command line is :
 *  mvn exec:java -Dexec.mainClass="com.jdbcTP2.SQLExec" -Dexec.args="jdbc:mysql://localhost/sakila com.mysql.jdbc.Driver root root 'SELECT * FROM actor;'"
 */
