package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.FormRej;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class InsertRejDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(InsertRejDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by reject form delegate");
		// we know it is post
		FormRej fj = new FormRej();
		fj.setFormId(Integer.parseInt(req.getParameter("fmid")));
		fj.setrejId(Integer.parseInt(req.getParameter("rejector")));
		fj.setReason(req.getParameter("reason"));
		FormService fs = new FormServiceImp();
		log.trace("in form reject delegate, preparing reject form info object to be inserted");
		int result = fs.insFormRej(fj);  //inserted form rejection
	}
}
