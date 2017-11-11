package com.jdbcTP2;

import java.sql.SQLException;

import com.jdbcReverseEngineering.ReverseEngineering;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Connexion.
 */
public class ReverseEngineeringTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReverseEngineeringTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ReverseEngineeringTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws SQLException 
     */
    public void testReverseEngineering() throws SQLException {
    	String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    	String MYSQL_HOST = "localhost";
    	String MYSQL_USERNAME = "root";
    	String MYSQL_PASSWORD = "root";
    	String MYSQL_SCHEMA = "sakila";
    	
    	ReverseEngineering re = new ReverseEngineering();
    	assertNotNull(re.connection(MYSQL_DRIVER, MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD, MYSQL_SCHEMA));
    	re.connect(MYSQL_DRIVER, MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD, MYSQL_SCHEMA);
    	assertNotNull(re.getSchema());
    	assertEquals("sakila", re.getSchema());
    	
    	re.cleanup();
    	assertNull(re.getConnection());
    }
}
