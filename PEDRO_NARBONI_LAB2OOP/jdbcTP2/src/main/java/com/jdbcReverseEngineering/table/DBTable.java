package com.jdbcReverseEngineering.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.DatabaseMetaData;

import org.apache.log4j.Logger;

import com.jdbcReverseEngineering.base.DBBase;
import com.jdbcReverseEngineering.column.DBColumn;
import com.jdbcReverseEngineering.column.DBColumnFactory;

public class DBTable {
	
	protected static Logger log=Logger.getLogger(DBTable.class);
	
	private String tableName;
	private List<DBColumn> columnsList = new LinkedList<DBColumn>();
	
	private DatabaseMetaData dbMetaData;
	
	public DBTable(String tablesName, DatabaseMetaData dbMetaData) throws SQLException {
		this.tableName = tablesName;
		this.dbMetaData = dbMetaData;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String toSQL() throws Exception {
		StringBuffer strBuffer = new StringBuffer();
		ResultSet columns = null;
		strBuffer.append("\n\n-- " + this.tableName);
		strBuffer.append("\nCREATE TABLE " + this.tableName + " (\n");

		columns = dbMetaData.getColumns(null, null, this.tableName, null);
		//printResult(columns);
		DBColumnFactory dbColumnFact = new DBColumnFactory();
		if(columns.next()) {
			do {
				DBColumn dbColumn = dbColumnFact.creationOfColumn(columns);
				columnsList.add(dbColumn);	
			} while (columns.next());
			for(DBColumn column : columnsList) {
				log.info("there is this column : " + column.getName());
				strBuffer.append(column.toSQL());
			}
		}
		
		return strBuffer.toString();
    }
	
	/**
	 * getName()
	 * @return tableName
	 */
	public String getName() {
		return tableName;
	}
	
	/***************
	 * 
	 *  TOUTES LES METHODES CI-DESSOUS SONT A ENLEVER APRES LA VAILDATION DES TESTS
	 */
	public void printResult(ResultSet resultSet) throws Exception {
		int nbCol = resultSet.getMetaData().getColumnCount();
		int[] colSizes = new int[nbCol];
		String line = "", delimiterLine;

		line = getHeader(resultSet, colSizes, nbCol);
		System.out.println(line);
		
		delimiterLine = String.format("%1$" + line.length() + "s", "")
				.replace(" ", /*LINE_DELIMITER*/ "/");
		System.out.println(delimiterLine);
		
		while (resultSet.next()) {
			line = getLine(resultSet, colSizes, nbCol);
			System.out.println(line);
		}
	}
	
	
	private String getLine(ResultSet resultSet, int[] colSizes, int nbCol) throws Exception {
		String data="", line="";
		for (int i = 1; i <= nbCol; ++i) {
			data = resultSet.getString(i);
			line += String.format("%1$" + colSizes[i - 1] + "s", data);
			if (i < nbCol) {
				line = addColumnDelimiter(line);
			}
		}
		return line;
	}
	
	private String getHeader(ResultSet resultSet, int[] colSizes, int nbCol) throws Exception {
		String data="", line="";
		for (int i = 1; i <= nbCol; ++i) {
			colSizes[i - 1] = resultSet.getMetaData().getColumnDisplaySize(i);
			if (colSizes[i-1] < resultSet.getMetaData().getColumnName(i).length()) {
				colSizes[i-1] = resultSet.getMetaData().getColumnName(i).length();
			}
			//System.out.println(resultSet.getMetaData().getColumnName(i) + " " + resultSet.getMetaData().getColumnDisplaySize(i));

			data = resultSet.getMetaData().getColumnName(i);
			line += String.format("%1$" + colSizes[i - 1] + "s", data);
			if (i < nbCol) {
				line = addColumnDelimiter(line);
			}
		}
		return line;
	}
	
	private String addColumnDelimiter(String line) {
		line += " "+ "/" +" ";
		return line;
	}
}
