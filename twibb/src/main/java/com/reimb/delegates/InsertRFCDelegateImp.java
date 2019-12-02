package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.ReqFC;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class InsertRFCDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(InsertRFCDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by RFC form delegate");
		ReqFC fc = new ReqFC();
		fc.setAskId(Integer.parseInt(req.getParameter("askid")));
		fc.setFormId(Integer.parseInt(req.getParameter("fmid")));
		fc.setQuestion(req.getParameter("question"));
		fc.setAnswer(req.getParameter("answer"));
		FormService fs = new FormServiceImp();
		log.trace("in req for comment delegate, preparing form info object to be inserted");
		int rfcdone = fs.insertFormRFC(fc);
	}
}
