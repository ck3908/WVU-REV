package services;

import java.util.ArrayList;
import java.util.List;

import carDAOs.CustomerDAO;
import carDAOs.CustomerOracle;
import entities.CarDetail;

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
		return null;
	}


	
	
	
//	@override
//	public List<CarDetail> getCarsAvail(){
//		List<CarDetail> carsAvail = new ArrayList<CarDetail>();
//		
//		return carsAvail;
//	}

}
