package com.reimb.entities;

public class Grading {
	
	private Integer gradingId;
	private String requirements;
	
	public Grading() {
		super();
		this.gradingId = 0;
		this.requirements = " ";
	}
	
	
	public Grading(Integer gradingId, String requirements) {
		super();
		this.gradingId = gradingId;
		this.requirements = requirements;
	}
	
	public Integer getGradingId() {
		return gradingId;
	}
	public void setGradingId(Integer gradingId) {
		this.gradingId = gradingId;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	
	
	@Override
	public String toString() {
		return "Grading [gradingId=" + gradingId + ", requirements=" + requirements + "]";
	}
	

}
