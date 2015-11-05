package com.btc.viewModel;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Account;
import com.btc.model.Group;
import com.btc.repositoty.GroupRepository;

public class GroupTableModel extends AbstractTableModel {

	private List<Group> groups;
	private GroupRepository repositoty;
	
	
	
	
	public GroupTableModel(GroupRepository repository) {
		this.repositoty = repository;
		this.groups = repositoty.getList();
	}
	
	public Group getGroupAtIndex(int row) {
		if (row < 0 || row >= groups.size())
			return null;
		return this.groups.get(row);
	}
	
	public Group insertGroup(Group group) {
		try {			
			group = repositoty.insert(group);
			
			// DialogHelpers.showAlert("Congratulations!", "Success");
            fireTableRowsInserted(groups.size(), groups.size());
            
			return group;
		} catch (SQLException e) {			
			DialogHelpers.showError("Error occured", e);
		}	
		return null;
	}
	
	public void deleteGroup(Group group) {
		try {			
			int row = groups.indexOf(group);
			if (row == -1) return;
			if (DialogHelpers.showConfirmMessage("Delete group", 
					"Do you want to delete this group\n id: " + String.valueOf(group.id) + "\nname: " + group.name, 1 ) == JOptionPane.YES_OPTION);
			{
				repositoty.delete(group);
				fireTableRowsDeleted(row, row);
			}			
		} catch (Exception exception) {
			DialogHelpers.showError("Error occured", exception);
		}		
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) return "Group Name";
		return super.getColumnName(column);
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}	
	
	@Override
	public int getRowCount() {		
		return groups.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Group accountType = groups.get(row);
		if (col == 0) return accountType.name;
		return "Error";
	}
	
	public void clearAll() {
		try {
		for (int i = 0; i < groups.size(); i++) {
			fireTableRowsDeleted(i, i);
			
		}
		groups.clear();
		} catch (Exception exception) {
			
		}
	}

	
}
