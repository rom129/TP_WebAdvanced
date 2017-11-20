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
	private List<DBKey> foreignKeysList = new LinkedList<DBKey>();
	private DatabaseMetaData dbMetaData;
	
	/**
	 * Allows to know when we don't want to print a key.
	 */
	private List<String> notAllowedIndexes = new LinkedList<String>() {
		{
			add("PRIMARY");
			add("INDEX");
			add("FOREIGN");
		}
	};
	
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
		
		columns = dbMetaData.getColumns(null, null, getName(), null);
		DBColumnFactory dbColumnFact = new DBColumnFactory();
		if(columns.next()) {
			while (columns.next()) {
				DBColumn dbColumn = dbColumnFact.creationOfColumn(columns);
				columnsList.add(dbColumn);	
			}
		}
    }
	
	/**
	 * Add all primary keys to the idexesList.
	 * @throws SQLException
	 */
	private void addPrimaryKeysInIndexList() throws SQLException {

		DBKey key = null;
		String primaryKeyName = null, lastPrimaryKeyName = null;
		ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, getName());
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
	
	/**
	 * Add unique key and keys in the indexes List.
	 * @throws SQLException
	 */
	private void addIndexInIndexLists() throws SQLException {
		DBKey key = null;
		String keyName = null, lastkeyName = null;
		ResultSet indexesInfo = dbMetaData.getIndexInfo(null, null, getName(), false, false);
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
	 * Add Foreign keys in the foreignKeys List. 
	 * @throws SQLException
	 */
	private void addForeignKeysInList() throws SQLException {
		DBKey key = null;
		String foreignName = null, lastForeignName = null;
		ResultSet foreignKeys = dbMetaData.getImportedKeys(null, null, getName());
		while (foreignKeys.next()) {
			foreignName = foreignKeys.getString("FK_NAME");
			if (foreignName == null || !foreignName.equals(lastForeignName)) {
				key = DBKeyFactory.create(foreignName, "FOREIGN", foreignKeys);
				foreignKeysList.add(key);
			} else {
				DBKeyFactory.addKeyColumn(key, "FOREIGN", foreignKeys);
			}
			lastForeignName = foreignName;
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
		addForeignKeysInList();
		
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(createTabletoSQL());
		strBuffer.append("\n\n");
		strBuffer.append(indexesToSQL());
		strBuffer.append("\n\n");
		strBuffer.append(foreignKeysToSQL());
		return strBuffer.toString();
	}
	
	/**
	 * Return a SQL format.
	 * @return strBuffer.toString()
	 * @throws Exception
	 */
	public String createTabletoSQL() throws Exception {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\n\n-- " + writeTableNameWithQuote());
		strBuffer.append("\nCREATE TABLE " + writeTableNameWithQuote() + " (\n");
		for(DBColumn column : columnsList) {
			log.debug("there is this column : " + column.getName() + " in the table " + getName());
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
        strBuffer.append("--\n-- Index pour la table " + writeTableNameWithQuote() +"\n--\n");
        strBuffer.append("ALTER TABLE " + writeTableNameWithQuote() + " \n");
        for (DBKey key : indexesList) {
        	strBuffer.append("\tADD ");
        	log.debug("there is this key : " + key.getName() + " in the table " + getName());
        	strBuffer.append(key.toSQL());
        	strBuffer.append(",\n");
        }
    	strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
    	strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf('\n'));
        strBuffer.append(";\n\n");
        return strBuffer.toString();
	}
	
	/**
	 * Return a SQL format.
	 * @return strBuffer.toString()
	 */
	public String foreignKeysToSQL() {
		final StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("--\n-- Contraintes pour la table " + writeTableNameWithQuote() +"\n--\n");
		if (foreignKeysList.size() > 0) {
			strBuffer.append("ALTER TABLE " + writeTableNameWithQuote() + " \n");
			for (DBKey key : foreignKeysList) {
				strBuffer.append("\tADD ");
				strBuffer.append(key.toSQL());
				log.debug("there is this foreignkey : " + key.getName() + " in the table " + getName());
				strBuffer.append(",\n");
			}
			strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf(','));
			strBuffer.deleteCharAt(strBuffer.toString().lastIndexOf('\n'));

			strBuffer.append(";\n\n");
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
	
	/**
	 * Format to print a name.
	 * @return str
	 */
	public String writeTableNameWithQuote() {
		String str = "`" + getName() + "`";
		return str;
	}
}
