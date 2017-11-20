package com.jdbcReverseEngineering.index;

import java.util.LinkedList;
import java.util.List;

public class DBForeignKey extends DBKey {

	protected List<String> pkColumnName;
	protected String pkTableName;
	
	/**
	 * Constructor.
	 * @param keyName
	 * @param fkColumnName
	 * @param pkColumnName
	 * @param pkTableName
	 */
	public DBForeignKey(String keyName, String fkColumnName, String pkColumnName, String pkTableName) {
		super(keyName, fkColumnName);
		this.pkColumnName = new LinkedList<String>();
		this.pkColumnName.add(pkColumnName);
		this.pkTableName = pkTableName;
	}
	
	/**
	 * getPrimaryColumnNames()
	 * @return pkColumnName
	 */
	public List<String> getPrimaryColumnNames() {
		return pkColumnName;
	}

	/**
	 * Add primary key to pkColumnName.
	 * @param columnName
	 */
	public void addPrimaryKeyColumn(String columnName) {
		pkColumnName.add(columnName);
	}
	
	@Override
	public String toSQL() {
		final StringBuffer strBuffer = new StringBuffer();
		if (super.getName() != null && ! super.getName().equals("")) {
			strBuffer.append("CONSTRAINT " + super.STR_SYMBOL + super.getName() + super.STR_SYMBOL + " ");
		}
		strBuffer.append("FOREIGN KEY " + "(");
		for (String columnName : getColumnNames()) {
			strBuffer.append(super.STR_SYMBOL + columnName + super.STR_SYMBOL + ", ");
		}
		strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
		strBuffer.append(")");
		strBuffer.append(" REFERENCES " + super.STR_SYMBOL + super.getName() + super.STR_SYMBOL + "(");
		for (String columnName : getPrimaryColumnNames()) {
			strBuffer.append(super.STR_SYMBOL + columnName + super.STR_SYMBOL + ", ");
		}
		strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
		strBuffer.append(")");
		return strBuffer.toString();

	}

}
