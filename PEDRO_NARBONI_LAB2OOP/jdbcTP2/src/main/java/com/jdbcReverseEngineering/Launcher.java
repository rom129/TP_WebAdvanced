package com.jdbcReverseEngineering;

import com.jdbcReverseEngineering.base.DBBase;

public class Launcher {

	public static void main(String[] args) throws Exception {
		String database = "sakila";
		
		DBBase dbBase = new	DBBase(args[0], args[1], args[2], args[3], database);
		String test = dbBase.toSQL();
		System.out.println(test);
	}

}
