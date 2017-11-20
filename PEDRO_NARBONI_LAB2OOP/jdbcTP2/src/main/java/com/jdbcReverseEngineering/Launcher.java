package com.jdbcReverseEngineering;

import java.io.FileWriter;
import java.io.IOException;

import com.jdbcReverseEngineering.base.DBBase;

public class Launcher {
	
	private static void createScript(String string, String fileName) {
		try {
			FileWriter fichier = new FileWriter(fileName + ".sql");
			fichier.write(string.toString());
			fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String database = "sakila";
		
		DBBase dbBase = new	DBBase(args[0], args[1], args[2], args[3], database);
		String test = dbBase.toSQL();
		System.out.println(test);
		createScript(test,database);
	}

}
