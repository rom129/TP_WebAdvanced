package com.jdbcReverseEngineering.index;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class DBKey {
	
	protected static Logger log=Logger.getLogger(DBKey.class);
	
	private String keyName;
	private List<String> columnNames;
	protected final String STR_SYMBOL = "`";

	/**
	 * Constructor.
	 * @param keyName
	 * @param columnName
	 */
	public  DBKey(String keyName, String columnName) {
		this.keyName = keyName;
		columnNames = new LinkedList<String>();
		addIndexColumn(columnName);
	}

	/**
	 * Return the keyName 
	 * @return keyName 
	 */

	public String getName() {
		return keyName;
	}
	
	/**
	 * Return the index in SQL format
	 * @return
	 */
	public String toSQL() {
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("KEY " + STR_SYMBOL + this.getName() + STR_SYMBOL + "(");
		for (String columnName : getColumnNames()) {
			stringBuffer.append(STR_SYMBOL + columnName + STR_SYMBOL + ", ");
		}
		stringBuffer.deleteCharAt(stringBuffer.toString().lastIndexOf(','));
		stringBuffer.append(")");
		return stringBuffer.toString();
	}
	
	/**
	 * getColumnNames()
	 * @return columnNames
	 */
	public List<String> getColumnNames() {
		return columnNames;
	}
	
	/**
	 * addIndexColumn()
	 * @param columnName
	 */
	public void addIndexColumn(String columnName) {
		columnNames.add(columnName);
	}

}
