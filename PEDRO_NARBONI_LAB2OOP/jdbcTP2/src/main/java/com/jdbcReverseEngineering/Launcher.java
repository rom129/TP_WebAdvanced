package com.jdbcReverseEngineering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.jdbcReverseEngineering.base.DBBase;

public class Launcher {
	
	protected static Logger log=Logger.getLogger(Launcher.class);
	
	/**
	 * Creation of the file.
	 * @param string
	 * @param fileName
	 */
	private static void createScript(String string, String fileName) {
		try {
			File file = new File(fileName + ".sql");
			file.setWritable(true);
			FileWriter fw = new FileWriter(file);
			fw.write(string.toString());
			fw.close();
			log.debug("The path of the created file is : " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main method.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {		
		DBBase dbBase = new	DBBase(args[0], args[1], args[2], args[3], args[4]);
		String test = dbBase.toSQL();
		createScript(test,args[4]);
	}

}
