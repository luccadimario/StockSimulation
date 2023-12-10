/*********************************************************
 * Filename: Order
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 
 * 
 * Purpose: 
 * Holds tempate for all orders created by agents.
 * 
 * Attributes:
 * 		-numShares: double
 * 		-price: double
 * 		-isSelling: boolean
 * 		-isSold: boolean
 * 		-agentArrID: int
 * 
 * Methods: 
 * 		+<<constructor>> Order(double, double, boolean, int)
 * 		+printString(): void
 * 		+toString(): String
 * 		
 * 
 *********************************************************/
public class Order {
	private double numShares, price;
	private boolean isSelling, isSold;
	private int agentArrID;
	public Order(double numShares, double price, boolean isSelling, int agentArrID) {
		this.numShares = numShares;
		this.price = price;
		this.isSelling = isSelling;
		this.agentArrID = agentArrID;
		isSold = false;
	}
	public void printString() {
		System.out.println("" + numShares + " " + price + " " + isSelling + " " + agentArrID + " " + isSold);
	}
	public String toString() {
		return(numShares + " " + price + " " + isSelling + " " + agentArrID + " " + isSold);
	}
	public double getShares() {
		return numShares;
	}
	public double getPrice() {
		return price;
	}
	public boolean getIsSelling() {
		return isSelling;
	}
	public boolean getIsSold() {
		return isSold;
	}
	public void setIsSold(boolean isSold) {
		this.isSold = isSold;
	}
	public int getAgentArrID() {
		return agentArrID;
	}
}
