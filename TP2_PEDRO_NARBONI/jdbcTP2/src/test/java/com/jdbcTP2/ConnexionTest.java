package com.jdbcTP2;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Connexion.
 */
public class ConnexionTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConnexionTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ConnexionTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
    	String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    	String connectionString = "jdbc:mysql://localhost/sakila";
    	String username="root";
    	String password="root";
    	String request="";
    	String MYSQL_URL = connectionString;
    	Connexion dao = new Connexion(MYSQL_DRIVER,MYSQL_URL,username,password,request);
    	assertNotNull(dao.connect());
    	assertNotNull(dao.createState());
    	dao.close();
    	assertNull(dao.getConnection());
    	assertNull(dao.getStatement());
    }
}
