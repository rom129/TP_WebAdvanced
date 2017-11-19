package com.jdbcReverseEngineering.column;


public class DBColumnNumber extends DBColumn {
	
	protected int columnSize;
	protected String columnDef;

	protected DBColumnNumber(String columnName, String typeName, String isNullable, int columnSize, String columnDef) {
		super(columnName, typeName, isNullable);
		this.columnSize = columnSize;
		this.columnDef = columnDef;
	}
	
	@Override
	public String toSQL() {
        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\t" + super.STR_SYMBOL + super.columnName + super.STR_SYMBOL 
        		+ " " + super.typeName  + "(" + this.columnSize + ")" 
        		+  " " + super.isNullable);

        if (this.columnDef != null) {
			strBuffer.append(" DEFAULT " + this.columnDef + ",\n");
        }else {
        	strBuffer.append(",\n");
        }
        return strBuffer.toString();
    }
		
}
