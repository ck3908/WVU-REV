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
import services.EmpService;
import services.EmpServiceOracle;
import services.UserService;
import services.UserServiceOracle;
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
		System.out.println("1. Login for customer and employees");
		System.out.println("2. View Cars on the Lot as a guest");
		System.out.println("3. Register into the System as Customer or Employee");
		System.out.println("4. Use the loan calculator");
		System.out.println("5. Logout");

		Scanner input = new Scanner(System.in);
		int answer = Integer.parseInt(input.nextLine()); //Use nexLline so linefeed auto

		if (answer == 1) {  //get login credentials
			System.out.println("Please enter user name");
			String userName = input.nextLine();
			System.out.println("Please enter your password");
			String passWord = input.nextLine();
			System.out.println("print we got your details "+userName+ " "+passWord);
			UserService getU= new UserServiceOracle();

			User u = getU.checkUser(userName, passWord);
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
						System.out.println("5. View cars you are bidding for");
						System.out.println("6. Use the loan calculator");
						System.out.println("7. Logout");
						int selection = Integer.parseInt(input.nextLine());
						if (selection == 7) {
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
						else if(selection == 5) { // see cars you are bidding
							List <CarOffer> e = new ArrayList<>();
							CustomerService cOffers = new CustomerServiceOracle();
							e = cOffers.seeMyBids(userName);
							System.out.println("  car   plate   bidder   offer price    status ");
							for (int t =0; t < e.size(); ++t) {
								System.out.println(t+1+ "   "+e.get(t).getVehName()+"   "+e.get(t).getPlateNum() +"   "+
										e.get(t).getBuyer() +"   "+e.get(t).getOfferPrice()+ "  " +e.get(t).getStatus());
							}
							
						}
						else if(selection == 6) {  //use loan calculator
							useMonthlyCalc(input);  //pass in the Scanner input
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
								int downpmt = Integer.parseInt(input.nextLine());  //probably should make it a double 
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
									double netLoan = bid-downpmt;

									if ((k > 0) && (k <= 3)) {  // make sure valid selection
										int tMonths = 48+(k-1)*12; //to get the key of hashmap
										System.out.println("On your loan of "+netLoan+""
												+ " term of loan "+tMonths+" months and rate of "+loanTypes.get(tMonths));
										System.out.println("your monthly payment is "+getPmts.get(k-1));  //note getPmts.get(index) contains index of monthly payments already
										System.out.println("please confirm your bid - enter 1 for yes, 0 for no");									 
										int s = Integer.parseInt(input.nextLine());  
										if (s == 1) {  //create the offer for the car
											CarOffer cOffer = new CarOffer(carName, carPlate, userName, (float) bid, "pending", (float) downpmt, k);
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
									else {  //wrong selection
										System.out.println("you have to pick a number 1, 2, or 3");
									}
								}

							} // user bid block - put in while loop eventually with bid session and a flag

						}  // select == 2


					}  //while user  logged in  session block
				}  //if user code is customer

				else if(u.getUser_code().equals("emp")) {  //employee login
					boolean empSession = true;
					while (empSession) {
						System.out.println("please select from following options");
						System.out.println("1. Add a car to the lot");
						System.out.println("2. View all cars in the lot sold and available");
						System.out.println("3. Accept or reject a pending offer");
						System.out.println("4. Remove a car from the lot");
						System.out.println("5. View all payments");
						System.out.println("6. Logout");
						int sc = Integer.parseInt(input.nextLine());
						if (sc == 6) {
							System.out.println("good bye");
							empSession = false;
							break;
						}
						if (sc == 1) {  // add car to the lot
							System.out.println("you like to add a car to the lot");
							System.out.println("please input make of the car");
							String make = input.nextLine();
							System.out.println("please input license number - must be unqiue");
							int lic = Integer.parseInt(input.nextLine());
							System.out.println("please input offer price");
							double carP = Double.parseDouble(input.nextLine());
							CarDetail nCar = new CarDetail();  //car empty constructor to initialize variables to avoid nulls
							nCar.setCarName(make);  //now specifically change fields to set
							nCar.setPlate(lic);
							nCar.setSelling_price((float) carP);
							EmpService addVeh = new EmpServiceOracle();
							int r = addVeh.addCar(nCar);							

						} // selection == 2  see all cars in the system
						else if (sc == 2) {
							List<CarDetail> b = new ArrayList<>();
							EmpService atc = new EmpServiceOracle();
							b = atc.seeAllCars();
							System.out.println("carname    platenum    selling price    owned status");
							for (int w = 0; w < b.size(); ++w) {
								System.out.println(w+1+" "+b.get(w).getCarName() +" "+b.get(w).getPlate() +" "+b.get(w).getSelling_price()+" "+b.get(w).getOwned());
							}			
						}
						else if (sc == 3) { //accept or reject a pending offer
							List <CarOffer> g = new ArrayList<>();
							EmpService gOffers = new EmpServiceOracle();
							g = gOffers.getAllOffers();
							System.out.println("  car   plate   bidder   offer price    down payment  status  loan type");
							for (int t =0; t < g.size(); ++t) {
								System.out.println(t+1+ "   "+g.get(t).getVehName()+"   "+g.get(t).getPlateNum() +"   "+
										g.get(t).getBuyer() +"   "+g.get(t).getOfferPrice()+ "   "+g.get(t).getDownPmt() +"   "+g.get(t).getStatus()
										+"   "+g.get(t).getTermFinance());
							}
							System.out.println("what do you like to do? accept = 1, reject = 2, exit = 3");
							int pc = Integer.parseInt(input.nextLine());
							if (pc == 1) { // accept a car
								System.out.println("enter the number of the offer to accept ");
								int ac = Integer.parseInt(input.nextLine());
								g.get(ac-1).setStatus("accepted"); //change the status of the offer
								CarOffer changeStatus = new CarOffer();
								changeStatus = g.get(ac-1);  // save the updated object into a another object for clarity
								EmpService statUpdate = new EmpServiceOracle();
								int bc = statUpdate.updateStatus(changeStatus);
								System.out.println("bc here is "+bc);
								if (bc == 1) { // update cardetails table to owned
									//System.out.println("success holy moly");
									CarDetail soldCar = new CarDetail(); //initialize object
									soldCar.setPlate(g.get(ac-1).getPlateNum()); //get the plate number to search for unique record in the table
									soldCar.setSelling_price(g.get(ac-1).getOfferPrice()); //set selling price to bidder's price
									soldCar.setOwned("true"); //change field to true
									soldCar.setDownpayment(g.get(ac-1).getDownPmt()); // get the downpayment info
									soldCar.setTotalPayments(g.get(ac-1).getDownPmt()); //assume the first downpayment made so total payments = downpayments
									soldCar.setFinancingDeal(g.get(ac-1).getTermFinance()); //set loan type
									float balDue = g.get(ac-1).getOfferPrice() - g.get(ac-1).getDownPmt();
									soldCar.setPrinBal(balDue); //balance is sold price less the downpayment
									int nMonths = 48+(g.get(ac-1).getTermFinance()-1)*12; // get the financing terms if = 1 then 48 months, 2 then 60 months etc
									soldCar.setTermRemaining(nMonths);  //set the months of the term loan
									HashMap<Integer,Double> tLoan = new HashMap<Integer,Double>();  
									tLoan = finDeals();  //get matrix of rates and months via hashmap table
									double aRate = tLoan.get(nMonths);  //extract the rate from hash map table
									double mDue = monthlyCalc(nMonths, aRate, balDue);
									soldCar.setMonthlyPmt((float) mDue); 
									int cc = statUpdate.carOwnedUpdate(soldCar);
									if (cc > 0) {  // updated cardetail database, now update other bidders to reject in caroffer
										//System.out.println("successfully updated cardetail table");
										int acceptPlate = g.get(ac-1).getPlateNum(); // pick the offer that was accepted by owner and plate
										String acceptName = g.get(ac-1).getBuyer();  // then reject all others
										String rejStatus = "rejected";
										EmpService rejUpdate = new EmpServiceOracle();
										int rejResult = rejUpdate.rejOffers(acceptName, acceptPlate, rejStatus);  //update offers to rejected status
										if (rejResult >= 0) {
											
											// now update the owner car table which is the bridge table
											EmpService OwnUpdate = new EmpServiceOracle();
											int own = OwnUpdate.upDateOwnT(acceptName, acceptPlate);
											if(own == 1) {
												System.out.println("successful update");
											}
											else {
												System.out.println("owner table didn't update");
											}
										}
										else {
											System.out.println("update not done");
										}
										
									}
									// reject all other offers
									
								}
								else {
									System.out.println("something wrong updating");
								}
							}  // pc = 1 accept offer
							else if (pc == 2) {  // selection is 2
								//reject offer
								System.out.println("enter the number of the offer to reject ");
								int rc = Integer.parseInt(input.nextLine());
								g.get(rc-1).setStatus("rejected"); //change the status of the offer
								CarOffer changeStatus = new CarOffer();
								changeStatus = g.get(rc-1);  // save the updated object into a another object for clarity
								EmpService statUpdate = new EmpServiceOracle();
								int dc = statUpdate.updateStatus(changeStatus);
								if (dc == 1) {
									System.out.println("successfully updated an rejected offer");
								}
								else {
									System.out.println("not successfully updated rejected offer");
								}
							}
							else {  // could be selection = 3
								// do anything? if nothing selected
							}

						}
						else if (sc == 4) {  // remove a car from lot
							List<CarDetail> getVeh = new ArrayList<>();
							getVeh = carsToSeeHelper();  // retrieve car from the lot
							System.out.println("select the number of the car you like to delete");
							int gs = Integer.parseInt(input.nextLine());
							System.out.println("you have selected "+getVeh.get(gs-1).getCarName()+" plate num "+getVeh.get(gs-1).getPlate());
							System.out.println("please confirm 1 for yes and  0 for no");
							int ls = Integer.parseInt(input.nextLine());
							if (ls == 1) {  //delete car from lot
								int carPlate = getVeh.get(gs-1).getPlate();
								EmpService deleteCar = new EmpServiceOracle();
								deleteCar.delCar(carPlate);
								// delete the car						
							}

						}
						else if (sc == 5) {  //view all payments
							EmpService allCarPmts = new EmpServiceOracle();
							List<CarDetail> cd = new ArrayList<>();
							cd = allCarPmts.getAllPmt();
							System.out.println("  car    plate      total paid     monthly due     term remaining     balance  ");  //took out plate number
							for (int t =0; t < cd.size(); ++t) {
								System.out.println(t+1+ "  "+cd.get(t).getCarName()+" "+cd.get(t).getPlate()+ " "+
										cd.get(t).getTotalPayments()+"  "+cd.get(t).getMonthlyPmt()+ "  "+cd.get(t).getTermRemaining()+
										"  "+cd.get(t).getPrinBal());
							}
						}

					}  // while employee session


				}
				else {  // manager login 

				}

			} // if user is validated
			else {
				System.out.println("Sorry, login name or password is invalid");
			} 

		} // if selection was 1 to login

		else if (answer == 3) {  // register account
			System.out.println("please enter new username");
			String newUser = input.nextLine();
			System.out.println("Please enter a new password");
			String newPass = input.nextLine();
			System.out.println("please enter 1-customer, 2-employee");
			String userType = input.nextLine();
			boolean userInput = true;
			if (Integer.parseInt(userType) == 1) {
				userType = "cust";
			}
			else if (Integer.parseInt(userType) == 2) {
				userType = "empl";
			}
			else {
				System.out.println("sorry wrong type selected");
				userInput = false;
			}
			if (userInput) {  // create a new customer user
				User newU = new User(newUser,newPass,userType);
				UserService u = new UserServiceOracle();
				int ok = 0;
				ok = u.inputUser(newU);
				if (ok > 0) {
					System.out.println("success new user registered, please start again to log in");
				}
				else {
					System.out.println("user not created");
				}

			}


		}
		else if (answer == 2) {
			//view cars as guest
			carsToSeeHelper();  //need to get back to top

		}
		else if (answer == 4) {  //use the loan calculator
			 useMonthlyCalc(input);  //pass in the Scanner input
			
		}
		else {  //assume 7 option chosen
			System.out.println(" goodbye, no option chosen or logging out");
			// anything else just exit -- assume logout
		}

	} // main program

	public static void useMonthlyCalc(Scanner getInput) {
		System.out.println("you like to use the loan calculator");
		System.out.println("enter annual rate i.e. 0.05, 0.067 etc");
		double inputRate = Double.parseDouble(getInput.nextLine());
		System.out.println("enter the number of months in this loan i.e. 12, 24, etc");
		int inputMonth = Integer.parseInt(getInput.nextLine());
		System.out.println("enter the principal loan amount you want to take out");
		double loanTaken = Double.parseDouble(getInput.nextLine());
		double monPymt = monthlyCalc(inputMonth, inputRate, loanTaken);
		System.out.println("based on your inputs, your monthly payment would be "+monPymt);
		
	}

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

}
