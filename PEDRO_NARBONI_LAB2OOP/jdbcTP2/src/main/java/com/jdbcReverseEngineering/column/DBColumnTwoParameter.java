package com.jdbcReverseEngineering.column;


public class DBColumnTwoParameter extends DBColumn {

	protected int columnSize;
	protected int decimalDigit;
	protected String columnDef;

	protected DBColumnTwoParameter(String columnName, String typeName, String isNullable, int columnSize, int decimalDigit, String columnDef) {
		super(columnName, typeName, isNullable);
		this.columnSize = columnSize;
		this.decimalDigit = decimalDigit;
		this.columnDef = columnDef;
	}

	@Override
	public String toSQL() {
		final StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\t" + super.STR_SYMBOL + super.columnName + super.STR_SYMBOL 
				+ " " + super.typeName  + "(" + this.columnSize + "," + this. decimalDigit + ")" 
				+  " " + super.isNullable);
		if (this.columnDef != null) {
			strBuffer.append(" DEFAULT " + this.columnDef + ",\n");
        }else {
        	strBuffer.append(",\n");
        }

		return strBuffer.toString();
	}
}
