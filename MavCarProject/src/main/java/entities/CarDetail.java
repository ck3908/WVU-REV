package entities;

public class CarDetail {

	private int id;
	private String carName;
	private int plate; // unique key
	private String owned; // use as true or false, but could be used as owner name if owned
	private float selling_price;  // if not currently owned, it is offer price. If owned, it is sold price
	private float downpayment;  //may not need this
	private float totalPayments;
	private int financingDeal;  // assume 3 packages 
	private float monthlyPmt;
	private int termRemaining;
	private float prinBal;
	
	public CarDetail() {
		this.id = 0;
		this.carName = " ";
		this.plate = 0;
		this.owned = "false"; //assume false as initial setting
		this.selling_price = 0;
		this.downpayment = 0; 
		this.totalPayments = 0;
		this.financingDeal = 0;  
		this.monthlyPmt = 0;
		this.termRemaining = 0;
		this.prinBal = 0;
	}
	
	
	public CarDetail(String carName, int plate, String owned, float selling_price, float downpayment,
			float totalPayments, int financingDeal, float monthlyPmt, int termRemaining, float prinBal) {
		super();
		this.carName = carName;
		this.plate = plate;
		this.owned = owned;  // if not zero, the number key references to the owner's name
		this.selling_price = selling_price;
		this.downpayment = downpayment;
		this.totalPayments = totalPayments;
		this.financingDeal = financingDeal;
		this.monthlyPmt = monthlyPmt;
		this.termRemaining = termRemaining;
		this.prinBal = prinBal;
	}
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public int getPlate() {
		return plate;
	}
	public void setPlate(int plate) {
		this.plate = plate;
	}
	public String getOwned() {
		return owned;
	}
	public void setOwned(String owned) {
		this.owned = owned;
	}
	public float getSelling_price() {
		return selling_price;
	}
	public void setSelling_price(float selling_price) {
		this.selling_price = selling_price;
	}
	public float getDownpayment() {
		return downpayment;
	}
	public void setDownpayment(float downpayment) {
		this.downpayment = downpayment;
	}
	public float getTotalPayments() {
		return totalPayments;
	}
	public void setTotalPayments(float totalPayments) {
		this.totalPayments = totalPayments;
	}
	public int getFinancingDeal() {
		return financingDeal;
	}
	public void setFinancingDeal(int financingDeal) {
		this.financingDeal = financingDeal;
	}
	public float getMonthlyPmt() {
		return monthlyPmt;
	}
	public void setMonthlyPmt(float monthlyPmt) {
		this.monthlyPmt = monthlyPmt;
	}
	public int getTermRemaining() {
		return termRemaining;
	}
	public void setTermRemaining(int termRemaining) {
		this.termRemaining = termRemaining;
	}
	public float getPrinBal() {
		return prinBal;
	}
	public void setPrinBal(float prinBal) {
		this.prinBal = prinBal;
	}
	
	@Override
	public String toString() {
		return "Car Details [name =" + carName + ", plate=" + plate + ", availability =" + owned
				+ ", selling price =" + selling_price + ", down payment =" + downpayment + 
				", total payments =" + totalPayments +", financing deal package =" + financingDeal + 
				", monthly payments =" + monthlyPmt + ", principal balance remaining =" + prinBal + "]";
	}
	
}
