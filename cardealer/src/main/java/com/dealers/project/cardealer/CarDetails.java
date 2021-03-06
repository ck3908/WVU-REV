package cardealer.src.main.java.com.dealers.project.cardealer;

public class CarDetails {
	
	private String carName;
	private int plate; // unique key
	private int owned; // flag if owned or not
	private float selling_price;  // if not currently owned, it is offer price. If owned, it is sold price
	private float downpayment;  //may not need this
	private float totalPayments;
	private int financingDeal;  // assume 3 packages 
	private float monthlyPmt;
	private int termRemaining;
	private float prinBal;
	
	public CarDetails() {
		this.carName = " ";
		this.plate = 0;
		this.owned = 0;
		this.selling_price = 0;
		this.downpayment = 0;
		this.totalPayments = 0;
		this.financingDeal = 1;  //1 ,2 or 3
		this.monthlyPmt = 0;
		this.termRemaining = 0;
		this.prinBal = 0;
	}
	
	
	public CarDetails(String carName, int plate, int owned, float selling_price, float downpayment,
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
	public int getOwned() {
		return owned;
	}
	public void setOwned(int owned) {
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
	
	


}
