package com.jdbcReverseEngineering.index;

public class DBUniqueKey extends DBKey {

	/**
	 * Constructor
	 * @param keyName
	 * @param columnName
	 */
	public DBUniqueKey(String keyName, String columnName) {
		super(keyName, columnName);
	}


	@Override
	public String toSQL() {
		final StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("UNIQUE KEY " + super.STR_SYMBOL + super.getName() + super.STR_SYMBOL + "(");
		for (String columnName : super.getColumnNames()) {
			strBuffer.append(super.STR_SYMBOL + columnName + super.STR_SYMBOL + ", ");
		}
		strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
		strBuffer.append(")");
		return strBuffer.toString();
	}
}
