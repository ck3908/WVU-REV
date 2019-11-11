package services;

import carDAOs.EmpDAO;
import carDAOs.EmpOracle;
import entities.CarDetail;

public class EmpServiceOracle implements EmpService {
	private EmpDAO ed = new EmpOracle();

	@Override
	public int addCar(CarDetail newCar) {
		// TODO Auto-generated method stub
		return ed.addToLot(newCar);
	}

	@Override
	public void delCar(Integer platenum) {
		// TODO Auto-generated method stub
		ed.delLot(platenum);
	}

}
