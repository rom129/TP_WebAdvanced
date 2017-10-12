package com.jdbcTP2;
/**
 * Hello world!
 *
 */
public class App 
{
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	static String connectionString = "jdbc:mysql://localhost:8889/sakila?user=root&password=root&useUnicode=true&characterEncoding=UTF-8";
	public static final String MYSQL_URL = connectionString;
	public static void main(String[] args) throws Exception {
		Connexion dao = new Connexion(MYSQL_DRIVER,MYSQL_URL);
		dao.readData();
	}
}
