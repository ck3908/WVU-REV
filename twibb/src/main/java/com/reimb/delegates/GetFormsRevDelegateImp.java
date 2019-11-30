package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class GetFormsRevDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(GetFormsRevDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form all forms to review delegate");
		Integer rid = Integer.parseInt(req.getParameter("reviewerId")); //from review js xttp request object
		FormService fs = new FormServiceImp();
		log.trace("in GetFormsRevDelegateImp preparing to get info");
		
		
	}
	
	
}
