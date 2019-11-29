package com.reimb.delegates;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.FormInfo;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class FsubmitDelegateImp implements FrontControllerDelegate{
	private Logger log = Logger.getLogger(FsubmitDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form submit delegate");
		// we know it is post
		//build the form object to submit
		FormInfo Finfo = new FormInfo();
		Finfo.setEmpId(Integer.parseInt(req.getParameter("submitter"))); //string arguments need to be converted
		Finfo.setSubDate(new Date());
		Finfo.setEmpLoc(req.getParameter("formloc"));
		Finfo.setReqAmt(Integer.parseInt(req.getParameter("reqamt")));
		Finfo.setGradeFmt(Integer.parseInt(req.getParameter("gradefmt")));
		FormService fs = new FormServiceImp();
		log.trace("in fsubmit delegate, preparing form info object to be inserted");
		int done = fs.submitForm(Finfo);
		
	}

}
