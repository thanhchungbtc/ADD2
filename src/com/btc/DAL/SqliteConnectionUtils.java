package com.btc.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class SqliteConnectionUtils {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		 String hostName = "";
		 
	      String dbName = "data//MyDB.db";
	      String userName = "";
	      String password = "";
	 
	      return getConnectionWith(hostName, dbName, userName, password);
	}
	
	public static Connection getConnectionWith(String hostName, String dbName, 
			String userName, String password) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:" + dbName;
		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);
		Connection connection = DriverManager.getConnection(url, config.toProperties());
		return connection;
	}
}
