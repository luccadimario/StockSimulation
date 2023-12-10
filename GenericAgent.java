/*********************************************************
 * Filename: GenericAgent
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 
 * 
 * Purpose: 
 * Holds the generic agent template
 * 
 * Attributes:
 * 		-buyPercent: double
 * 		-sellPercent: double
 * 		-money: double
 * 		-stock: double
 * 		-marketView: int
 * 
 * Methods: 
 * 		+<<constructor>> StaticBollingerAfent(double, double, double, double, double)
 * 		+runPurchaseSellCheck(double, int): Order[]
 * 		+isStatic(): boolean
 * 		+isBollinger(): boolean
 * 		+printType(): void
 * 		+printAgent(): void
 * 
 *********************************************************/
public class GenericAgent {
	protected double buyPercent, sellPercent, money, stock;
	protected int marketView;
	public GenericAgent(double buyPercent, double sellPercent, double startingMoney, double startingStock) {
		this.buyPercent = buyPercent;
		this.sellPercent = sellPercent;
		money = startingMoney;
		stock = startingStock;
			
	}
	public Order[] runPurchaseSellCheck(double stockPrice, int agentArrID) {
		double buyPrice = ((stockPrice + marketView) - (stockPrice * buyPercent));
		if(buyPrice < 1.5) {
			buyPrice = 1.25;
		}
		System.out.println("This is Agent " + (agentArrID + 1) + "'s buy price! " + buyPrice);
		double sellPrice = ((stockPrice + marketView) + (stockPrice * sellPercent));
		if(sellPrice < 1.5) {
			sellPrice = 1.25;
		}
		System.out.println("This is Agent " + (agentArrID + 1) + "'s sell price! " + sellPrice);
		double sharesToSell = 1;
		double sharesToBuy = 1;
		Order sellOrder = new Order(sharesToSell, sellPrice, true, agentArrID);
		Order buyOrder = new Order(sharesToBuy, buyPrice, false, agentArrID);
		return (new Order[] {sellOrder, buyOrder});
	}
	public double getMoney() {
		return money;
	}
	public double getStock() {
		return stock;
	}
	public void setMoney(double newMoney) {
		money = newMoney;
	}
	public void setStock(double newStock) {
		stock = newStock;
	}
	public boolean isStatic() {
		return true;
	}
	public boolean isBollinger() {
		return false;
	}
	public void printAgent() {
		System.out.println("Buy Percent: " + buyPercent + " Sell Percent: " + sellPercent + " Money: " + money + " Stock: " + stock);
	}
	public void printType() {
		System.out.println("This is a GenericAgent!");
	}
	public void setMarketView(int marketView) {
		this.marketView = marketView;
	}
	
}
