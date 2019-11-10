package services;

import java.util.List;

import entities.CarDetail;

public interface CustomerService {

//	public List<CarDetail> viewCarLot();  //user can view cars available
	
	public List<CarDetail> getCarsAvail();
	public List<CarDetail> getMyCars(String username);
	
}
