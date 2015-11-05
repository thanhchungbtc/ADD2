package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.btc.model.Account;
import com.btc.model.Group;
import com.btc.repositoty.AccountRepositoty;
import com.btc.repositoty.GroupRepository;
import com.btc.viewModel.AccountTableModel;
import com.btc.viewModel.GroupTableModel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Desktop.Action;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTable groupTable;
	private JTable accountTable;
	private JLabel lblWelcome;

	private AccountRepositoty accountRepository;
	private GroupRepository groupRepository;

	private DefaultTableModel accountTableModel;

	private boolean hasBeenApproved = false;
	private JMenuItem mntmLogin;

	private void login() {
		try{
			if (hasBeenApproved) {
				mntmLogin.setText("Login");
				hasBeenApproved = false;
				((GroupTableModel)this.groupTable.getModel()).clearAll();
				((AccountTableModel)this.accountTable.getModel()).clearAll();

				groupTable.setEnabled(false);
				accountTable.setEnabled(false);
				return;
			}


			//String input = DialogHelpers.showInPutDialog("Login panel", "Enter password");
//			if (input == null) {
//				return;
//			}
//			if (input.trim() == "")
//				return;
			
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Enter a password:");
			JPasswordField pass = new JPasswordField(10);
			panel.add(label);
			panel.add(pass);
			String[] options = new String[]{"OK", "Cancel"};
			int option = JOptionPane.showOptionDialog(null, panel, "Login panel",
			                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			                         null, options, options[1]);
			if(option == 0) // pressing OK button
			{
			   String input = pass.getText();
				if (input == null) {
					return;
				}
				if (input.trim() == "")
					return;

				String md5 = Algorithms.MD5(input);
				if (md5.equals(AccountRepositoty.getPasswordFromDataBase())) {
					hasBeenApproved = true;
					String newKey = input + md5;
					Algorithms.key = newKey.substring(0, 16);

					mntmLogin.setText("Logout");
					setupTable();
				} 


			}

		}catch (Exception exception) {

		}
	}

	private void createAndSetupGUI(){
		System.out.println("error");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmLogin = new JMenuItem("Login");
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		mnFile.add(mntmLogin);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setDividerSize(2);
		mainSplitPane.setDividerLocation(250);

		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(255, 255, 255));
		mainSplitPane.setLeftComponent(leftPanel);

		groupTable = new JTable();
		groupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] columnNames = {"Group Name"};
		leftPanel.setLayout(new BorderLayout(0, 0));

		groupTable.setModel(new DefaultTableModel(null, columnNames));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(groupTable);
		leftPanel.add(scrollPane);	

		JPanel leftButtonPanel = new JPanel();
		leftPanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

		JButton btnAddGroup = new JButton("Add");
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddGroupActionPerformed(e);	
			}
		});
		leftButtonPanel.add(btnAddGroup);

		JButton btnDeleteGroup = new JButton("Delete");
		btnDeleteGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteGroupActionPerformed(e);
			}
		});
		leftButtonPanel.add(btnDeleteGroup);


		JPanel rightPanel = new JPanel();
		mainSplitPane.setRightComponent(rightPanel);

		accountTable = new JTable();
		accountTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					accountTableDoubleClicked(e);
				}
			}
		});
		accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] columnNames1 = {"Title", "User Name", "Password", "URL", "Notes" };
		rightPanel.setLayout(new BorderLayout(0, 0));

		accountTable.setModel(new DefaultTableModel(null, columnNames1));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(accountTable);

		rightPanel.add(scrollPane_1);

		JPanel rightButtonPanel = new JPanel();
		rightPanel.add(rightButtonPanel, BorderLayout.NORTH);
		rightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

		JButton btnAddAccount = new JButton("Add");
		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddAccountActionPerformed(e);
			}
		});
		rightButtonPanel.add(btnAddAccount);

		JButton btnDeleteAccount = new JButton("Delete");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteAccountActionPerformed(e);
			}
		});
		rightButtonPanel.add(btnDeleteAccount);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(null);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblWelcome = new JLabel("Welcome");
		statusPanel.add(lblWelcome);

	}

	private void initializeData() {
		accountRepository = new AccountRepositoty(0);
		groupRepository = new GroupRepository();
	}

	private void setupTable() {

		initializeData();
		groupTable.setEnabled(true);
		accountTable.setEnabled(true);
		groupTable.setModel(new GroupTableModel(this.groupRepository));
		groupTable.setRowHeight(25);

		groupTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {	
				groupTableValueChanged(e);
			}
		});
		accountTable.setRowHeight(25);	


	}

	private void loadAccountTableWithGroup(Group group) {
		if (group == null) {
			return;
		} else
			accountTable.setModel(new AccountTableModel(group.id));
	}

	// handle events
	private void btnAddGroupActionPerformed(ActionEvent event) {

		String newgGroupName = DialogHelpers.showInPutDialog("Add new group", "Enter group's name");
		if (newgGroupName == null) {
			return;
		}
		if (newgGroupName.trim() == "")
			return;
		Group group = new Group();
		group.name = newgGroupName;
		GroupTableModel model = (GroupTableModel)groupTable.getModel();
		model.insertGroup(group);
	}

	private void btnDeleteGroupActionPerformed(ActionEvent event) {
		GroupTableModel model = (GroupTableModel)groupTable.getModel();
		int idx = groupTable.getSelectedRow();
		if (idx == -1) return;
		Group selectedGroup = model.getGroupAtIndex(idx);
		model.deleteGroup(selectedGroup);
	}

	private void btnAddAccountActionPerformed(ActionEvent event) {
		AccountDetailsForm accountDetailsForm = new AccountDetailsForm(accountRepository, groupRepository.getList());
		int idx = groupTable.getSelectedRow();
		if (idx != -1)
			accountDetailsForm.cbGroup.setSelectedIndex(idx);
		else 
			accountDetailsForm.cbGroup.setSelectedIndex(0);
		accountDetailsForm.setLocationRelativeTo(this);		
		accountDetailsForm.setVisible(true);
		groupTableValueChanged(new ListSelectionEvent(groupTable, 0, 0, false));
	}

	private void btnDeleteAccountActionPerformed(ActionEvent event) {
		AccountTableModel model = (AccountTableModel)accountTable.getModel();
		int idx = accountTable.getSelectedRow();
		if (idx == -1) return;
		Account selectedAccount = model.getAccountAtIndex(idx);
		model.deleteAccount(selectedAccount);
	}

	private void accountTableDoubleClicked(MouseEvent event) {
		AccountDetailsForm accountDetailsForm = new AccountDetailsForm(accountRepository, groupRepository.getList());
		int idx = accountTable.getSelectedRow();
		if (idx == -1){			
			return;
		}		

		int selectedGroup = groupTable.getSelectedRow();
		if (selectedGroup != -1)
			accountDetailsForm.cbGroup.setSelectedIndex(selectedGroup);
		else 
			accountDetailsForm.cbGroup.setSelectedIndex(0);


		AccountTableModel model = (AccountTableModel)accountTable.getModel();
		Account selectedAccount = model.getAccountAtIndex(idx);
		accountDetailsForm.setAccount(selectedAccount);

		accountDetailsForm.setLocationRelativeTo(this);		
		accountDetailsForm.setVisible(true);
		groupTableValueChanged(new ListSelectionEvent(groupTable, 0, 0, false));
		accountTable.addRowSelectionInterval(idx, idx);
	}

	private void groupTableValueChanged(ListSelectionEvent e){
		if (!e.getValueIsAdjusting()) {
			GroupTableModel model = (GroupTableModel)groupTable.getModel();		
			loadAccountTableWithGroup(model.getGroupAtIndex(groupTable.getSelectedRow()));
		}
	}
	// end handle events

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);

//		initializeData();
		createAndSetupGUI();
//		 if (hasBeenApproved)
//			 setupTable();

		pack();
	}

}
