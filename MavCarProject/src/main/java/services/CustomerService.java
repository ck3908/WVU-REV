package services;

import java.util.List;

import entities.CarDetail;
import entities.CarOffer;

public interface CustomerService {

//	public List<CarDetail> viewCarLot();  //user can view cars available
	
	public List<CarDetail> getCarsAvail();
	public List<CarDetail> getMyCars(String username);
	public int putBid(CarOffer myBid);
	public List<CarOffer> seeMyBids(String username);
	
}
