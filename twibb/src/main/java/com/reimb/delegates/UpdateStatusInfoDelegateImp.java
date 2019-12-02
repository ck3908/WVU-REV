package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.FormStatus;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class UpdateStatusInfoDelegateImp implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(UpdateStatusInfoDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form Update Status delegate");
		FormStatus fsta = new FormStatus();
		fsta.setEmpId(Integer.parseInt(req.getParameter("submitter")));  // these parameters from xttp call
		fsta.setFormId(Integer.parseInt(req.getParameter("fmid")));
		fsta.setStatus(Integer.parseInt(req.getParameter("status")));
		FormService fs = new FormServiceImp();
		int result = fs.updFormStat(fsta);		
	}
}
