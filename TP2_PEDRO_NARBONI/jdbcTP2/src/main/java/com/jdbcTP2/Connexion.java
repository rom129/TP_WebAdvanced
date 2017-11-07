package com.jdbcTP2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




import org.apache.log4j.*;

public class Connexion {
	
	
	protected static Logger log=Logger.getLogger(Connexion.class);
	
	enum TestTableColumns{
		actor_id,last_name;
	}
	private String jdbcDriverStr;
	private String jdbcURL;
	private String username;
	private String password;
	private String request;
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	/**
	 * Constuctor of the class.
	 * @param jdbcDriverStr
	 * @param jdbcURL
	 */
	public Connexion(String jdbcDriverStr, String jdbcURL, String username, String password,String request) {
		this.jdbcDriverStr = jdbcDriverStr;
		this.jdbcURL = jdbcURL;
		this.username = username;
		this.password = password;
		this.request = request;
	}
	
	/**
	 * Connection
	 * @param jdbcDriverStr
	 * @return
	 */
	public Connection connect() {
		try {
			Class.forName(this.jdbcDriverStr);
			connection =  DriverManager.getConnection(this.jdbcURL, this.username, this.password);
		}catch(Exception e) {
			log.debug("The database connection failed. Verify the URL");
		}
		return connection;
	}
	

	public Connection getConnection() {
		return connection;
	}
	
	public Statement createState() {
		try {
			statement = connection.createStatement();
		} catch(Exception e) {
			log.debug("The creation of statement failed");
		}
		return statement;
	}
	

	public Statement getStatement() {
		return statement;
	}
	
	public void execRequest(String request) throws SQLException {
		if(request.toUpperCase().startsWith("SELECT")) {
			resultSet = statement.executeQuery(request);
		} else {
			int update = statement.executeUpdate(request);
		}
	}
	
	/**
	 * This method allows to read data from a database
	 * @throws Exception
	 */
	public void readData() throws Exception {
		try {
			connect();
			createState();
			execRequest(this.request);
			getResultSet(resultSet);
		} catch(Exception e) {
			log.debug("The database connection failed. Verify the URL");
		}finally{
			close();
		}
	}
	
	/**
	 * This method allows to print the data from the database.
	 * @param resultSet
	 * @throws Exception
	 */
	public void getResultSet(ResultSet resultSet) throws Exception {
		int nbColomn = resultSet.getMetaData().getColumnCount();
		int[] colomnSizes = new int[nbColomn];
		String line = "", data;
		String delimiterLine = " | ";
		
		for(int i=1; i <= nbColomn;i++) {
			colomnSizes[i-1] = resultSet.getMetaData().getColumnDisplaySize(i);
			data = resultSet.getMetaData().getColumnName(i);
			line += data;
			line += delimiterLine;
			
		}
		System.out.println(line);
		
		while(resultSet.next()){
			line = "";
			for(int i=1; i <= nbColomn;i++) {
				data = resultSet.getString(i);
				line += data;
				line += delimiterLine;
			}
			System.out.println(line);
		}
	}
	
	/**
	 * This method close the connexion to the database.
	 */
	public void close(){
		//we close the connection to the data base 
		try {
			if(resultSet!=null) {
				resultSet.close();
				resultSet = null;
			}
			if(statement!=null) {
				statement.close();
				statement = null;
			}
			if(connection!=null) {
				connection.close();
				connection = null;
			}
			
		} catch(Exception e){
			log.debug("The connection didn't close.");
		}
	}

}
