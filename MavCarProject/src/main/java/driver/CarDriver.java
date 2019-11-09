package driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import carDAOs.UserInfoOracle;
import carUtils.LogUtil;
import entities.User;
import carUtils.ConnectionUtil;

//import com.revature.beans.User;
//import com.revature.driver.Driver;
//import com.revature.utils.LogUtil;



public class CarDriver {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Hello, welcome to the car dealership system.");
		System.out.println("In this system, depending on your credentials");
		System.out.println("you will have access to the system to do all or");
		System.out.println("some of the functionalities of this system.");
		System.out.println();
		System.out.println();
		System.out.println("Please choose from the following");
		System.out.println("1. Login");
		System.out.println("2. View Cars on the Lot as a guest");
		System.out.println("3. Register into the System as Customer or Employee");
		System.out.println("4. Logout");
		
		Scanner input = new Scanner(System.in);
		int answer = Integer.parseInt(input.nextLine()); //Use nexLline so linefeed auto
		
		if (answer == 1) {  //get login credentials
			System.out.println("Please enter user name");
			String userName = input.nextLine();
			System.out.println("Please enter your password");
			String passWord = input.nextLine();
			System.out.println("print we got your details "+userName+ " "+passWord);
			UserInfoOracle checkUser = new UserInfoOracle();
			
			User u = checkUser.validateUser(userName, passWord);
			if(u != null) {
				System.out.println("We logged in!");
				System.out.println(u.getName());
			} else {
				System.out.println("Sorry, login name or password is invalid");
			}
						
			
		}

	}
	
	
	// keep bottom for referencing codes
	public static User getUser2(String username, String password) {
		User u = null;
		String sql = "select name, code"
				+" from userinfo where name = ? and password = ?";
		try(Connection conn = cu.getConnection()){
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				u = new User();
		
				u.setName((rs.getString("name")));
				u.setUser_code((rs.getString("code")));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Driver.class);
		}
		return u;
	}


}
