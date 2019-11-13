package services;

import java.util.List;

import entities.CarDetail;
import entities.CarOffer;

public interface EmpService {
	public int addCar(CarDetail newCar);
	public void delCar(Integer platenum);
	public List<CarDetail> getAllPmt();
	public List<CarOffer> getAllOffers();
	public int updateStatus(CarOffer car);
	public int carOwnedUpdate(CarDetail car);
	public int rejOffers(String acName, int platen, String status);
	public List<CarDetail> seeAllCars();
	public int upDateOwnT(String name, int plate);
	
	

}
