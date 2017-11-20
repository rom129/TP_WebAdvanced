package com.jdbcReverseEngineering.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.DatabaseMetaData;

import org.apache.log4j.Logger;

import com.jdbcReverseEngineering.column.DBColumn;
import com.jdbcReverseEngineering.column.DBColumnFactory;
import com.jdbcReverseEngineering.index.DBKey;
import com.jdbcReverseEngineering.index.DBKeyFactory;

public class DBTable {
	
	protected static Logger log=Logger.getLogger(DBTable.class);
	
	private String tableName;
	private List<DBColumn> columnsList = new LinkedList<DBColumn>();
	private List<DBKey> indexesList = new LinkedList<DBKey>();
	
	private List<String> notAllowedIndexes = new LinkedList<String>() {
		{
			add("PRIMARY");
			add("INDEX");
			add("FOREIGN");
		}
	};
	
	
	private DatabaseMetaData dbMetaData;
	
	/**
	 * Constructor of DBTable.
	 * @param tablesName
	 * @param dbMetaData
	 * @throws SQLException
	 */
	public DBTable(String tablesName, DatabaseMetaData dbMetaData) throws SQLException {
		this.tableName = tablesName;
		this.dbMetaData = dbMetaData;
	}
	
	/**
	 * Create a list with all columns.
	 * @throws SQLException
	 */
	public void createColumnsInList() throws SQLException {
		ResultSet columns = null;
		
		columns = dbMetaData.getColumns(null, null, this.tableName, null);
		DBColumnFactory dbColumnFact = new DBColumnFactory();
		if(columns.next()) {
			do {
				DBColumn dbColumn = dbColumnFact.creationOfColumn(columns);
				columnsList.add(dbColumn);	
			} while (columns.next());
		}
    }
	
	/**
	 * Add all primary keys to the idexesList.
	 * @throws SQLException
	 */
	private void addPrimaryKeysInIndexList() throws SQLException {

		DBKey key = null;
		String primaryKeyName = null, lastPrimaryKeyName = null;
		ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, this.tableName);
		while (primaryKeys.next()) {
			primaryKeyName = primaryKeys.getString("PK_NAME");
			if(notAllowedIndexes.contains(primaryKeyName)) {
				primaryKeyName = "";
			}
			if (lastPrimaryKeyName ==null || !lastPrimaryKeyName.equals(primaryKeyName)) {
				key = DBKeyFactory.create(primaryKeyName, "PRIMARY", primaryKeys);
				indexesList.add(key);
			}
			lastPrimaryKeyName = primaryKeyName;
		}

	}
	
	
	private void addIndexInIndexLists() throws SQLException {
		DBKey key = null;
		String keyName = null, lastkeyName = null;
		ResultSet indexesInfo = dbMetaData.getIndexInfo(null, null, this.tableName, false, false);
		while (indexesInfo.next()) {
			keyName = indexesInfo.getString("INDEX_NAME");
			if(!notAllowedIndexes.contains(keyName)) {
				if (keyName !=null && !keyName.equals(lastkeyName)) {
					key = DBKeyFactory.create(keyName, "INDEX", indexesInfo);
					indexesList.add(key);
				}
				else {
					DBKeyFactory.addKeyColumn(key, "INDEX", indexesInfo);
				}
				lastkeyName = keyName;
			}
		}
	}
	
	
	/**
	 * Return a SQL format.
	 * @return strBuffer.toString()
	 * @throws Exception
	 */
	public String toSQL() throws Exception {
		createColumnsInList();
		addPrimaryKeysInIndexList();
		addIndexInIndexLists();
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(createTabletoSQL());
		strBuffer.append("\n\n");
		strBuffer.append(indexesToSQL());
		return strBuffer.toString();
	}
	
	/**
	 * Return a SQL format.
	 * @return strBuffer.toString()
	 * @throws Exception
	 */
	public String createTabletoSQL() throws Exception {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\n\n-- " + this.tableName);
		strBuffer.append("\nCREATE TABLE " + this.tableName + " (\n");
		for(DBColumn column : columnsList) {
			log.info("there is this column : " + column.getName());
			strBuffer.append(column.toSQL());
		}
		return strBuffer.toString();
	}
	
	/**
	 * Return a SQL format.
	 * @return strBuffer.toString()
	 */
	public String indexesToSQL() {

        final StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("--\n-- Index pour la table " + this.tableName +"\n--\n");
        strBuffer.append("ALTER TABLE " + this.tableName + " \n");
        for (DBKey key : indexesList) {
        	strBuffer.append("\tADD ");
        	strBuffer.append(key.toSQL());
        	strBuffer.append(",\n");
        }
        if (indexesList.size()>0) {
        	strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
        	strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf('\n'));
        }
        strBuffer.append(";\n\n");
        return strBuffer.toString();
	}
	
	/**
	 * getName()
	 * @return tableName
	 */
	public String getName() {
		return tableName;
	}
}
