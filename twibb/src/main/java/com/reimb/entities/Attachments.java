package com.reimb.entities;

public class Attachments {

	private Integer id;
	private Integer formId;
	private String urlStr;
	
	
	public Attachments() {
		super();
		this.id = 0;
		this.formId = 0;
		this.urlStr = " ";
	}
	
	
	public Attachments(Integer id, Integer formId, String urlStr) {
		super();
		this.id = id;
		this.formId = formId;
		this.urlStr = urlStr;
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
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}


	@Override
	public String toString() {
		return "Attachments [id=" + id + ", formId=" + formId + ", urlStr=" + urlStr + "]";
	}
	
	
	
}
