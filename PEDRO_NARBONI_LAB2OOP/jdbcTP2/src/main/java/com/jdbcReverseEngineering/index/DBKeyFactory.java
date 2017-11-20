package com.jdbcReverseEngineering.index;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBKeyFactory {
	
	/**
	 * create a type of Key (PrimaryKey, ForeignKey, UniqueKey or just Key).
	 * @param keyName
	 * @param type
	 * @param result
	 * @return key
	 * @throws SQLException
	 */
	public static DBKey create(String keyName, String type, ResultSet result) throws SQLException {

		DBKey key = null;
		String columnName;
		boolean isUniqueKey;

		switch (type) {
		case "FOREIGN" : 
			key = createForeignKey(keyName, result);
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
	
	/**
	 * Create UniqueKey Or Key.
	 * @param keyName
	 * @param columnName
	 * @param isUniqueKey
	 * @return dbKey
	 * @throws SQLException
	 */
	public static DBKey createUniqueKeyOrKey(String keyName, String columnName, boolean isUniqueKey) throws SQLException {
		DBKey dbKey = null;
		if(isUniqueKey == true) {
			dbKey = new DBKey(keyName, columnName);
		} else {
			dbKey = new DBUniqueKey(keyName, columnName);
		}
		return dbKey;
	}
	
	/**
	 * Add Key to Columns
	 * @param key
	 * @param type
	 * @param result
	 * @throws SQLException
	 */
	public static void addKeyColumn(DBKey key, String type, ResultSet result) throws SQLException {
		String columnName;
		String pkColumnName;
		switch (type) {
			case "FOREIGN" :
				columnName = result.getString("FKCOLUMN_NAME");
				pkColumnName = result.getString("PKCOLUMN_NAME");
				key.addIndexColumn(columnName);
				((DBForeignKey) key).addPrimaryKeyColumn(pkColumnName);
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
	
	/**
	 * create ForeignKey.
	 * @param keyName
	 * @param result
	 * @return dbKey
	 * @throws SQLException
	 */
	public static DBKey createForeignKey(String keyName, ResultSet result) throws SQLException {
		DBKey dbKey = null;
		String fkColumnName = result.getString("FKCOLUMN_NAME");
		String pkColumnName = result.getString("PKCOLUMN_NAME");
		String pkTableName = result.getString("PKTABLE_NAME");
		dbKey = new DBForeignKey(keyName, fkColumnName, pkColumnName, pkTableName);
		return dbKey;
	}
}
