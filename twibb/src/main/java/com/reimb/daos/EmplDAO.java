package com.reimb.daos;

import com.reimb.entities.Employee;

public interface EmplDAO {
	
	/**
	 * returns a login object from the database
	 * 
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return the user from the database that matches the username and password
	 */	
	public Employee getEmpl(String username, String password);

}
