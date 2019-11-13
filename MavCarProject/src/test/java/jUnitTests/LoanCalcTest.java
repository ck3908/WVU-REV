package jUnitTests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import driver.CarDriver;

public class LoanCalcTest {
	
	

	@Test
	public void TestCalculator1() {
		CarDriver cd = new CarDriver();  //need the class in order to call its static method below
		double monthlyPayment = cd.monthlyCalc(180, 0.0525, 300000);
		assertThat((int) monthlyPayment,is(2412));
	}
	
	@Test
	public void TestCalculator2() {
		CarDriver cd = new CarDriver();  //need the class in order to call its static method below
		double monthlyPayment = cd.monthlyCalc(240, 0.0392, 100000);
		assertThat((int) monthlyPayment,is(602));
	}
	
	@Test
	public void TestCalculator3() {
		CarDriver cd = new CarDriver();  //need the class in order to call its static method below
		double monthlyPayment = cd.monthlyCalc(240, 0.0231, 849999);
		assertThat((int) monthlyPayment,is(4426));
	}
	
	
	

}
