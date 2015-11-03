package com.btc.DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConnection;

public class ConnectionUtils {

	
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		return SqliteConnectionUtils.getConnection();
	}
	
	public static ResultSet executeQuery(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection();
		Statement stmt = conn.createStatement();
        return  stmt.executeQuery (sql);	         
	}
	
	public static void main(String[] args) throws SQLException,
    ClassNotFoundException {
		
		System.out.println("Get connection ... ");
		
		Connection conn = ConnectionUtils.getConnection();
		
		System.out.println("Get connection " + conn);
		
		System.out.println("Done!");
	}
}
