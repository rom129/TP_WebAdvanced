package com.jdbcReverseEngineering.column;


public class DBColumnTimeStamp extends DBColumn {
	
	protected  int columnSize;
	protected  String columnDef;

	protected DBColumnTimeStamp(String columnName, String typeName, String isNullable, String columnDef) {
		super(columnName, typeName, isNullable);
		this.columnDef = columnDef;
	}
	
	@Override
	public String toSQL() {
        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("\t" + super.STR_SYMBOL + super.columnName + super.STR_SYMBOL 
        		+ " " + super.typeName  + " " + super.isNullable);
        		
		if(super.typeName.toUpperCase().equals("TIMESTAMP")) {
			strBuffer.append(" DEFAULT " + this.columnDef + " ON UPDATE " + this.columnDef 
					+ "\n)ENGINE=InnoDB DEFAULT CHARSET=utf8;\n");
		} else {
			strBuffer.append(",\n") ;
			}
        return strBuffer.toString();
    }

}
