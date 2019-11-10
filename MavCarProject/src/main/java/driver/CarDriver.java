package driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import carDAOs.UserInfoOracle;
import carUtils.LogUtil;
import entities.CarDetail;
import entities.CarOffer;
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
				if (u.getUser_code().equals("cust")){  // handling customer block
					boolean keepSession = true;
					List<CarDetail> carCount = new ArrayList<>();  // use this later

					while (keepSession) {  // customer session
						System.out.println("please select from following options");
						System.out.println("1. View the cars on the lot");
						System.out.println("2. Make an offer for a car");
						System.out.println("3. View cars you own");
						System.out.println("4. View remaining payments for a car");
						System.out.println("5. Logout");
						int selection = Integer.parseInt(input.nextLine());
						if (selection == 5) {
							System.out.println("good bye");
							keepSession = false;
							break;
						}
						else if (selection == 1) {  // view available cars on the lot
							carsToSeeHelper();						
						}
						else if (selection == 3) { // view cars you own
							myListCarHelper(userName); // mainly for option 4  keep track of items returned mainly to help with selection 4
						}
						else if (selection == 4) { // view cars you own and then select which one to see payments for
							carCount = myListCarHelper(userName);  //return list of car objects
							int count = carCount.size();
							System.out.println("select the number car you like to view payments for");
							int n = Integer.parseInt(input.nextLine());
							if (n > count) {
								System.out.println("sorry invalid selection"); //if more time can handle this better
							}
							else {
								System.out.println("the remaining balance due on your "+carCount.get(n-1).getCarName()
										+ " is "+carCount.get(n-1).getPrinBal());
							}

						}
						else if (selection == 2) {  // make an offer for a car
							carCount = carsToSeeHelper();
							int nCars = carCount.size();
							System.out.println("select the number car you like to bid for");
							int m = Integer.parseInt(input.nextLine());
							if (m > nCars) {
								System.out.println("sorry invalid selection");
							}
							else {  // prepare user to bid
								String carName = carCount.get(m-1).getCarName();
								double carPrice = carCount.get(m-1).getSelling_price();
								int carPlate = carCount.get(m-1).getPlate();
								System.out.println("you have selected "+carName+ " the current asking price is "+carPrice);
								System.out.println("enter your bid price");
								double bid = Double.parseDouble(input.nextLine());
								System.out.println("enter down payment -> enter zero if none");
								int downpmt = Integer.parseInt(input.nextLine());
								System.out.println("we can offer you 3 financing packages - select a number or select 0 if you do not"
										+ "want a financing package");
								HashMap<Integer,Double> loanTypes = new HashMap<Integer,Double>();  // term and rates in hash map table
								loanTypes = finDeals();
								List<Double> getPmts = new ArrayList<>();
								getPmts = getPayments(loanTypes,bid,downpmt);
								
								int k = Integer.parseInt(input.nextLine());
								if (k > loanTypes.size()) {
									System.out.println(" sorry, you have selected an invalid entry");  //how to handle it more
								}
								else {  // calculate the monthly payments for the selection
									int months = 48;  // set default
									double monthlyDue = 0;
									
									if (k == 1) {  //48 months chosen
										System.out.println("On your loan of "+(bid-downpmt)+""
												+ " term of loan 48 months and rate of "+loanTypes.get(months));
										System.out.println("your monthly payment is "+getPmts.get(k-1));
										System.out.println("please confirm your bid - enter 1 for yes, any other for no");
										int s = Integer.parseInt(input.nextLine());
										if (s == 1) {  //create the offer for the car
											CarOffer cOffer = new CarOffer(carPlate, userName, (float) bid, "pending", (float) downpmt, k);
											CustomerService addBid = new CustomerServiceOracle();
											int v = addBid.putBid(cOffer);  //update the database
											if (v > 0 ) {
												System.out.println("thank you for your bid, it has been recorded");
											}
											else {
												System.out.println("sorry, issue with bid");
											}
											
										}
										else {
											// repeat?
										}
									}
									else if (k == 2) {
										System.out.println("monthly due is "+getPmts.get(k-1));	
									}
									else if (k == 3) {
										System.out.println("monthly due is "+getPmts.get(k-1));	
									}
									else {  //wrong selection
										System.out.println("you have to pick a number 1, 2, or 3");
									}
								}

							} // user bid block - put in while loop eventually with bid session and a flag

						}  // select == 2


					}  //while user  logged in  session block
				}  //if user code is customer

			} // if user is validated
			else {
				System.out.println("Sorry, login name or password is invalid");
			} 

		} // if selection was 1 to login

		else if (answer == 3) {  // register account

		}
		else if (answer == 2) {
			//view cars as guest
		}
		else {
			System.out.println(" goodbye, no option chosen or logging out");
			// anything else just exit -- assume logout
		}

	} // main program
	
	// monthly payment calculator
	public static double monthlyCalc(int months, double rate, double principal) {
		double mRate = rate/12;
		double payment = (principal * mRate) / (1 - Math.pow(1+mRate, -months));
		payment = Math.round(payment);
		return payment;
	}
	
	//get all monthly payments in one go for all deal terms
	public static List<Double> getPayments(HashMap<Integer,Double> dealTerms, double cBid, int dPymt) {
		List<Double> p = new ArrayList<>();
		// do the monthly calculations for the 3 loan types
		int termMonth = 48;  // initialize to first term
		for (int g = 0; g < dealTerms.size(); ++g) {
			// do calculations
			double annRate = dealTerms.get(termMonth).doubleValue();
			double prinBal = cBid - dPymt;
			double monthlyDue = monthlyCalc(termMonth,annRate,prinBal);
			//System.out.println("monthly due is "+monthlyDue);
			p.add(monthlyDue);  // put into array
			termMonth = termMonth+12;  // the different monthly terms are factors of 12 months
		}
		return p;
	}

	public static LinkedHashMap<Integer,Double> finDeals() {  //linked version of hashmap keep ordering to print later
		final LinkedHashMap<Integer,Double> loanMap = new LinkedHashMap<>(); // keep ordering for printing
		loanMap.put(48, 0.05);  // months, annual rates
		loanMap.put(60, 0.06); 
		loanMap.put(72, 0.07);

		int i = 1;				
		for (Integer name: loanMap.keySet()){  //print out financial deal packages
			String key = name.toString();
			String value = loanMap.get(name).toString();
			System.out.println((i++) + " loan months "+ key+ " rate is " + value);  
		} 
		return loanMap;
	}

	// this is used in a few places in main
	public static List<CarDetail> carsToSeeHelper(){  //mainly print helpers
		CustomerService carsToSee = new CustomerServiceOracle();
		List<CarDetail> cs = new ArrayList<>();
		cs = carsToSee.getCarsAvail();
		System.out.println("  car    asking price");  //took out plate number
		for (int i =0; i< cs.size(); ++i) {
			System.out.println(i+1+ "  "+cs.get(i).getCarName()+"  "+cs.get(i).getSelling_price() );
		}
		return cs;
	}

	// this is used in a few places in main
	public static List<CarDetail> myListCarHelper(String userN) {  //mainly print helpers
		CustomerService seeMyCars = new CustomerServiceOracle();
		// past object initiation
		List<CarDetail> gmc = new ArrayList<>();
		gmc = seeMyCars.getMyCars(userN);
		System.out.println("  car    plate");  
		for (int i =0; i< gmc.size(); ++i) {
			System.out.println(i+1+ "  "+gmc.get(i).getCarName()+"  "+gmc.get(i).getPlate());
		}
		return gmc;
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
