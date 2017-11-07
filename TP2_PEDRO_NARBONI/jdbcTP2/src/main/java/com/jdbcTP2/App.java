package com.jdbcTP2;


public class App {
	//public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	//static String connectionString = "jdbc:mysql://localhost/sakila?user=root&password=root&useUnicode=true&characterEncoding=UTF-8";
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
