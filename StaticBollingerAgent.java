/*********************************************************
 * Filename: StaticBollingerAgent
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified:  11/17/23
 * 
 * Purpose: 
 * Agent type which calculates the bollinger bands and trades in a stock market accordingly
 * 
 * Attributes:
 * 		-buyPercent: double
 * 		-sellPercent: double
 * 		-money: double
 * 		-stock: double
 * 		-percentUpdatingBound: double
 *   	-marketView: int
 * 
 * Methods: 
 * 		+<<constructor>> StaticBollingerAgent(double, double, double, double, double)
 * 		+runPurchaseSellCheck(double, ArrayList<Double>, int): Order[]
 * 		+runPurchaseSellCheckWithoutMarketView(double, double, double, int): Order[]
 * 		+generateRandomMarket(): int
 * 		+updateListAndSellPercent(Double, double, double): void
 * 		+bollingerBandDistance(double, double): void
 * 		+isStatic(): boolean
 * 		+isBollinger(): boolean
 *  	+printType(): void
 *  	+printAgent(): void
 * 		
 * 
 *********************************************************/

public class StaticBollingerAgent extends GenericAgent{
	private double percentUpdatingBound;
	private boolean marketViewInit = false;
	public StaticBollingerAgent(double buyPercent, double sellPercent, double startingMoney, double startingStock, double percentUpdatingBound) {
		super(buyPercent, sellPercent, startingMoney, startingStock);
		this.percentUpdatingBound = percentUpdatingBound;
	}
	
	public Order[] runPurchaseSellCheck(double stockPrice, double upperBollingerCalculation, double lowerBollingerCalculation, int agentArrID) {
		if(!marketViewInit) {
			marketView = generateRandomMarketView(stockPrice);
			marketViewInit = true;
		}
		updateListAndSellPercent(upperBollingerCalculation, lowerBollingerCalculation, stockPrice);
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public Order[] runPurchaseSellCheckWithoutMarketView(double stockPrice, double upperBollingerCalculation, double lowerBollingerCalculation, int agentArrID) {
		if(!Double.isNaN(upperBollingerCalculation) && !Double.isNaN(lowerBollingerCalculation)) {
			updateListAndSellPercent(upperBollingerCalculation, lowerBollingerCalculation, stockPrice);
		}
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public void updateListAndSellPercent(double upperBollingerCalculation, double lowerBollingerCalculation, double stockPrice) {
		double distanceToUpper = (upperBollingerCalculation - stockPrice);
		double buyPercentChange = (distanceToUpper/((upperBollingerCalculation - lowerBollingerCalculation)/2))*percentUpdatingBound;
		double distanceToLower = (stockPrice - lowerBollingerCalculation);
		double sellPercentChange = (distanceToLower/((upperBollingerCalculation - lowerBollingerCalculation)/2))*percentUpdatingBound;
		if(distanceToUpper <= 0) {
			buyPercent = (buyPercent * (.5));
			sellPercent = (sellPercent * (1));
		}
		else if(distanceToLower < 0) {
			buyPercent = (buyPercent * (1));
			sellPercent = (sellPercent * (.5));
		}
		else if(distanceToUpper < distanceToLower) {
			if(buyPercentChange > .5) {
				buyPercentChange = .5;
			}
			if(sellPercentChange > .5) {
				sellPercentChange = .5;
			}
			buyPercent = (buyPercent * (1-buyPercentChange));
			sellPercent = (sellPercent * (1+sellPercentChange));
		}
		else {
			if(buyPercentChange > .5) {
				buyPercentChange = .5;
			}
			if(sellPercentChange > .5) {
				sellPercentChange = .5;
			}
			buyPercent = (buyPercent * (1+buyPercentChange));
			sellPercent = (sellPercent * (1-sellPercentChange));
		}
	}
	public int generateRandomMarketView(double stockPrice) {
		int randRaiseOrLower = ((int)(Math.random()*(stockPrice/2) + 1));
		int posOrNeg = ((int)(Math.random() * 2));
		if(posOrNeg == 0) {
			randRaiseOrLower = (-randRaiseOrLower);
		}
		return randRaiseOrLower;
	}
	public double bollingerBandDistance(double upperBollingerCalculation, double lowerBollingerCalculation) {
		return ((upperBollingerCalculation - lowerBollingerCalculation)/2);
	}
	@Override
	public boolean isStatic() {
		return true;
	}
	@Override
	public boolean isBollinger() {
		return true;
	}
	@Override
	public void printType() {
		System.out.println("This is a StaticBollingerAgent!");
	}
	public void printAgent() {
		System.out.println("StaticBollingerAgent: Buy Percent: " + buyPercent + " Sell Percent: " + sellPercent + " Money: " + money + " Stock: " + stock + " percentUpdatingBound: " + percentUpdatingBound);
	}
}
