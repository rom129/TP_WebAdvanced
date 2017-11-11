package com.jdbcReverseEngineering;
import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter; 
import java.sql.*; 
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ReverseEngineering { 
	
	protected static Logger log=Logger.getLogger(ReverseEngineering.class);
	
	private String hostname;
	private boolean verbose; 
	private static String version = "0.1"; 
	private String schema = null; 
	private Connection conn = null; 
	private DatabaseMetaData databaseMetaData; 
	private String databaseProductVersion = null; 
	private int databaseProductMajorVersion = 0; 
	private int databaseProductMinorVersion = 0; 

	/** 
	 * Default contructor for ReverseEngineering. 
	 */ 
	public ReverseEngineering() { 

	} 

	public void init(Connection conn) throws SQLException { 
		schema=conn.getCatalog(); 
		this.conn=conn; 

		databaseMetaData = conn.getMetaData(); 
		databaseProductVersion = databaseMetaData.getDatabaseProductVersion();
		log.info("The product version is " + databaseProductVersion);
		databaseProductMajorVersion = databaseMetaData.getDatabaseMajorVersion(); 
		log.info("The product major version is " + databaseProductMajorVersion);
		databaseProductMinorVersion = databaseMetaData.getDatabaseMinorVersion(); 
		log.info("The product minor version is " + databaseProductMinorVersion);
	} 

	/**
	 * Connection
	 * @param driver
	 * @param host
	 * @param username
	 * @param password
	 * @param db
	 * @return the connection
	 */
	public Connection connection(String driver, String host, String username, String password,String db) {
		try {
			log.info("The driver is : " + driver);
			log.info("The host is : " + host);
			log.info("The username is : " + username);
			log.info("The password is : " + password);
			log.info("The dbName is : " + db);
			Class.forName(driver);
			conn =  DriverManager.getConnection("jdbc:mysql://"   + host + "/" +  db, username, password);
		}catch(Exception e) {
			log.debug("The database connection failed. Verify the URL");
		}
		return conn;
	}

	/** 
	 * Connect to MySQL server 
	 * 
	 * @param  host      MySQL Server Hostname 
	 * @param  username  MySQL Username 
	 * @param  password  MySQL Password 
	 * @param  db        Default database 
	 */ 
	public void connect(String driver, String host, String username, String password,String db) throws SQLException{ 
		try { 
			init(connection(driver, host, username, password, db)); 
			hostname = host; 
			schema = db; 
			if (verbose){ 
				System.out.println ("Database connection established"); 
			} 
		} 
		catch (SQLException se){ 
			throw se; 
		} 
		catch (Exception e) 
		{ 
			System.err.println ("Cannot connect to database server"); 
		} 
	} 
	
	/**
	 * Creation of the database.
	 * @param database
	 * @return
	 */
	public String dumpCreateDatabase(String database) { 
		String createDatabase = null; 
		try { 
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery("SHOW CREATE DATABASE "  + database); 
			ResultSet rs = s.getResultSet(); 
			while (rs.next ()) { 
				createDatabase = rs.getString("Create Database") + ";"; 
			} 
		} catch (SQLException e) { 

		} 
		return createDatabase; 
	} 

	/**
	 * Creation of all tables of the database including views
	 * @param out
	 * @throws SQLException
	 * @throws IOException
	 */
	public void dumpAllTables(BufferedWriter out) throws SQLException,IOException { 
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("show tables"); 
		while(rs.next()) { 
			String tableName=rs.getString(1); 
			log.info("Table or View write in the new .sql : " + tableName);
			out.write(dumpCreateTable(tableName)); 
			this.dumpTable(out,tableName); 
		} 

	} 
	
	/**
	 * Creation of tables.
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public String dumpCreateTable(String table) throws SQLException { 
		return dumpCreateTable(schema,table); 
	} 

	/**
	 * Creation of tables.
	 * @param schema
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public String dumpCreateTable(String schema, String table) throws SQLException { 
		String createTable = "--\n-- Table structure for table `"  + table  + 
				"`\n--\n\n"; 
		try{ 
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery("SHOW CREATE TABLE "  + schema  + "."  + table); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) { 
				createTable  = rs.getString("Create Table")  + ";"; 
			} 
		} catch (SQLException e) { 
			Statement s2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s2.executeQuery("SHOW CREATE VIEW "  + schema  + "."  + table); 
			ResultSet rs = s2.getResultSet (); 
			while (rs.next ()) { 
				createTable  = rs.getString("Create View")  + ";"; 
			} 
		} 
		return createTable; 
	} 
	
	/**
	 * This method allows to write the insertion of datas. 
	 * @param out
	 * @param table
	 */
	public void dumpTable(BufferedWriter out, String table){ 
		try{ 
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SELECT /*!40001 SQL_NO_CACHE */ * FROM "+table); 
			ResultSet rs = s.getResultSet (); 
			ResultSetMetaData rsMetaData = rs.getMetaData(); 
			if (rs.last()){ 
				out.write("--\n-- Dumping data for table `" +  table+ "`\n--\n\n"); 
				rs.beforeFirst(); 
			} 
			
			int columnCount = rsMetaData.getColumnCount(); 
			String prefix = new String("INSERT INTO " +  table +  " ("); 
			for (int i = 1; i <= columnCount; i ++ ) { 
				if (i == columnCount){ 
					prefix  = rsMetaData.getColumnName(i)   + ") VALUES("; 
				}else{ 
					prefix  = rsMetaData.getColumnName(i) +  ","; 
				} 
			} 
			
			String postfix = new String(); 
			int count = 0; 
			while (rs.next()) { 
				postfix = ""; 
				for (int i = 1; i <= columnCount; i++) { 
					if (i == columnCount){ 
						postfix  = "unhex('" + escapeString(rs.getBytes(i)).toString() +  "'));\n"; 
					}else{ 
						try{ 
							postfix  = "unhex('" + escapeString(rs.getBytes(i)).toString() +  "'),"; 
						}catch (Exception e){ 
							postfix  = "NULL,"; 
						} 
					} 
				} 
				out.write(prefix  + postfix); 
				count++; 
			} 
			
			rs.close (); 
			s.close(); 
		}catch(IOException e){ 
			System.err.println (e.getMessage()); 
		}catch(SQLException e){ 
			System.err.println (e.getMessage()); 
		} 
	} 
	
	/** 
	 * Escape string ready for insert via mysql client 
	 * 
	 * @param  bIn       String to be escaped passed in as byte array 
	 * @return bOut      MySQL compatible insert ready ByteArrayOutputStream 
	 */ 
	private ByteArrayOutputStream escapeString(byte[] bIn){ 
		int numBytes = bIn.length;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes); 
		for (int i = 0; i < numBytes;++i) { 
			byte b = bIn[i]; 

			switch (b) { 
			case 0: /* Must be escaped for 'mysql' */ 
				bOut.write('\\'); 
				bOut.write('0'); 
				break; 

			case '\n': /* Must be escaped for logs */ 
				bOut.write('\\'); 
				bOut.write('n'); 
				break; 

			case '\r': 
				bOut.write('\\'); 
				bOut.write('r'); 
				break; 

			case '\\': 
				bOut.write('\\'); 
				bOut.write('\\'); 

				break; 

			case '\'': 
				bOut.write('\\'); 
				bOut.write('\''); 

				break; 

			case '"': /* Better safe than sorry */ 
				bOut.write('\\'); 
				bOut.write('"'); 
				break; 

			case '\032': /* This gives problems on Win32 */ 
				bOut.write('\\'); 
				bOut.write('Z'); 
				break; 

			default: 
				bOut.write(b); 
			} 
		} 
		return bOut; 
	} 

	protected static final byte[] Hexhars = { 

			'0', '1', '2', '3', '4', '5', 
			'6', '7', '8', '9', 'a', 'b', 
			'c', 'd', 'e', 'f' 
	}; 
	
	/**
	 * Creation of the Headers of the file.
	 * @return
	 */
	public String getHeader(){ 
		return "-- BinaryStor MySQL Dump "  + version +"\n--\n-- Host: " + hostname + "    "+"Database: " +schema+
				"\n--------------------------------------------------------\n-- Server Version: " +  databaseProductVersion +  "\n"; 
	} 

	/** 
	 * Parse command line arguments and run ReverseEngineering.
	 * @param  args  Command line arguments 
	 */ 
	public void doMain(String[] args) throws IOException { 
		//String schema = "sakila";
		try{ 
			//Create temporary file to hold SQL output. 
			File temp = File.createTempFile(args[4], ".sql"); 
			log.info("The new sakila file is here : " + temp.getAbsolutePath());
			BufferedWriter out = new BufferedWriter(new FileWriter(temp)); 
			this.connect(args[0], args[1], args[2], args[3], args[4]); 
			out.write(getHeader()); 
			System.out.println("---------------------------------------------------------------------------------");
			this.dumpCreateDatabase(args[4]);
			this.dumpAllTables(out);
			out.flush(); 
			out.close(); 
			this.cleanup(); 
		} 
		catch (SQLException se){ 
			System.err.println (se.getMessage()); 
		} 
	} 
	
	/**
	 * Close the connection.
	 * @return 1
	 */
	public int cleanup(){ 
		try 
		{ 
			conn.close();
			conn = null;
			if (verbose){ 
				System.out.println ("Database connection terminated"); 
			} 
		} 
		catch (Exception e) { /* ignore close errors */ } 
		return 1; 
	} 
	
	public String getSchema() {
		return this.schema;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
} 