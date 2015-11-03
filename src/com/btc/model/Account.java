package com.btc.model;

public class Account {
	public String userName;
	public String password;
	public String url;
	public String title;
	public String notes;
	public Group group;
	
	public Account() {
		group = new Group();
	}
}
