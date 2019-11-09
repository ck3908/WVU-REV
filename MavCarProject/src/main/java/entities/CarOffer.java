package entities;

public class CarOffer {

	private int plateNum;  //unique key to identify the car
	private String buyer;  // name matches user name on user table, assume unique names
	private float offerPrice; // buyer's offer price
	private String status;  //pending rejected or approved
	private float downPmt;  // buy's down payment
	private int termFinance; // financing deal selected 1-2-3
	
	public CarOffer() {
		super();
		this.plateNum = 0;
		this.buyer = " ";
		this.offerPrice = 0;
		this.status = " ";
		this.downPmt = 0;
		this.termFinance = 0;
	}
	
	public CarOffer(int plateNum, String buyer, float offerPrice, String status, float downPmt, int termFinance) {
		super();
		this.plateNum = plateNum;
		this.buyer = buyer;
		this.offerPrice = offerPrice;
		this.status = status;
		this.downPmt = downPmt;
		this.termFinance = termFinance;
	}
	public int getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(int plateNum) {
		this.plateNum = plateNum;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public float getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(float offerPrice) {
		this.offerPrice = offerPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getDownPmt() {
		return downPmt;
	}
	public void setDownPmt(float downPmt) {
		this.downPmt = downPmt;
	}
	public int getTermFinance() {
		return termFinance;
	}
	public void setTermFinance(int termFinance) {
		this.termFinance = termFinance;
	}
	
	// if deal accepted then offerprice, downpmt, and termfinance populates the 
	// car details table
	
	
	
}
