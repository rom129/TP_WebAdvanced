package com.jdbcTP2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connexion {

	enum TestTableColumns{
		actor_id,last_name;
	}
	private final String jdbcDriverStr;
	private final String jdbcURL;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	public Connexion(String jdbcDriverStr, String jdbcURL){
		this.jdbcDriverStr = jdbcDriverStr;
		this.jdbcURL = jdbcURL;
	}
	public void readData() throws Exception {
		try {
			Class.forName(jdbcDriverStr);
			connection =  DriverManager.getConnection(jdbcURL);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT last_name FROM actor;");
			getResultSet(resultSet);
			//			preparedStatement = connection.prepareStatement("insert into school.salary values (default,?)");
			//			preparedStatement.setString(1,"insert test from java");
			preparedStatement.executeUpdate();
		}finally{
			close();
		}
	}
	private void getResultSet(ResultSet resultSet) throws Exception {
		while(resultSet.next()){
			//Integer id = resultSet.getInt(TestTableColumns.actor_id.toString());
			String lastName = resultSet.getString(TestTableColumns.last_name.toString());
			//System.out.println("id: "+id);
			System.out.println("Last Name : "+lastName);
		}
	}
	private void close(){
		//we close the connection to the data base 
		try {
			if(resultSet!=null) resultSet.close();
			if(statement!=null) statement.close();
			if(connection!=null) connection.close();
		} catch(Exception e){}
	}

}
