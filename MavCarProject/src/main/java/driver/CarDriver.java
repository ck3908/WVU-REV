package driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import carDAOs.UserInfoOracle;
import carUtils.LogUtil;
import entities.CarDetail;
import entities.User;
import services.CustomerService;
import services.CustomerServiceOracle;
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
				// check if customer, employee or manager
//				switch(u.getUser_code()) {
//				case "cust":
				System.out.println("user code is " +u.getUser_code());
				if (u.getUser_code().equals("cust")){
					boolean keepSession = true;
					while (keepSession) {  // customer session
						System.out.println("please select from following options");
						System.out.println("1. View the cars on the lot");
						System.out.println("2. Make an offer for a car");
						System.out.println("3. View cars you own");
						System.out.println("4. View remaining payments");
						System.out.println("5. Logout");
						int selection = Integer.parseInt(input.nextLine());
						if (selection == 5) {
							System.out.println("good bye");
							keepSession = false;
							break;
						}
						else if (selection == 1) {
							CustomerService carsToSee = new CustomerServiceOracle();
							List<CarDetail> cs = new ArrayList<>();
							cs = carsToSee.getCarsAvail();
							System.out.println("  car    asking price");  //took out plate number
							for (int i =0; i< cs.size(); ++i) {
								System.out.println(i+1+ "  "+cs.get(i).getCarName()+"  "+cs.get(i).getSelling_price() );
							}							
						}
						else if (selection == 3) {
							CustomerService seeMyCars = new CustomerServiceOracle();
							List<CarDetail> gmc = new ArrayList<>();
							gmc = seeMyCars.getMyCars(userName);
							System.out.println("  car    plate");  //took out plate number
							for (int i =0; i< gmc.size(); ++i) {
								System.out.println(i+1+ "  "+gmc.get(i).getCarName()+"  "+gmc.get(i).getPlate() );
							}
						}
					}
				;
					
					
				}
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
