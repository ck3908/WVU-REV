package services;

import java.util.List;

import carDAOs.EmpDAO;
import carDAOs.EmpOracle;
import entities.CarDetail;
import entities.CarOffer;

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

	@Override
	public List<CarDetail> getAllPmt() {
		// TODO Auto-generated method stub
		return ed.seeAllPmts();
	}

	@Override
	public List<CarOffer> getAllOffers() {
		// TODO Auto-generated method stub
		return ed.findAllOffers();
	}

	@Override
	public int updateStatus(CarOffer car) {
		// TODO Auto-generated method stub
		return ed.updateCarStatus(car);
	}

	@Override
	public int carOwnedUpdate(CarDetail car) {
		// TODO Auto-generated method stub
		return ed.updateOwnStatus(car);
	}

	@Override
	public int rejOffers(String acName, int platen, String status) {
		// TODO Auto-generated method stub
		return ed.rejOtherOffers(acName, platen, status);
	}

	@Override
	public List<CarDetail> seeAllCars() {
		// TODO Auto-generated method stub
		return ed.getAllCars();
	}

	@Override
	public int upDateOwnT(String name, int plate) {
		// TODO Auto-generated method stub
		return ed.updateTableOwner(name, plate);
	}



}
