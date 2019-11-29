package com.reimb.entities;

public class ReqFC {
	
	private Integer id;
	private Integer formId;
	private Integer askId; //asker id
	private String question;
	private String answer;
	
	public ReqFC() {
		super();
		this.id = 0;
		this.formId = 0;
		this.askId = 0;
		this.question = " ";
		this.answer = " ";
	}
	
	
	public ReqFC(Integer id, Integer formId, Integer askId, String question, String answer) {
		super();
		this.id = id;
		this.formId = formId;
		this.askId = askId;
		this.question = question;
		this.answer = answer;
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
	public Integer getAskId() {
		return askId;
	}
	public void setAskId(Integer askId) {
		this.askId = askId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return "ReqFC [id=" + id + ", formId=" + formId + ", askId=" + askId + ", question=" + question + ", answer="
				+ answer + "]";
	}
	
	

}
