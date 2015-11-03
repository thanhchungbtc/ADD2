package com.btc.controllers;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.btc.model.Account;
import com.btc.model.Group;
import com.btc.repositoty.AccountRepositoty;
import com.btc.repositoty.GroupRepository;

import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

public class AccountDetailsForm extends JDialog {

	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtUserName;
	private JTextField txtURL;
	

	private String password;
	private boolean viewing = false;
	// must set
	public List<Group> groups;
	public AccountRepositoty repositoty;
	public Account account;
	private JTextArea txtNotes;
	public JComboBox cbGroup;
	private JPasswordField txtPassword;
	private JPasswordField txtRepeatPassword;
	private JLabel lblNewLabel;
	
	public void setAccount(Account account) {
		this.account = account;
		insertMode = false;
		setupGUIWithData();
	}
	
	private boolean insertMode = true;
	
	private boolean validateUserInput() {		
		if (!txtPassword.getText().equals(txtRepeatPassword.getText())) {
			DialogHelpers.showAlert("Error", "Invalid password!");
			return false;
		} 
		
		if (txtUserName.getText().trim().equals("")) {
			DialogHelpers.showAlert("Error", "User name cannot be blank!");
			return false;
		}
		
		
		return true;
		
	}
	
	private void setupGUIWithData() {
		txtUserName.setEnabled(false);
		cbGroup.setEnabled(false);
		txtUserName.setText(account.userName);
		txtPassword.setText(account.password);
		txtURL.setText(account.url);
		txtNotes.setText(account.notes);
		txtRepeatPassword.setText(account.password);
		txtTitle.setText(account.title);
	}
	
	private void loadGroupCombobox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (Group group: groups) {
			model.addElement(group.name);
		}
		cbGroup.setModel(model);
		
		int selectedIndex = 0;
		if (!insertMode) selectedIndex = account.group.id;
		cbGroup.setSelectedIndex(selectedIndex);
	}
	
	private void generatePassword() {
		this.password = Algorithms.generateRandomString();
		txtPassword.setText(password);
		txtRepeatPassword.setText(password);
	}
	
	@SuppressWarnings("deprecation")
	private void btnOKActionPerformed(ActionEvent e) {
		if (!validateUserInput()) {
			
			return;
		}
		try {
			if (!insertMode) {
				account.userName = txtUserName.getText();
				account.password = Algorithms.encryptedString(txtPassword.getText());
				account.url = txtURL.getText();
				account.notes = txtNotes.getText();
				account.title = txtTitle.getText();
				account.group = groups.get(cbGroup.getSelectedIndex());
				repositoty.update(account);
				DialogHelpers.showAlert("Success", "This account has been saved!");
			} 
			else {
				account.userName = txtUserName.getText();
				account.password = Algorithms.encryptedString(txtPassword.getText());
				account.url = txtURL.getText();
				account.notes = txtNotes.getText();
				account.title = txtTitle.getText();
				account.group = groups.get(cbGroup.getSelectedIndex());
				
				repositoty.insert(account);
				DialogHelpers.showAlert("Success", "A new account has been add to database!");
			}
						
		} catch (SQLException e1) {
			DialogHelpers.showError("Error occured", e1);
		}
		this.dispose();
	}

	
	/**
	 * Create the frame.
	 */
	public AccountDetailsForm(AccountRepositoty repositoty, List<Group> groups) {
		this.repositoty = repositoty;
		this.groups = groups;
		this.account = new Account();
		this.insertMode = true;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 458, 386);
		createAndSetupGUI();
		loadGroupCombobox();
		//setupGUIWithData();
		// pack();
		
	}

	private void createAndSetupGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
		FlowLayout flowLayout = (FlowLayout) headerPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		headerPanel.setBackground(SystemColor.textHighlight);
		contentPane.add(headerPanel, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Create new account");
		lblNewLabel.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		headerPanel.add(lblNewLabel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblNewLabel_1 = new JLabel("Group:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		mainPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		cbGroup = new JComboBox();
		GridBagConstraints gbc_cbGroup = new GridBagConstraints();
		gbc_cbGroup.gridwidth = 2;
		gbc_cbGroup.insets = new Insets(0, 0, 5, 0);
		gbc_cbGroup.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbGroup.gridx = 1;
		gbc_cbGroup.gridy = 0;
		mainPanel.add(cbGroup, gbc_cbGroup);
		
		JLabel lblNewLabel_2 = new JLabel("Title:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		mainPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtTitle = new JTextField();
		txtTitle.setColumns(10);
		GridBagConstraints gbc_txtTitle = new GridBagConstraints();
		gbc_txtTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitle.gridwidth = 2;
		gbc_txtTitle.gridx = 1;
		gbc_txtTitle.gridy = 1;
		mainPanel.add(txtTitle, gbc_txtTitle);
		
		JLabel lblUserName = new JLabel("User name:");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 2;
		mainPanel.add(lblUserName, gbc_lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		GridBagConstraints gbc_txtUserName = new GridBagConstraints();
		gbc_txtUserName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUserName.insets = new Insets(0, 0, 5, 0);
		gbc_txtUserName.gridwidth = 2;
		gbc_txtUserName.gridx = 1;
		gbc_txtUserName.gridy = 2;
		mainPanel.add(txtUserName, gbc_txtUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		mainPanel.add(lblPassword, gbc_lblPassword);
		
		JButton btnView = new JButton("See");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(viewing) {
					viewing = false;
					txtPassword.setEchoChar('*');	
					txtRepeatPassword.setEchoChar('*');	
				} else {
					viewing = true;
					txtPassword.setEchoChar((char)0);
					txtRepeatPassword.setEchoChar((char)0);
				}
			}
		});
		
		txtPassword = new JPasswordField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 3;
		mainPanel.add(txtPassword, gbc_txtPassword);
		
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.insets = new Insets(0, 0, 5, 0);
		gbc_btnView.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnView.gridx = 2;
		gbc_btnView.gridy = 3;
		gbc_btnView.gridwidth = GridBagConstraints.RELATIVE;
		mainPanel.add(btnView, gbc_btnView);
		
		JLabel lblRepeat = new JLabel("Repeat:");
		GridBagConstraints gbc_lblRepeat = new GridBagConstraints();
		gbc_lblRepeat.anchor = GridBagConstraints.WEST;
		gbc_lblRepeat.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepeat.gridx = 0;
		gbc_lblRepeat.gridy = 4;
		mainPanel.add(lblRepeat, gbc_lblRepeat);
		
		JButton btnGen = new JButton("Gen");
		btnGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatePassword();
			}
			
		});
		
		txtRepeatPassword = new JPasswordField();
		GridBagConstraints gbc_txtRepeatPassword = new GridBagConstraints();
		gbc_txtRepeatPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtRepeatPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepeatPassword.gridx = 1;
		gbc_txtRepeatPassword.gridy = 4;
		mainPanel.add(txtRepeatPassword, gbc_txtRepeatPassword);
		GridBagConstraints gbc_btnGen = new GridBagConstraints();
		gbc_btnGen.insets = new Insets(0, 0, 5, 0);
		gbc_btnGen.gridx = 2;
		gbc_btnGen.gridy = 4;
		mainPanel.add(btnGen, gbc_btnGen);
		
		JLabel lblUrl = new JLabel("URL:");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.WEST;
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 5;
		mainPanel.add(lblUrl, gbc_lblUrl);
		
		txtURL = new JTextField();
		txtURL.setColumns(10);
		GridBagConstraints gbc_txtURL = new GridBagConstraints();
		gbc_txtURL.insets = new Insets(0, 0, 5, 0);
		gbc_txtURL.gridwidth = 2;
		gbc_txtURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURL.gridx = 1;
		gbc_txtURL.gridy = 5;
		mainPanel.add(txtURL, gbc_txtURL);
		
		JLabel lblNotes = new JLabel("Notes:");
		GridBagConstraints gbc_lblNotes = new GridBagConstraints();
		gbc_lblNotes.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNotes.insets = new Insets(0, 0, 0, 5);
		gbc_lblNotes.gridx = 0;
		gbc_lblNotes.gridy = 6;
		mainPanel.add(lblNotes, gbc_lblNotes);
		
		txtNotes = new JTextArea();
		txtNotes.setLineWrap(true);
		txtNotes.setBorder(BorderFactory.createLineBorder(Color.black));
		txtNotes.setRows(10);
		
		GridBagConstraints gbc_txtNotes = new GridBagConstraints();
		gbc_txtNotes.gridwidth = 2;
		gbc_txtNotes.fill = GridBagConstraints.BOTH;
		gbc_txtNotes.gridx = 1;
		gbc_txtNotes.gridy = 6;
		mainPanel.add(txtNotes, gbc_txtNotes);
		
		JPanel footerPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) footerPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		footerPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
		contentPane.add(footerPanel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		footerPanel.add(btnCancel);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOKActionPerformed(e);
			}
		});
		footerPanel.add(btnOK);
		
		
	}

}
