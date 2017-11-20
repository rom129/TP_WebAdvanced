package com.jdbcTP2;


import java.sql.ResultSet;
import java.sql.Types;

import com.jdbcReverseEngineering.Connexion;
import com.jdbcReverseEngineering.column.DBColumn;
import com.jdbcReverseEngineering.column.DBColumnFactory;
import com.jdbcReverseEngineering.column.DBColumnString;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Connexion.
 */
public class ColumnTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ColumnTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ColumnTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws Exception 
     * @throws SQLException 
     */
    public void testConnexion() throws Exception {
    	Connexion connect = new Connexion("jdbc:mysql://localhost/sakila", "com.mysql.jdbc.Driver", "root", "root", "sakila");
    	DBColumn dbColumn;
    	ResultSet columnResultSet;
		String tableName = "category";
		String columnName = "name"; 
		String sqlType = "VARCHAR";
		
		connect.initialiseConnection();

		columnResultSet = connect.getConnection().getMetaData().getColumns(null, null, tableName, columnName);
		if (columnResultSet.next()) {
			dbColumn = DBColumnFactory.creationOfColumn(columnResultSet);
			assertEquals(dbColumn.getClass().getName(), DBColumnString.class.getName());
			assertEquals(dbColumn.getName(), columnName);
			assertEquals(dbColumn.getSqlType(), sqlType);
		}
		connect.close();
    }
}
