package com.reimb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.reimb.delegates.FrontControllerDelegate;
import com.reimb.delegates.LoginDelegateImp;



public class ReqDispatcher {
	
	private Logger log = Logger.getLogger(ReqDispatcher.class);
	private Map<String, FrontControllerDelegate> delegateMap;
	{
		delegateMap = new HashMap<String, FrontControllerDelegate>();
		
		delegateMap.put("login", new LoginDelegateImp());
		
		
		delegateMap.put("getBook",
			(req, resp) -> {
				req.getRequestDispatcher("/static/books.html").forward(req, resp);
			});
		delegateMap.put("addBook",
				(req, resp) -> {
					req.getRequestDispatcher("/static/addbook.html").forward(req, resp);
				});
		delegateMap.put("editBook",
				(req, resp) -> {
					req.getRequestDispatcher("/static/editbook.html").forward(req, resp);
				});
	}
	public FrontControllerDelegate dispatch(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		log.trace("Calling our Request Delegate: "+req.getRequestURI());

		
		// ADD CORS - thank me later
		addCorsHeader(req.getRequestURI(), resp);
		if("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {}; // empty delegate
		}
		// back to the other stuff
		// Input: "BookStore/books"
		// Input: "BookStore/books/1"
		// Input: "BookStore/books/"
		StringBuilder switchString = new StringBuilder(req.getRequestURI());
		// remove context path
		switchString.replace(0, req.getContextPath().length()+1, "");
		/*
		 * books
		 * books/1
		 * books/
		 */
		// remove the first part of the string ending with /
		if(switchString.indexOf("/")!=-1) {
			// save the remains for later
			req.setAttribute("path",
					switchString.substring(switchString.indexOf("/")+1));
			switchString.replace(switchString.indexOf("/"), switchString.length(), "");
		}
		log.trace("at dispatch, the switchString is  "+switchString);
		log.trace("at dispatch, the to string of switchstring is "+switchString.toString());
		return delegateMap.get(switchString.toString());
	}
	private void addCorsHeader(String requestURI, HttpServletResponse resp) {
		log.trace("adding headers");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		resp.addHeader("Vary", "Origin");
		//if I don't care about getting my cookie, this will work
		//response.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Max-Age", "1728000");
        resp.addHeader("Produces", "application/json");
	}

}
