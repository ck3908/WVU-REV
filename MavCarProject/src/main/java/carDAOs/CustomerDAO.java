package carDAOs;

import java.util.List;

import entities.CarDetail;
import entities.CarOffer;

public interface CustomerDAO {
	
	public List<CarDetail> viewCarLot();  //user can view cars available
	public List<CarDetail> viewMyCars(String username);
	public int enterBid(CarOffer custBid);
	public List<CarOffer> getMyBids(String username);
}
