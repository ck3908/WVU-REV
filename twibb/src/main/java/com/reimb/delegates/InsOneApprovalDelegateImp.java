package com.reimb.delegates;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.entities.Employee;
import com.reimb.entities.FormAppr;
import com.reimb.entities.FormReview;
import com.reimb.services.EmplServImp;
import com.reimb.services.EmplService;
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
		int HRflag = Integer.parseInt(req.getParameter("gotoHR"));  // 0 = go to Director next, 1 = HR next, 2 = done
		int deptId = Integer.parseInt(req.getParameter("deptid"));
		String dateStr = req.getParameter("approveDt");
		DateFormat format = new SimpleDateFormat("yyy-MM-dd");
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
		
		// check who is to review this next
		
		if (HRflag == 0) {	// set reviewer to next chain in line which is dept head				
			fr.setReviewId(Integer.parseInt(req.getParameter("deptHeadId")));  //supervisor approved now to depthead
			int frdone = fs.insertFrev(fr);
		}
		else if (HRflag == 1) { // look for the HR person responsible for approving this - someone in dept 2
			
			//set the parameters for the amount to potentially approve based on grading format here
			// use the override field in fapprove table as the approved amount for any one request/formid
			// for any particular user, we can then sum up all of the submitter's approved amounts by 
			// summing all the override values for that submitter to keep track of what is left over for the year
			// where a $1000 is max per year
			
			int gradingfmt = Integer.parseInt(req.getParameter("gfmt"));
			
			if (deptId != 2 ) {  //the person requesting approval is not in HR dept, so anyone in HR can approve now
				// find a reviewer id in HR department
				Employee hrEmp = new Employee();
				EmplService es = new EmplServImp();
				hrEmp = es.getHREmpl();
				fr.setReviewId(hrEmp.getId()); //set reviewer to an HR person 
				int gotdone = fs.insertFrev(fr);
			}
			else {  //person requesting approval in HR department, so find the head of HR as reviewer
				// go to the dept head table to fetch head of HR
				Employee hrHead = new Employee();
				EmplService es = new EmplServImp();
				hrHead = es.getHRhead();
				fr.setReviewId(hrHead.getId());
				int doneAgain = fs.insertFrev(fr);				
			}
		}
	}
}
