package com.reimb.entities;

public class FormRej {
	
	private Integer id;
	private Integer formId;
	private Integer rejId; //employee rejecting the form
	private String reason;
	
	public FormRej() {
		super();
		this.id = 0;
		this.formId = 0;
		this.rejId = 0;
		this.reason = " ";
	}
	
	
	
	public FormRej(Integer id, Integer formId, Integer rejId, String reason) {
		super();
		this.id = id;
		this.formId = formId;
		this.rejId = rejId;
		this.reason = reason;
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
	public Integer getrejId() {
		return rejId;
	}
	public void setrejId(Integer rejId) {
		this.rejId = rejId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "FormRej [id=" + id + ", formId=" + formId + ", rejId=" + rejId + ", reason=" + reason + "]";
	}
	
	
	
}
