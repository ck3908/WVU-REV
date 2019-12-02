package com.reimb.delegates;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.FormAppr;
import com.reimb.entities.FormReview;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class InsOneApprovalDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(InsertRFCDelegateImp.class);

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by One approval form delegate");
		FormAppr fa = new FormAppr();
		// converting string to date format to store in SQL DB
		String dateStr = req.getParameter("approveDt");
		DateFormat format = new SimpleDateFormat("dd-MMM-yy");
		Date ldate = new Date();
		try {
			ldate = format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fa.setApprovDt(ldate);
		fa.setApproverId(Integer.parseInt(req.getParameter("approveId")));
		fa.setEmpId(Integer.parseInt(req.getParameter("submitter")));
		fa.setFormId(Integer.parseInt(req.getParameter("fmid")));
		fa.setOverride(Integer.parseInt(req.getParameter("override")));
		FormService fs = new FormServiceImp();
		int fadone = fs.insertFormAppr(fa);
		
		// now insert form review table
		log.trace(" form approved step one completed, now update form reviewer table");
		FormReview fr = new FormReview();
		fr.setFormId(Integer.parseInt(req.getParameter("fmid")));
		fr.setReviewId(Integer.parseInt(req.getParameter("deptHeadId")));  //supervisor approved now to depthead
		int frdone = fs.insertFrev(fr);
	}
}
