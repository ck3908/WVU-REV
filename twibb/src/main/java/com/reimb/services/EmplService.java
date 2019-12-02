package com.reimb.services;

import com.reimb.entities.Employee;

public interface EmplService {
	public Employee getEmployee(String username, String password);
	public Employee getEmpById(Integer id);
	public Employee getHREmpl(); //get any HR employee to do approval
	public Employee getHRhead();

}
