package carDAOs;

import entities.CarDetail;

public interface EmpDAO {
	public int addToLot(CarDetail nCar);
	public void delLot(Integer plate);
}
