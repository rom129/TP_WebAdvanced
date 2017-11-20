package com.jdbcReverseEngineering.base;

import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdbcReverseEngineering.Connexion;
import com.jdbcReverseEngineering.table.DBTable;
import com.jdbcReverseEngineering.table.DBTableFactory;

public class DBBase {

	protected static Logger log=Logger.getLogger(DBBase.class);
	
	private List<DBTable> tablesList = new LinkedList();
	private String dbName = null;
	private String[] types = {"TABLE"};
	private Connexion connect;
	
	private DatabaseMetaData dbMetaData;
	
	/**
	 * Constructor of DBBase.
	 * @param url
	 * @param driver
	 * @param user
	 * @param password
	 * @param database
	 * @throws Exception
	 */
	public DBBase(String url, String driver, String user, String password, String database) throws Exception {
		this.dbName = database;
		connect = new Connexion(url, driver, user, password, database);
		connect.initialiseConnection();
		dbMetaData = connect.getConnection().getMetaData();
	}
	
	/**
	 * Creation of the Database containing tables
	 * @throws Exception
	 */
	public void createDB() throws Exception {
		String[] types = { "TABLE" };
		ResultSet tables = null;
		try {
			tables = dbMetaData.getTables(null, null, "%", types);
			DBTableFactory tableFact = new DBTableFactory();
			if (tables.next()) {
				while (tables.next()) {
					DBTable dbTable = tableFact.creationOfTable(tables, dbMetaData);
					this.tablesList.add(dbTable);	
				} 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Convert to SQL Format.
	 * @return strBuffer.toString()
	 * @throws Exception
	 */
	public String toSQL() throws Exception {
		createDB();
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("-- Database " + this.dbName + "\n");
		strBuffer.append("CREATE DATABASE " + this.dbName + ";\n\n");
		strBuffer.append("-- Tables\n");
		
		for(DBTable table : this.tablesList) {
			log.debug("there is this table : " + table.getName());
			strBuffer.append(table.toSQL());
		}
		return strBuffer.toString();
	}
}
