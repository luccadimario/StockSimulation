/*********************************************************
 * Filename: UpdatingAgent
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 
 * 
 * Purpose: 
 * Agent type which updates its listPercent and sellPercent values from the recorded file
 * 
 * Attributes:
 * 		-buyPercent: double
 * 		-sellPercent: double
 * 		-money: double
 * 		-stock: double
 * 		-marketView: int
 * 
 * Methods: 
 * 		+<<constructor>> UpdatingAgent(double, double, double, double)
 * 		+runPurchaseSellCheck(double, int, FileManager): Order[]
 *		+runPurchaseSellCheckWithoutMarketView(double, int, FileManager): Order[]
 *		+generateMarketView(double, FileManager): int
 *		+testMarketView(double, FileManager): void
 *		+isStatic(): boolean
 *		+isBollinger(): boolean
 *		+printAgent(): void
 * 		
 * 
 *********************************************************/
public class UpdatingAgent extends StaticAgent{
	public UpdatingAgent(double buyPercent, double sellPercent, double startingMoney, double startingStock) {
		super(buyPercent, sellPercent, startingMoney, startingStock);
	}
	public Order[] runPurchaseSellCheck(double stockPrice, int agentArrID, FileManager fm) {
		marketView = generateMarketView(stockPrice, fm);
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public Order[] runPurchaseSellCheckWithoutMarketView(double stockPrice, int agentArrID, FileManager fm) {
		return(super.runPurchaseSellCheck(stockPrice, agentArrID));
	}
	public int generateMarketView(double stockPrice, FileManager fm) {
		String[][] transactionArray = fm.getTransactionArray();
		double averageLast100 = 0.0;
		int divideBy = 100;
		if(transactionArray.length >= 100) {
			for(int i = (transactionArray.length - 100); i < transactionArray.length; i++) {
				averageLast100 += Double.parseDouble(transactionArray[i][1]);
			}
			averageLast100 = averageLast100/divideBy;
		}
		else {
			divideBy = transactionArray.length;
			for(int i = 0; i < transactionArray.length; i++) {
				averageLast100 += Double.parseDouble(transactionArray[i][1]);
			}
			averageLast100 = averageLast100/divideBy;
		}
		int randRaiseOrLower = ((int)(Math.random()*(stockPrice/2) + 1));
		if((averageLast100 - stockPrice) < 1) {
			randRaiseOrLower = -randRaiseOrLower;
		}
		return randRaiseOrLower;
	}
	//same as above, used for testing
	public void testMarketView(double stockPrice, FileManager fm) {
		String[][] transactionArray = fm.getTransactionArray();
		double averageLast100 = 0.0;
		int divideBy = 100;
		if(transactionArray.length >= 100) {
			for(int i = (transactionArray.length - 100); i < transactionArray.length; i++) {
				averageLast100 += Double.parseDouble(transactionArray[i][1]);
			}
			averageLast100 = averageLast100/divideBy;
		}
		else {
			divideBy = transactionArray.length;
			for(int i = 0; i < transactionArray.length; i++) {
				averageLast100 += Double.parseDouble(transactionArray[i][1]);
			}
			averageLast100 = averageLast100/divideBy;
		}
		System.out.println(averageLast100);
	}
	@Override
	public boolean isStatic() {
		return false;
	}
	@Override
	public boolean isBollinger() {
		return false;
	}
	public void printAgent() {
		System.out.println("UpdatingAgent: Buy Percent: " + buyPercent + " Sell Percent: " + sellPercent + " Money: " + money + " Stock: " + stock);
	}
}
