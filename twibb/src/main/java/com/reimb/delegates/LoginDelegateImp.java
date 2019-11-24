package com.reimb.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.services.CustomerService;
import com.revature.services.CustomerServiceOracle;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceOracle;

public class LoginDelegateImp implements FrontControllerDelegate{
	
	private Logger log = Logger.getLogger(LoginDelegateImp.class);
	private EmployeeService es = new EmployeeServiceOracle();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
			checkLogin(req, resp);
			break;
		case "POST":
			// logging in
			Employee emp = (Employee) session.getAttribute("loggedEmployee");
			if (emp != null || cust != null) {
				respondWithUser(resp, emp, cust);
			} else {
				checkLogin(req, resp);
			}
			break;
		case "DELETE":
			// logging out
			session.invalidate();
			// disassociates an id with a session and response says to delete cookie
			log.trace("User logged out");
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			break;
		default:
			break;
		}
	}

	private void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.trace("Logging in!");
		HttpSession session = req.getSession();
		Customer c = (Customer) session.getAttribute("loggedCustomer");
		Employee e = (Employee) session.getAttribute("loggedEmployee");
		if (c != null || e != null) {
			respondWithUser(resp, e, c);
		} else {

			// Need to see if we are an employee. Then we need to see if we are a customer.
			// Then we need to store that information in the session object.
			String username = req.getParameter("user");
			String password = req.getParameter("pass");
			log.trace((username + " " + password));
			c = cs.getCustomer(username, password);
			e = es.getEmployee(username, password);

			if (c != null) {
				log.trace("cust being added to session me");
				session.setAttribute("loggedCustomer", c);
			}
			if (e != null) {
				log.trace("empl being added to session you");
				session.setAttribute("loggedEmployee", e);
			}
			log.trace("checking the other if statements after employee added to session");
			if (c == null && e == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No user found with those credentials");
			} else {
				log.trace("preparing response back to xmlhttrequest");
				respondWithUser(resp, e, c);
			}
		}
	}

	private void respondWithUser(HttpServletResponse resp, Employee emp, Customer cust) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		log.trace("building string response");
		String c = om.writeValueAsString(cust);
		String e = om.writeValueAsString(emp);
		StringBuilder sb = new StringBuilder("{\"customer\":");
		sb.append(c);
		sb.append(", \"employee\":");
		sb.append(e);
		sb.append("}");
		log.trace(sb);
		resp.getWriter().write(sb.toString());
	}

}
