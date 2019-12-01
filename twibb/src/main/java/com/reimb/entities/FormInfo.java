package com.reimb.entities;

import java.time.LocalDate;
import java.util.Date;

public class FormInfo {
	
	private Integer id;
	private Integer empId;
	private Date subDate;
	private String empLoc;
	private Integer ReqAmt;
	private Integer ReimbAmt;
	private Integer GradeFmt;

	
	public FormInfo() {
		super();
		this.id = 0;
		this.empId = 0;
		this.subDate = new Date();
		this.empLoc = " ";
		this.ReqAmt = 0;
		this.ReimbAmt = 0;
		this.GradeFmt = 0;
	}	


	public FormInfo(Integer id, Integer empId, Date subDate, String empLoc, Integer reqAmt, Integer reimbAmt,
			Integer gradeFmt) {
		super();
		this.id = id;
		this.empId = empId;
		this.subDate = subDate;
		this.empLoc = empLoc;
		this.ReqAmt = reqAmt;
		this.ReimbAmt = reimbAmt;
		this.GradeFmt = gradeFmt;
	}
	
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Date getSubDate() {
		return subDate;
	}
	public void setSubDate(Date subDate) {
		this.subDate = subDate;
	}
	public String getEmpLoc() {
		return empLoc;
	}
	public void setEmpLoc(String empLoc) {
		this.empLoc = empLoc;
	}
	public Integer getReqAmt() {
		return ReqAmt;
	}
	public void setReqAmt(Integer reqAmt) {
		ReqAmt = reqAmt;
	}
	public Integer getReimbAmt() {
		return ReimbAmt;
	}
	public void setReimbAmt(Integer reimbAmt) {
		ReimbAmt = reimbAmt;
	}
	public Integer getGradeFmt() {
		return GradeFmt;
	}
	public void setGradeFmt(Integer gradeFmt) {
		GradeFmt = gradeFmt;
	}


	@Override
	public String toString() {
		return "FormInfo [id=" + id + ", empId=" + empId + ", subDate=" + subDate + ", empLoc=" + empLoc + ", ReqAmt="
				+ ReqAmt + ", ReimbAmt=" + ReimbAmt + ", GradeFmt=" + GradeFmt + "]";
	}
	
	

}
