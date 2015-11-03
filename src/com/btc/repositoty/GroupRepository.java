package com.btc.repositoty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Group;

public class GroupRepository {

	private List<Group> groups;
	private Connection connection;
	
	public GroupRepository() {
		groups = new LinkedList<Group>();		    
		     try {
		         try {
		        	 ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM AccountType");		         
			         while (rs.next()) {
			           int id = rs.getInt(1);
			           String name = rs.getString(2);
			           Group accountType = new Group();
			           accountType.name = name;
			           accountType.id = id;
			           groups.add(accountType);
			         }
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
		         
		      }
		      catch(SQLException e){
		         System.out.println("SQL exception occured" + e);
		      }    
	}
	
	public List<Group> getList() {
		return this.groups;
	}
	
	public Group getGroupByID(int id) {
		for (Group group: groups) {
			if (group.id == id)
				return group;
		}
		return null;
	}
	
	public Group insert(Group accountType) throws SQLException {
		 String sql = "INSERT INTO AccountType (name) VALUES(?)";
	        try {
	          connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	          Logger.getLogger(GroupRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		    statement.setString(1, accountType.name);
		    statement.executeUpdate();
		    ResultSet key = statement.getGeneratedKeys();
		    int id = 0;
		    while (key.next()) {
		    	id = key.getInt(1);
		    }
		    statement.close();
		    statement = null;
		    accountType.id = id;
		    groups.add(accountType);
		    connection.close();
		    return accountType;
	}
	
	public boolean delete(Group group) throws SQLException {
		if(group == null) {
			return false;
		}	
		String sql = "DELETE FROM AccountType WHERE id = " + group.id + "";		
        try {
          connection = ConnectionUtils.getConnection();
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(GroupRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
		Statement st = connection.createStatement();
		groups.remove(group);
        boolean result = st.execute(sql);
        connection.close();
	    return result;
	  }
	
	
	
}
