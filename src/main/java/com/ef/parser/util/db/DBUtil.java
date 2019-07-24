package com.ef.parser.util.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ef.parser.util.PropertyReader;

public class DBUtil {
	public static Connection getDBConnection() {
		 Connection conn = null;
		try {					   
			
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(PropertyReader.getdbUrl(),
						PropertyReader.getDbUsername(), PropertyReader.getDbPassword());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * This method closes database connection.
	 * 
	 */
	public static void closeDBConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
