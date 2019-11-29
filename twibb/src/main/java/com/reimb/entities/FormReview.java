package com.reimb.entities;

public class FormReview {
	
	private Integer id;
	private Integer reviewId;
	private Integer formId;
	
	public FormReview() {
		super();
		this.id = 0;
		this.reviewId = 0;
		this.formId = 0;
	}
	
	
	public FormReview(Integer id, Integer reviewId, Integer formId) {
		super();
		this.id = id;
		this.reviewId = reviewId;
		this.formId = formId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReviewId() {
		return reviewId;
	}
	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	
	@Override
	public String toString() {
		return "FormReview [id=" + id + ", reviewId=" + reviewId + ", formId=" + formId + "]";
	}  
	
	
	
	

}
