package com.reimb.entities;

public class FormStatus {

	private Integer id;
	private Integer empId;
	private Integer formId;
	private Integer status;
	
	public FormStatus() {
		super();
		this.id = 0;
		this.empId = 0;
		this.formId = 0;
		this.status = 0;
	}
	
	
	
	public FormStatus(Integer id, Integer empId, Integer formId, Integer status) {
		super();
		this.id = id;
		this.empId = empId;
		this.formId = formId;
		this.status = status;
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
	
	public Integer getFormId() {
		return formId;
	}


	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "FormStatus [id=" + id + ", empId=" + empId + ", formId=" + formId + ", status=" + status + "]";
	}
	

	
	
}
