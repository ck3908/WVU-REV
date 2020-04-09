package carDAOs;

import java.util.List;

import entities.CarDetail;
import entities.CarOffer;

public interface CustomerDAO {
	
	public List<CarDetail> viewCarLot();  // Mygirls can view cars available for all to see 4/8/2020
	public List<CarDetail> viewMyCars(String username);
	public int enterBid(CarOffer custBid);
	public List<CarOffer> getMyBids(String username);
	
}
