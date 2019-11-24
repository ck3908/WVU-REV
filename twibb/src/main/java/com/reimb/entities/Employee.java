package com.reimb.entities;

public class Employee {
	
	private Integer id;
	private String name;
	private String pass;
	private Integer dept;
	private Integer supId;
	private Integer deptHId;  //may not be needed

	public Employee() {
		super();
		this.id = 0;
		this.name = " ";
		this.pass = " ";
		this.dept = 0;
		this.supId = 0;
		this.deptHId = 0;
	}
	
	
	public Employee(Integer id, String name, String pass, Integer dept, Integer supId, Integer deptHId) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.dept = dept;
		this.supId = supId;
		this.deptHId = deptHId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public Integer getSupId() {
		return supId;
	}
	public void setSupId(Integer supId) {
		this.supId = supId;
	}
	public Integer getDeptHId() {
		return deptHId;
	}
	public void setDeptHId(Integer deptHId) {
		this.deptHId = deptHId;
	}
	
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", pass=" + pass + ", dept=" + dept + ", supId=" + supId
				+ ", deptHId=" + deptHId + "]";
	}
	

}
