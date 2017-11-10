package com.jdbcReverseEngineering;
import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter; 
import java.sql.*; 
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap; 
import java.util.Map;
public class ReverseEngineering { 
	//Command Line Arguments
	private boolean help;
	private String hostname="localhost";
	private String username="root";
	private String password="root";
	// receives other command line parameters than options
	private List<String> arguments = new ArrayList<String>();
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

	/** 
	 * Create a new instance of ReverseEngineering using default database. 
	 * 
	 * @param  host      MySQL Server Hostname 
	 * @param  username  MySQL Username 
	 * @param  password  MySQL Password 
	 */ 
	public ReverseEngineering(String host, String username, String password) throws   
	SQLException { 
		try{ 
			connect(host, username, password, "mysql"); 
		} 
		catch (SQLException se){ 
			throw se; 
		} 

	} 

	/** 
	 * Create a new instance of ReverseEngineering using supplied database. 
	 * 
	 * @param  host      MySQL Server Hostname 
	 * @param  username  MySQL Username 
	 * @param  password  MySQL Password 
	 * @param  db        Default database 
	 */ 
	public ReverseEngineering(String host, String username, String password, String   
			db) throws SQLException{ 
		try{ 
			connect(host, username, password, db); 
		} 
		catch (SQLException se){ 
			throw se; 
		} 
	} 

	/** 
	 * Connect to MySQL server 
	 * 
	 * @param  host      MySQL Server Hostname 
	 * @param  username  MySQL Username 
	 * @param  password  MySQL Password 
	 * @param  db        Default database 
	 */ 
	public void connect(String host, int port, String username, String   
			password, String db) throws SQLException{ 
		try 
		{ 
			Class.forName ("com.mysql.jdbc.Driver").newInstance (); 
			conn = DriverManager.getConnection ("jdbc:mysql://"   + host +":"  + port +  "/" +  db, username, password); 
			init(conn); 
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

	public void connect(Connection conn) throws SQLException { 
		init(conn); 
	} 

	public void init(Connection conn) throws SQLException { 
		schema=conn.getCatalog(); 
		this.conn=conn; 

		databaseMetaData = conn.getMetaData(); 
		databaseProductVersion =   
				databaseMetaData.getDatabaseProductVersion(); 
		databaseProductMajorVersion =   
				databaseMetaData.getDatabaseMajorVersion(); 
		databaseProductMinorVersion =   
				databaseMetaData.getDatabaseMinorVersion(); 
	} 

	/** 
	 * Connect to MySQL server 
	 * 
	 * @param  host      MySQL Server Hostname 
	 * @param  username  MySQL Username 
	 * @param  password  MySQL Password 
	 * @param  db        Default database 
	 */ 
	public void connect(String host, String username, String password,String db) throws SQLException{ 
		connect(host,8889,username,password,db); 
	} 

	public String dumpCreateDatabase(String database) { 
		String createDatabase = null; 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW CREATE DATABASE "  + database); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				createDatabase = rs.getString("Create Database") + ";"; 
			} 
		} catch (SQLException e) { 

		} 
		return createDatabase; 
	} 

	public File dumpDatabase(String database){ 
		return null; 
	} 


	public void dumpAllTables(BufferedWriter out) throws SQLException,IOException { 
		Statement st=conn.createStatement(); 
		ResultSet rs=st.executeQuery("show tables"); 
		while(rs.next()) { 
			String tableName=rs.getString(1); 
			out.write(dumpCreateTable(tableName)); 
			this.dumpTable(out,tableName); 
		} 

	} 

	public String dumpCreateEvent(String schema, String event) { 
		String createEvent = "--\n-- Event structure for event `"+   event +"`\n--\n\n"; 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW CREATE EVENT "  + schema  + "."  + event); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				createEvent  = rs.getString("Create Event")  + ";"; 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 
		return createEvent; 
	} 

	public String dumpCreateTable(String table) { 
		return dumpCreateTable(schema,table); 
	} 

	public String dumpCreateTable(String schema, String table) { 
		String createTable = "--\n-- Table structure for table `"  + table  + 
				"`\n--\n\n"; 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW CREATE TABLE "  + schema  + "."  + table); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				createTable  = rs.getString("Create Table")  + ";"; 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
			createTable = ""; 
		} 
		return createTable; 
	} 

	public String dumpCreateEvent(String event) { 
		return dumpCreateEvent(schema,event); 
	} 

	public String dumpCreateRoutine(String schema, String routine) { 
		String createRoutine = "--\n-- Routine structure for routine `"+     
				routine +  "`\n--\n\n"; 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SELECT ROUTINE_DEFINITION FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME='" +  routine +  "'"); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				createRoutine  = rs.getString("ROUTINE_DEFINITION") +  ";"; 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
			createRoutine = ""; 
		} 
		return createRoutine; 
	} 

	public String dumpCreateRoutine(String routine) { 
		return dumpCreateRoutine(schema,routine); 
	} 

	public String dumpCreateTrigger(String schema, String trigger) { 
		String createTrigger = "--\n-- Trigger structure for trigger `" + trigger   +"`\n--\n\n"; 
		try{ 
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW CREATE TRIGGER " +  schema  + "."+ trigger); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				createTrigger  = rs.getString("SQL Original Statement") + ";"; 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
			createTrigger = ""; 
		} 
		return createTrigger; 
	} 

	public String dumpCreateTrigger(String trigger) { 
		return dumpCreateTrigger(schema,trigger); 
	} 


	public void dumpTable(BufferedWriter out, String table){ 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
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
			while (rs.next()) 
			{ 
				postfix = ""; 
				for (int i = 1; i <= columnCount; i++) { 
					if (i == columnCount){ 
						postfix  = "unhex('"     
								+ escapeString(rs.getBytes(i)).toString() +  "'));\n"; 
					}else{ 
						try{ 
							postfix  = "unhex('"     
									+ escapeString(rs.getBytes(i)).toString() +  "'),"; 
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

	public Map<String, String> dumpGlobalVariables() { 
		Map<String, String> variables = new TreeMap(); 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW GLOBAL VARIABLES"); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				variables.put(rs.getString(1),rs.getString(2)); 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 
		return variables; 
	} 

	public File dumpAllViews(String database) { 
		return null; 
	} 

	public String dumpCreateView(String view) { 
		return null; 
	} 

	public File dumpView(String view) { 
		return null; 
	} 

	public ArrayList<String> listSchemas() { 
		ArrayList<String> schemas = new ArrayList(); 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SHOW DATABASES"); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				//Skip Information Schema 
				if   
				(!rs.getString("Database").equals("information_schema")) { 
					schemas.add(rs.getString("Database")); 
				} 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 

		return schemas; 
	} 

	public ArrayList<String> listRoutines(String schema) { 
		ArrayList<String> routines = new ArrayList(); 
		//Triggers were included beginning with MySQL 5.0.2 
		if (databaseProductMajorVersion < 5){ 
			return routines; 
		} 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_SCHEMA='"  + schema  + "'"); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				routines.add(rs.getString(1)); 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 

		return routines; 
	} 

	public ArrayList<String> listTriggers(String schema) { 
		ArrayList<String> triggers = new ArrayList(); 
		//Triggers were included beginning with MySQL 5.0.2 
		if (databaseProductMajorVersion < 5){ 
			return triggers; 
		} 
		try{ 
			Statement s = conn.createStatement   
					(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			s.executeQuery ("SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='" +  schema  + "'"); 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				triggers.add(rs.getString(1)); 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 

		return triggers; 
	} 

	public ArrayList<String> listGrantTables() { 
		ArrayList<String> grantTables = new ArrayList(); 
		grantTables.add("user"); 
		grantTables.add("db"); 
		grantTables.add("tables_priv"); 
		grantTables.add("columns_priv"); 
		//The procs_priv table exists as of MySQL 5.0.3. 
		if (databaseProductMajorVersion > 4){ 
			grantTables.add("procs_priv"); 
		} 
		return grantTables; 
	} 

	public ArrayList<String> listTables(String schema) { 
		ArrayList<String> tables = new ArrayList(); 
		try{ 
			Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			//Version 5 upwards use information_schema 
			if (databaseProductMajorVersion < 5){ 
				s.executeQuery ("SHOW TABLES FROM " +  schema); 
			}else{ 
				s.executeQuery ("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '"  + schema  + "'"); 
			} 
			ResultSet rs = s.getResultSet (); 
			while (rs.next ()) 
			{ 
				tables.add(rs.getString(1)); 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 
		return tables; 
	} 
	public ArrayList<String> listEvents(String schema) { 
		ArrayList<String> events = new ArrayList(); 
		try{ 
			//Version 5 upwards use information_schema 
			if (databaseProductMajorVersion == 5 && databaseProductMinorVersion == 1 ){ 
				Statement s = conn.createStatement   
						(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
				s.executeQuery ("SELECT EVENT_NAME FROM INFORMATION_SCHEMA.EVENTS WHERE EVENT_SCHEMA='" +  schema +  "'"); 
				ResultSet rs = s.getResultSet (); 
				while (rs.next ()) 
				{ 
					events.add(rs.getString(1)); 
				} 
			} 
		} catch (SQLException e) { 
			System.err.println (e.getMessage()); 
		} 
		return events; 
	} 
	public String getSchema(){ 
		return schema; 
	} 
	public void setSchema(String schema){ 
		this.schema = schema; 
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
	public String getHeader(){ 
		//return Dump Header 
		return "-- BinaryStor MySQL Dump "  + version +"\n--\n-- Host: " + hostname + "    "+"Database: " +schema+
				"\n--------------------------------------------------------\n-- Server Version: " +  databaseProductVersion +  "\n--"; 
	} 

	/** 
	 * Main entry point for ReverseEngineering when run from command line 
	 * 
	 * @param  args  Command line arguments 
	 */ 
	public static void main (String[] args) throws IOException { 
		new ReverseEngineering().doMain(args); 
	} 

	/** 
	 * Parse command line arguments and run ReverseEngineering 
	 * 
	 * @param  args  Command line arguments 
	 */ 
	public void doMain(String[] args) throws IOException { 
		arguments.add(0, "sakila");
		arguments.add("root");
		arguments.add("localhost");
		arguments.add("root");
		//Do we have a hostname? if not use localhost as default 
		if (hostname == null){ 
			hostname = "localhost"; 
		} 
		//First argument here should be database 
		schema = (String) arguments.remove(0); 

		try{ 
			//Create temporary file to hold SQL output. 
			File temp = File.createTempFile(schema, ".sql"); 
			BufferedWriter out = new BufferedWriter(new FileWriter(temp)); 
			this.connect(hostname, username, password, schema); 
			out.write(getHeader()); 
			this.dumpCreateDatabase(schema);
			this.dumpAllTables(out);
			out.flush(); 
			out.close(); 
			this.cleanup(); 
		} 
		catch (SQLException se){ 
			System.err.println (se.getMessage()); 
		} 
	} 

	public int cleanup(){ 
		try 
		{ 
			conn.close (); 
			if (verbose){ 
				System.out.println ("Database connection terminated"); 
			} 
		} 
		catch (Exception e) { /* ignore close errors */ } 
		return 1; 
	} 
} 