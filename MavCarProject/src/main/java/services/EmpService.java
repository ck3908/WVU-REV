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

}
