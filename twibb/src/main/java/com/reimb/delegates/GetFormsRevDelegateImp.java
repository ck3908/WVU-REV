package com.reimb.delegates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimb.entities.FormInfo;
import com.reimb.services.FormService;
import com.reimb.services.FormServiceImp;

public class GetFormsRevDelegateImp implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(GetFormsRevDelegateImp.class);
	private FormService fs = new FormServiceImp();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.trace(req.getMethod() + " received by form all forms to review delegate"); //post method
		Integer rid = Integer.parseInt(req.getParameter("reviewerId")); //from review js xttp request object
		
		log.trace("in GetFormsRevDelegateImp preparing to get info");
//		List<FormReview> frw = new ArrayList<>();
//		frw = fs.getRevs(rid);
		List<FormInfo> fir = new ArrayList<>();
		fir = fs.getFormsR(rid);
		if (fir != null) {  //prepare to respond to user
			log.trace("responding to user");
			buildFormsR(resp,fir);
			
		}
		else {
			log.trace("nothing to review or there is an error");
		}
	}
	private void buildFormsR(HttpServletResponse resp, List<FormInfo> firev) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		log.trace("building string response");
		String e = om.writeValueAsString(firev);
//		StringBuilder sb = new StringBuilder("{\"employee\":");
//		sb.append(e);
//		sb.append("}");
		log.trace(e);
		resp.getWriter().write(e);
	}

	
	
}

