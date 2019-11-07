package cardealer.src.main.java.com.dealers.project.cardealer;

public class CarOffer {
	
	private int plateNum;  //unique key to identify the car
	private String buyer;  // name matches user name on user table, assume unique names
	private float offerPrice; // buyer's offer price
	private float downPmt;  // buy's down payment
	private int termFinance; // financing deal selected 1-2-3
	
	// if deal accepted then offerprice, downpmt, and termfinance populates the 
	// car details table
	
	public CarOffer() {
		this.plateNum = 0;
		this.buyer = " ";
		this.offerPrice = 0;
		this.downPmt = 0;
		this.termFinance = 0;
	}
	
	

	public CarOffer(int plateNum, String buyer, float offerPrice, float downPmt, int termFinance) {
		super();
		this.plateNum = plateNum;
		this.buyer = buyer;
		this.offerPrice = offerPrice;
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
	


	
	
}
