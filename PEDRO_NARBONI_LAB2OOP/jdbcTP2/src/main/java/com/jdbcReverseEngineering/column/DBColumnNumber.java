package com.jdbcReverseEngineering.column;


public class DBColumnNumber extends DBColumn {
	
	protected int columnSize;
	protected String columnDef;
	protected String unsigned;

	protected DBColumnNumber(String columnName, String typeName, String isNullable, int columnSize, String columnDef, boolean unsigned) {
		super(columnName, typeName, isNullable);
		this.columnSize = columnSize;
		this.columnDef = columnDef;
		this.unsigned = unsigned? "UNSIGNED" : ""; 
	}
	
	@Override
	public String toSQL() {
        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\t" + super.STR_SYMBOL + super.columnName + super.STR_SYMBOL 
        		+ " " + super.typeName  + "(" + this.columnSize + ")" + " " + this.unsigned 	
        		+  " " + super.isNullable);

        if (this.columnDef != null) {
			strBuffer.append(" DEFAULT " + this.columnDef + ",\n");
        }else {
        	strBuffer.append(",\n");
        }
        return strBuffer.toString();
    }
		
}
