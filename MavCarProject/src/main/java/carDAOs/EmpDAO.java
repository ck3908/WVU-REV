package carDAOs;

import java.util.List;

import entities.CarDetail;
import entities.CarOffer;

public interface EmpDAO {
	public int addToLot(CarDetail nCar);
	public void delLot(Integer plate);
	public List<CarDetail> seeAllPmts();
	public List<CarOffer> findAllOffers();
	public int updateCarStatus(CarOffer cS);
	public int updateOwnStatus(CarDetail car);
	public int rejOtherOffers(String accName, int platenum, String status);
	public List<CarDetail> getAllCars();
}
