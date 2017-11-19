package com.jdbcReverseEngineering.column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class DBColumnFactory {

	public static DBColumn creationOfColumn(ResultSet column) throws SQLException {
		DBColumn dbColumn = null;
		int sqlType;
				
		String columnName = column.getString("COLUMN_NAME");
		String typeName = column.getString("TYPE_NAME");
        String strNullable = column.getString("IS_NULLABLE");
        String isNullableBool = isNullableBool(strNullable);
        String columnDef = column.getString("COLUMN_DEF");
        int decimalDigit = column.getInt("DECIMAL_DIGITS");
        
        int columnSize = column.getInt("COLUMN_SIZE");
		sqlType = column.getInt("DATA_TYPE");
		
		dbColumn = creationOfColumn(columnName, sqlType, columnSize, isNullableBool,
				typeName, columnDef, decimalDigit);
		
		return dbColumn;
	}
	
	public static DBColumn creationOfColumn(String columnName, int sqlType, int columnSize, String isNullable, 
			String typeName, String columnDef, int decimalDigit) {
		DBColumn dbColumn = new DBColumnDefault(columnName, typeName, isNullable);
		switch(sqlType) {
			//One parameter not String
			case Types.TINYINT : 
			case Types.SMALLINT : 
			case Types.BIGINT :
			case Types.BIT :
			case Types.INTEGER : 
			case Types.DATE :
			case Types.TIME :
				dbColumn =  new DBColumnNumber(columnName, typeName, isNullable, columnSize, columnDef);
				break;
				 
			//Two parameters
			case Types.DECIMAL :
			case Types.FLOAT :
			case Types.DOUBLE :
			case Types.REAL :
			case Types.NUMERIC : 
				dbColumn = new DBColumnTwoParameter(columnName, typeName, isNullable, columnSize, decimalDigit, columnDef);
				break;
				
			// String 	
			case Types.VARCHAR :
			case Types.CHAR :
			case Types.NCHAR :
			case Types.NVARCHAR :
			case Types.BLOB :
			case Types.BINARY :
			case Types.VARBINARY:
			case Types.LONGNVARCHAR :
			case Types.LONGVARBINARY :
			case Types.LONGVARCHAR :
				dbColumn =  new DBColumnString(columnName, typeName, isNullable, columnSize);
				break;
				
			case Types.TIMESTAMP :
				dbColumn = new DBColumnTimeStamp(columnName, typeName, isNullable, columnDef);
			default :
				
		}
		return dbColumn;
	}
	
	/**
	 * 
	 * @param strNullable
	 * @return NullableValue
	 */
	public static String isNullableBool(String strNullable) {
		String NullableValue = "";
		switch(strNullable) {
			case "YES" : NullableValue = "NULL";
				break;
			case "NO" : NullableValue = "NOT NULL";
				break;
			default:
		}
		return NullableValue;
	}
}
