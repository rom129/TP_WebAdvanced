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
	
	public DBBase(String url, String driver, String user, String password, String database) throws Exception {
		this.dbName = database;
		connect = new Connexion(url, driver, user, password, database);
		connect.initialiseConnection();
		dbMetaData = connect.getConnection().getMetaData();
	}

	public String toSQL() throws Exception {
		String[] types = { "TABLE" };
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("CREATE DATABASE " + this.dbName);
		ResultSet tables = null;
		try {
			tables = dbMetaData.getTables(null, null, "%", types);
			DBTableFactory tableFact = new DBTableFactory();
			if (tables.next()) {
				do {
					DBTable dbTable = tableFact.creationOfTable(tables, dbMetaData);
					tablesList.add(dbTable);	
				} while (tables.next());
				for(DBTable table : tablesList) {
					log.info("there is this table : " + table.getName());
					strBuffer.append(table.toSQL());
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		connect.closeResultSet(tables);
		return strBuffer.toString();
	}
}
