package com.reimb.services;

import org.apache.log4j.Logger;

import com.reimb.daos.EmplDAO;
import com.reimb.daos.EmplOracle;
import com.reimb.entities.Employee;


public class EmplServImp implements EmplService {
	
	private Logger log = Logger.getLogger(EmplServImp.class);
	private EmplDAO ed = new EmplOracle();
	
	@Override
	public Employee getEmployee(String username, String password) {
		Employee emp = new Employee();
		
		emp = ed.getEmpl(username, password);
		if(emp.getId()==0)
		{
			log.trace("No employee found");
			return null;
		}
//		if(emp.getSupervisor()!=null)
//		{
//			log.trace("Retrieving supervisor");
//			emp.setSupervisor(getEmployeeById(emp.getSupervisor().getId()));
//		}
		return emp;
	}

	@Override
	public Employee getEmpById(Integer id) {
		// TODO Auto-generated method stub
		Employee emp = new Employee();
		
		emp = ed.getEmplbyId(id);
		if(emp.getId()==0)
		{
			log.trace("No employee found");
			return null;
		}
//		if(emp.getSupervisor()!=null)
//		{
//			log.trace("Retrieving supervisor");
//			emp.setSupervisor(getEmployeeById(emp.getSupervisor().getId()));
//		}
		return emp;
	}

}
