/*********************************************************
 * Filename: StaticAgent
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified:  11/17/23
 * 
 * Purpose: 
 * Agent type which calculates the buy and sell prices solely from hard-coded values
 * 
 * Attributes:
 *		-buyPercent: double
 * 		-sellPercent: double
 * 		-money: double
 * 		-stock: double
 * 		-marketView: int
 * 
 * Methods: 
 * 		+<<constructor>> StaticAgent(double, double, double, double)
 * 		+runPurchaseSellCheck(double, int): Order[]
 * 		+runPurchaseSellCheckWithoutMarketView(double, int): Order[]
 * 		+generateRandomMarketView(double): int
 * 		+isStatic(): boolean
 * 		+isBollinger(): boolean
 * 		+printType(): void
 * 		+printAgent(): void
 * 		
 * 
 *********************************************************/

public class StaticAgent extends GenericAgent{
	private boolean marketViewInit;
	public StaticAgent(double buyPercent, double sellPercent, double startingMoney, double startingStock) {
		super(buyPercent, sellPercent, startingMoney, startingStock);
	}
	@Override
	public Order[] runPurchaseSellCheck(double stockPrice, int agentArrID) {
		if(!marketViewInit) {
			marketView = generateRandomMarketView(stockPrice);
			marketViewInit = true;
		}
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public Order[] runPurchaseSellCheckWithoutMarketView(double stockPrice, int agentArrID) {
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public int generateRandomMarketView(double stockPrice) {
		int randRaiseOrLower = ((int)(Math.random()*(stockPrice/2) + 1));
		int posOrNeg = ((int)(Math.random() * 2));
		if(posOrNeg == 0) {
			randRaiseOrLower = (-randRaiseOrLower);
		}
		return randRaiseOrLower;
	}
	@Override
	public boolean isStatic() {
		return true;
	}
	@Override
	public boolean isBollinger() {
		return false;
	}
	public void printAgent() {
		System.out.println("StaticAgent: Buy Percent: " + buyPercent + " Sell Percent: " + sellPercent + " Money: " + money + " Stock: " + stock);
	}
	@Override
	public void printType() {
		System.out.println("This is a StaticAgent!");
	}
	
}
