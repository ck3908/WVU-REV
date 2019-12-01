package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.entities.Employee;
import com.reimb.services.EmplServImp;
import com.reimb.services.EmplService;

public class GetEmpInfoDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(GetEmpInfoDelegateImp.class);
	private ObjectMapper om = new ObjectMapper();
	private EmplService es = new EmplServImp();


	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form GetEmpInfo delegate");
		Employee ei = new Employee();
		Integer eid = Integer.parseInt(req.getParameter("empid")); //empid from xttp request
		ei = es.getEmpById(eid);
		log.trace("preparing response back to xmlhttrequest");
		respondToUser(resp, ei);
	}
	
	private void respondToUser(HttpServletResponse resp, Employee emp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		log.trace("building string response");
		String e = om.writeValueAsString(emp);
//		StringBuilder sb = new StringBuilder("{\"submitter\":");  because local storage in js gives it the name
//		sb.append(e);
//		sb.append("}");
//		log.trace(sb);
		log.trace(e);
		resp.getWriter().write(e);
	}
	
	
	
	
}
