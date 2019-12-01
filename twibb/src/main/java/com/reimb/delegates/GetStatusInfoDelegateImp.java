package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.entities.Employee;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;


public class GetStatusInfoDelegateImp implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(GetStatusInfoDelegateImp.class);
	private ObjectMapper om = new ObjectMapper();
	
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form GetStatus Info delegate");
		FormService fs = new FormServiceImp();
		Integer fid = Integer.parseInt(req.getParameter("getfid"));
		Integer result = fs.getStatInfo(fid);  //retreive form status
		log.trace("preparing response back to xmlhttrequest");
		respUser(resp, result);
	}
	
	private void respUser(HttpServletResponse resp, Integer ans) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		log.trace("building string response");
		String e = om.writeValueAsString(ans);
//		StringBuilder sb = new StringBuilder("{\"submitter\":");  because local storage in js gives it the name
//		sb.append(e);
//		sb.append("}");
//		log.trace(sb);
		log.trace(e);
		resp.getWriter().write(e);
	}
	
	
}
