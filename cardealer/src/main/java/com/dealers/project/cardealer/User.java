package cardealer.src.main.java.com.dealers.project.cardealer;

public class User {
	
	private String name;
	private String pass;
	private String user_code;  // cust = customer, emp= employee, man= manager
	
	//initial object 
	public User() {
		this.name = " ";
		this.pass = " ";
		this.user_code = " ";
	}
	
	public User(String name, String pass, String user_code) {
		super();
		this.name = name;
		this.pass = pass;
		this.user_code = user_code;
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
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	
	

}
