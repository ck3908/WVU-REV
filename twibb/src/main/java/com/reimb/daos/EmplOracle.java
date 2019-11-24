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
			String sql = "select id, empname, empdept, empsupid, empdeptid"
					+ "from logemp where empname=? and emppass = ?";
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

}
