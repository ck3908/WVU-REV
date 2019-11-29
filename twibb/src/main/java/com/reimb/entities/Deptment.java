package com.reimb.entities;

public class Deptment {
	
	private Integer id;
	private Integer deptNum;
	private String deptName;
	private Integer deptSup; // refers to employee id
	
	
	public Deptment() {
		super();
		this.id = 0;
		this.deptNum = 0;
		this.deptName = " ";
		this.deptSup = 0;
	}
	
	
	
	public Deptment(Integer id, Integer deptNum, String deptName, Integer deptSup) {
		super();
		this.id = id;
		this.deptNum = deptNum;
		this.deptName = deptName;
		this.deptSup = deptSup;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(Integer deptNum) {
		this.deptNum = deptNum;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getDeptSup() {
		return deptSup;
	}
	public void setDeptSup(Integer deptSup) {
		this.deptSup = deptSup;
	}
	@Override
	public String toString() {
		return "Deptment [id=" + id + ", deptNum=" + deptNum + ", deptName=" + deptName + ", deptSup=" + deptSup + "]";
	}
	
	
	

}
