package com.reimb.entities;

import java.util.Date;

public class FormAppr {
	
	private Integer id;
	private Integer formId;
	private Integer empId; //employee submitting the form
	private Date approvDt; 
	private Integer override;
	
	
	public FormAppr() {
		super();
		this.id = 0;
		this.formId = 0;
		this.empId = 0;
		this.approvDt = new Date();
		this.override = 0;
	}
	
	
	public FormAppr(Integer id, Integer formId, Integer empId, Date approvDt, Integer override) {
		super();
		this.id = id;
		this.formId = formId;
		this.empId = empId;
		this.approvDt = approvDt;
		this.override = override;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Date getApprovDt() {
		return approvDt;
	}
	public void setApprovDt(Date approvDt) {
		this.approvDt = approvDt;
	}
	public Integer getOverride() {
		return override;
	}
	public void setOverride(Integer override) {
		this.override = override;
	}
	@Override
	public String toString() {
		return "FormAppr [id=" + id + ", formId=" + formId + ", empId=" + empId + ", approvDt=" + approvDt
				+ ", override=" + override + "]";
	}
	
	
	

}
