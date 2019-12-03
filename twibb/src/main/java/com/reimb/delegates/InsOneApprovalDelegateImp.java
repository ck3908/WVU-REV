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
	private Logger log = Logger.getLogger(InsOneApprovalDelegateImp.class);

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
		fa.setOverride(Integer.parseInt(req.getParameter("override")));  //when HR = 2, this variable stores reimbamt from html which in last step is stored in override
		FormService fs = new FormServiceImp();
		if (HRflag != 2) {  // HR flag = 2 is handled differently checking amounts before setting the field.
			int fadone = fs.insertFormAppr(fa);
		}
		
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
			
			// first check how much many already granted to this submitter by summing up all of the 
			//submitter's grants in fapprove table
			int submitter = Integer.parseInt(req.getParameter("submitter"));
			EmplService eg = new EmplServImp();
			int sumGrant = eg.getEmpTotGrant(submitter);  // employee's total grant in the db				
			int requestamt = Integer.parseInt(req.getParameter("reqamt"));
			
			// now check if requested amount 
			final Integer maxGrant = 1000;  //can't change variable
			if (sumGrant >= maxGrant) {  // already at 1000 max 
				requestamt = 0;				
			}
			else if ((maxGrant - (sumGrant+requestamt) < 0)) {  //requested amt could exceed limit, reduce it down
				requestamt = maxGrant - sumGrant;
			}
			else {
				//leave it for now
			}
			
			//add checks that request amount less than 1000 in total for year
			int fmid = Integer.parseInt(req.getParameter("fmid"));
			int gradingfmt = Integer.parseInt(req.getParameter("gfmt"));
			int grantamt = 0;  // this amount is to present to HR person before approval
			switch (gradingfmt) {  //need to check the business rules
				case 1: grantamt = (int) Math.round(requestamt*0.80); //certifications prep
				break;
				case 2: grantamt = (int) Math.round(requestamt*1.00); // certifications
				break;
				case 3: grantamt = (int) Math.round(requestamt*0.90); 
				break;
				case 4: grantamt = (int) Math.round(requestamt*0.85); 
				break;
				case 5: grantamt = (int) Math.round(requestamt*0.90); 
				break;
				case 6: grantamt = (int) Math.round(requestamt*0.60); //others
				break;
			} 
			System.out.println("grantamt is "+grantamt);
			// prepare to update form info with the amount potentially granted
			int updamt = fs.upDateAmtReimb(fmid, grantamt);		
			
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
		} // end HRflag == 1
		
		else { //HRflag should be == 2 here means HR has approved it, prepare to insert fapprove record
			// remember status table was updated already so no need to do it here
			// actually logic might look better in its own module since not doing anything with reviewer table
			// but for simplicity stick it here and update fapprove table
			FormAppr hra = new FormAppr();
			
			String dStr = req.getParameter("approveDt");
			DateFormat fmt = new SimpleDateFormat("dd-MMM-yy");
			Date ndate = new Date(); //initial date somehow
			try {
				ndate = format.parse(dStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hra.setApprovDt(ndate);
			hra.setApproverId(Integer.parseInt(req.getParameter("approveId")));
			hra.setEmpId(Integer.parseInt(req.getParameter("submitter")));
			hra.setFormId(Integer.parseInt(req.getParameter("fmid")));
			hra.setOverride(Integer.parseInt(req.getParameter("override")));
			FormService fshra = new FormServiceImp();
			int hraDone = fshra.insertFormAppr(hra);		
			
		}
	}
}
