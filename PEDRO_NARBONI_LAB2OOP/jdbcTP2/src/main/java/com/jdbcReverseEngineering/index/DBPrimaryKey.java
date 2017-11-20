package com.jdbcReverseEngineering.index;

public class DBPrimaryKey extends DBKey {

	public DBPrimaryKey(String keyName, String columnName) {
		super(keyName, columnName);
	}

	@Override
	public String toSQL() {
		final StringBuffer sb = new StringBuffer();
		sb.append("PRIMARY KEY (");
		for (String columnName : getColumnNames()) {
			sb.append(super.STR_SYMBOL + columnName + super.STR_SYMBOL + ", ");
		}
		sb.deleteCharAt(sb.toString().lastIndexOf(','));
		sb.append(")");
		return sb.toString();
	}

}
