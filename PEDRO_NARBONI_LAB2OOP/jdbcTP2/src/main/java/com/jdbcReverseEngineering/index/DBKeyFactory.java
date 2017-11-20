package com.jdbcReverseEngineering.index;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBKeyFactory {
	
	public static DBKey create(String keyName, String type, ResultSet result) throws SQLException {

		DBKey key = null;
		String columnName;
		boolean isUniqueKey;

		switch (type) {
		case "FOREIGN" :
			break;

		case "PRIMARY" :
			columnName = result.getString("COLUMN_NAME");
			key = new DBPrimaryKey(keyName, columnName);
			break;

		case "INDEX" :
			columnName = result.getString("COLUMN_NAME");
			isUniqueKey = result.getBoolean("NON_UNIQUE");
			key = createUniqueKeyOrKey(keyName, columnName, isUniqueKey);
			break;

		default :
			break;

		}
		return key;
	}
	
	public static DBKey createUniqueKeyOrKey(String keyName, String columnName, boolean isUniqueKey) throws SQLException {
		DBKey dbKey = null;
		if(isUniqueKey == true) {
			dbKey = new DBKey(keyName, columnName);
		} else {
			dbKey = new DBUniqueKey(keyName, columnName);
		}
		return dbKey;
	}
	
	public static void addKeyColumn(DBKey key, String type, ResultSet result) throws SQLException {
		String columnName;
		switch (type) {
			case "FOREIGN" :
				break;
			case "PRIMARY" :
			case "INDEX" :
				columnName = result.getString("COLUMN_NAME");
				key.addIndexColumn(columnName);
				break;
			default :
				break;
		}

		

	}
}
