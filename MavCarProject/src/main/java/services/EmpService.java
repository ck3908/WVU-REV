package services;

import entities.CarDetail;

public interface EmpService {
	public int addCar(CarDetail newCar);
	public void delCar(Integer platenum);

}
