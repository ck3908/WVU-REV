package services;

import java.util.ArrayList;
import java.util.List;

import carDAOs.CustomerDAO;
import carDAOs.CustomerOracle;
import entities.CarDetail;
import entities.CarOffer;

public class CustomerServiceOracle implements CustomerService {
	private CustomerDAO cd = new CustomerOracle();

	@Override
	public List<CarDetail> getCarsAvail() {
		// TODO Auto-generated method stub
		List<CarDetail> carsInLot = new ArrayList<CarDetail>();
		carsInLot = cd.viewCarLot();
		return carsInLot;
	}

	@Override
	public List<CarDetail> getMyCars(String username) {
		// TODO Auto-generated method stub
		List<CarDetail> carsMine = new ArrayList<CarDetail>();
		carsMine = cd.viewMyCars(username);
		return carsMine;
	
	}

	@Override
	public int putBid(CarOffer myBid) {
		// TODO Auto-generated method stub
		return cd.enterBid(myBid);
	}

	@Override
	public List<CarOffer> seeMyBids(String username) {
		// TODO Auto-generated method stub
		List<CarOffer> myBids = new ArrayList<CarOffer>();
		myBids = cd.getMyBids(username);
		//System.out.println(myBids);
		return myBids;
	}
	



	
	
	
//	@override
//	public List<CarDetail> getCarsAvail(){
//		List<CarDetail> carsAvail = new ArrayList<CarDetail>();
//		
//		return carsAvail;
//	}

}
