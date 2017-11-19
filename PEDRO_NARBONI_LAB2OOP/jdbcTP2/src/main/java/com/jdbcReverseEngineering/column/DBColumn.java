package com.jdbcReverseEngineering.column;

public abstract class DBColumn {
	

	protected String columnName;
	protected String typeName;
	//private int columnSize;
	protected String isNullable;
	protected final String STR_SYMBOL = "`";
	
	protected DBColumn(String columnName, String typeName, String isNullable) {
        this.columnName = columnName;
        this.typeName = typeName;
        this.isNullable = isNullable;
    }

	public String getName() {
		return columnName;
	}
	
	public String getSqlType() {
		return typeName;
	}

	public void setSqlType(String typeName) {
		this.typeName = typeName;
	}
	
	public String toSQL() {
        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\t" + STR_SYMBOL + this.columnName + STR_SYMBOL + " " + this.typeName  + " " + this.isNullable + "\n");
        return strBuffer.toString();
    }
}
