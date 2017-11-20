package com.jdbcReverseEngineering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Connexion {
	
	protected static Logger log=Logger.getLogger(Connexion.class);
	
	public String jdbcDriver;
	private String jdbcURL;
	private String user;
	private String password;
	String database;
	
	private Connection connection;
	private java.sql.Statement statement;
	private ResultSet resultSet;
	
	/**
	 * Constructor of Connexion.
	 * @param url
	 * @param driver
	 * @param user
	 * @param password
	 * @param database
	 */
	public Connexion(String url, String driver, String user, String password, String database) {
		this.jdbcURL = url;
		this.jdbcDriver =  driver;
		this.user = user;
		this.password = password;
		this.database = database;
		log.info("Driver is : " + this.jdbcDriver);
		log.info("URL is : " + this.jdbcURL);
		log.info("USER is : " + this.user);
		log.info("password is : " + this.password);
		log.info("database is : " + this.database);
		
	}

	/**
	 * getConnection()
	 * @return connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * getStatement()
	 * @return statement
	 */
	public Statement getStatement() {
		return statement;
	}
	
	/**
	 * getResultSet()
	 * return resultSet
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}
	
	/**
	 * Initialise a connection to the database
	 * @throws Exception
	 */
	public void initialiseConnection() throws Exception{
		Class.forName(this.jdbcDriver);
		connection = DriverManager.getConnection(this.jdbcURL, this.user, this.password);
		statement = connection.createStatement();
	}
	

	/**
	 * Release all the database objects (resultSet, statement, connection)
	 */
	public void close() {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}
}
