package com.reimb.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;


import com.reimb.entities.Employee;
import com.reimb.utils.ConnectionUtil;
import com.reimb.utils.LogUtil;



public class EmplOracle implements EmplDAO{
	private static Logger log = Logger.getLogger(EmplOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Employee getEmpl(String username, String password) {
		Employee em = new Employee();
		log.trace("Retrieve empl from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select id, empname, emppass, empdept, empsupid, empdeptid from logemp where empname=? and emppass = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("empl found.");
				em = new Employee();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("empname"));
				em.setPass(rs.getString("emppass"));
				em.setDept(rs.getInt("empdept"));
				em.setSupId(rs.getInt("empsupid"));
				em.setDeptHId(rs.getInt("empdeptid"));
		
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmplOracle.class);
		}
		
		return em;
	}

	@Override
	public Employee getEmplbyId(Integer id) {
		// TODO Auto-generated method stub
		Employee em = new Employee();
		log.trace("Retrieve empl from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select id, empname, emppass, empdept, empsupid, empdeptid from logemp where id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("empl found.");
				em = new Employee();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("empname"));
				em.setPass(rs.getString("emppass"));
				em.setDept(rs.getInt("empdept"));
				em.setSupId(rs.getInt("empsupid"));
				em.setDeptHId(rs.getInt("empdeptid"));
		
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmplOracle.class);
		}
		
		return em;
	}

	@Override
	public Employee getAnyHR() {
		Employee em = new Employee();
		log.trace("In get any HR Retrieve empl from database.");
		try(Connection conn = cu.getConnection()){  //select stmt to find first occurrence of an HR person
			String sql = "select * from (select * from logemp where empdept = 2) where rownum < 2";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("empl found.");
				em = new Employee();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("empname"));
				em.setPass(rs.getString("emppass"));
				em.setDept(rs.getInt("empdept"));
				em.setSupId(rs.getInt("empsupid"));
				em.setDeptHId(rs.getInt("empdeptid"));
		
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmplOracle.class);
		}
		
		return em;
	}

	@Override
	public Employee getTopHR() {
		Employee em = new Employee();
		log.trace("In getTopHR Retrieve empl from database.");
		try(Connection conn = cu.getConnection()){  //select stmt to find the head  HR person
			String sql = "select * from logemp where id in (select depthead from dept where dept = 2)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("empl found.");
				em = new Employee();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("empname"));
				em.setPass(rs.getString("emppass"));
				em.setDept(rs.getInt("empdept"));
				em.setSupId(rs.getInt("empsupid"));
				em.setDeptHId(rs.getInt("empdeptid"));
		
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmplOracle.class);
		}
		
		return em;
	}

	
	@Override
	public Integer getSumEmplGrants(Integer id) {  //check how much funds been granted to an individual
		// TODO Auto-generated method stub
		Integer sumTotal = 0;
		log.trace("In get employee's total grant from database.");
		try(Connection conn = cu.getConnection()){  //select stmt to find the head  HR person
			String sql = "SELECT SUM(override) as totalsum FROM fapprove WHERE submitter = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("sum total found");
				sumTotal = rs.getInt("totalsum");	
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmplOracle.class);
		}
		
		return sumTotal;
	}

}
