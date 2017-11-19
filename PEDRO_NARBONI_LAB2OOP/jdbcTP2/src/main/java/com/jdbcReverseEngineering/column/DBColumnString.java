package com.jdbcReverseEngineering.column;


public class DBColumnString extends DBColumn {
	
	protected  int columnSize;

	protected DBColumnString(String columnName, String typeName, String isNullable, int columnSize) {
		super(columnName, typeName, isNullable);
		this.columnSize = columnSize;
	}
	
	@Override
	public String toSQL() {
        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\t" + super.STR_SYMBOL + super.columnName + super.STR_SYMBOL 
        		+ " " + super.typeName  + "(" + this.columnSize + ")" 
        		+ " " + super.isNullable + ",\n");
        return strBuffer.toString();
    }

}
