package com.jdbcReverseEngineering.table;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBTableFactory {
	
	public DBTable creationOfTable(ResultSet table, DatabaseMetaData dbMetaData) throws SQLException {
		String tableName; 
		tableName = table.getString("TABLE_NAME");
		return new DBTable(tableName, dbMetaData);
	}

}
