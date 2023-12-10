/*********************************************************
 * Filename: Market
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 11/17/23
 * 
 * Purpose: 
 * Market object for simulation, contains agents and the arrays that hold the orders
 * 
 * Attributes:
 *		-stockPrice: double
 *		-upperBollingerCalculation: double
 *		-lowerBollingerCalculation: double
 *		-stockClosingHistoryL ArrayList<Double>
 *		-sellingArray: ArrayList<Order>
 *		-buyingArray: ArrayList<Order>
 *		-agentArray: GenericAgent[]
 *		-fm: FileManager
 *		-input: Scanner
 * 
 * Methods: 
 * 		+<<constructor>> Market(double, int)
 * 		+<<constructor>> Market()
 * 		+initStockClosingHistory(): void
 *		+userSimulationCreation(): void
 *		+createStaticAgents(int): int
 *		+createUpdatingAgents(int): int
 *		+createStaticBollingerAgents(int): int
 *		+askDoubleQuestion(String): double
 *		+askIntQuestion(String): int
 *		+setTestBollinger(int): void
 *		+addStockClosingValue(double): void
 *		+printStockClosingHistory(): void
 *		+printBollinger(): void
 *		+writeTestTransactions(int): void
 *		+initAgentArrayUpdating(int): void
 *		+runUpdatingAgentTest(): void
 *		+createAgentArray(int): void
 *		+createAgentArrayStatic(int): void
 *		+createTestDiversityArray(): void
 *		+createAgentArrayStaticBollinger(int): void
 *		+parseAndRunPurchaseSellCheck(): void
 *		+printAgents(): void
 *		+printSellingArray(): void
 *		+printBuyingArray(): void
 *		+startSimulation(int): void
 *		+testAppendBuyingSellingArray(): void
 *		+cycleLoopTestWithoutMarketView(int): void
 *		+cycleLoop(int): void
 *		+castAndRunStatic(int): Order[]
 *		+castAndRunStaticWithoutMarketView(int): Order[]
 *		+castAndRunStaticBollinger(int): Order[]
 *		+castAndRunStaticBollingerWithoutMarketView(int): Order[]
 *		+castAndRunUpdatingAgent(int): Order[]
 *		+castAndRunUpdatingAgentWithoutMarketView(int): Order[]
 *		+checkBuyingArray(Order, int): void
 *		+checkSellingArray(Order, int): void
 *		+removeSoldBoughtOrders(): void
 *		+calculateBollinger(): void
 *		+addToSMA(double, double, double, int): void
 * 		
 * 
 *********************************************************/
import java.util.*;

public class Market {
	private double upperBollingerCalculation, lowerBollingerCalculation, stockPrice;
	private ArrayList<Double> stockClosingHistory = new ArrayList<Double>();
	private ArrayList<Order> sellingArray = new ArrayList<Order>();
	private ArrayList<Order> buyingArray = new ArrayList<Order>();
	private GenericAgent[] agentArray;
	private String stockName;
	private FileManager fm = new FileManager("transactions.txt");
	private Scanner input = new Scanner(System.in);
	
	public Market(double stockPrice, int numberOfAgents) {
		this.stockPrice = stockPrice;
		agentArray = new GenericAgent[numberOfAgents];
		
	}
	public Market() {
		//No Values, for User Simulation Creation 
	}
	public void initStockClosingHistory() {
		stockClosingHistory.add(9.0);
		stockClosingHistory.add(10.0);
		stockClosingHistory.add(11.0);
		stockClosingHistory.add(13.0);
		stockClosingHistory.add(14.0);
		stockClosingHistory.add(15.0);
		stockClosingHistory.add(16.0);
		stockClosingHistory.add(17.0);
		stockClosingHistory.add(18.0);
		stockClosingHistory.add(19.0);
		stockClosingHistory.add(20.0);
	}
	public void userSimulationCreation() {
		int numAgents;
		System.out.println("*DISCLAIMER PLEASE READ*\nThe recommended values for buy percent, sell percent, and percent updating bound of agents are between .1 and .5.\nExtreme or extremely diverse values could cause situations where no agents want to trade with eachother and the stock price does not move.\nMake sure to give agents enough money to trade.\nRead prompts carefully! Happy Trading!\n");
		System.out.print("Welcome to the Agent Stock Simulation Creation. \nPlease enter the stock name: ");
		stockName = input.nextLine();
		stockPrice = askDoubleQuestion("Please enter a Double starting price for " + stockName + " ex. 10.0: ");
		System.out.println("Starting " + stockName + " at a price of $" + stockPrice + ". Make sure agents have enough money to trade at this price!");
		agentArray = new GenericAgent[askIntQuestion("Please enter an Integer for the total number of agents trading in the market: ")];
		int agentsCreated = 0;
		boolean rotatedOnce = false;
		while(agentsCreated < agentArray.length) {
			if(rotatedOnce) {
				System.out.println("Not enough agents! Looping to select more!");
			}
			if(agentsCreated < agentArray.length) {
				numAgents = askIntQuestion("Please enter the number of different types of Static Agents trading in the simulation: ");
				for(int j = 0; j < numAgents; j++) {
					if(agentsCreated < agentArray.length) {
						agentsCreated = createStaticAgents(agentsCreated, j);
					}
				}
			}
			if(agentsCreated < agentArray.length) {
				numAgents = askIntQuestion("Please enter the number of different types of Static Bollinger Agents trading in the simulation: "); 
				for(int j = 0; j < numAgents; j++) {
					if(agentsCreated < agentArray.length) {
						agentsCreated = createStaticBollingerAgents(agentsCreated, j);
					}
				}
			}
			if(agentsCreated < agentArray.length) {
				numAgents = askIntQuestion("Please enter the number of different types of Updating Agents trading in the simulation: "); 
				for(int j = 0; j < numAgents; j++) {
					if(agentsCreated < agentArray.length) {
						agentsCreated = createUpdatingAgents(agentsCreated, j);
					}
				}
			}
			rotatedOnce = true;
			
		}
		printAgents();
		int numDays = askIntQuestion("Please enter the number of days the simulation should run for: ");
		startSimulation(numDays);
	}
	public int createStaticAgents(int agentsCreated, int cycle) {
		double buyPercent = 0.0;
		double sellPercent = 0.0;
		double startingMoney = 0.0;
		double startingStock = 0.0;
		int numAgents = 0;
		do {
			numAgents = askIntQuestion("Please enter the amount of the " + (cycle + 1) + "(nd/st) type of Static Agent trading in the simulation: ");
			if(numAgents + agentsCreated > agentArray.length) {
				System.out.println("Too many agents! You only have " + (agentArray.length - agentsCreated) + " spots left.");
			}
		}while(numAgents + agentsCreated > agentArray.length);
		
		buyPercent = askDoubleQuestion("Please enter the Double for the buyPercent of this agent (Recommended non-zero under 1): ");
		sellPercent = askDoubleQuestion("Please enter the Double for the sellPercent of this agent (Recommended non-zero under 1): ");
		startingMoney = askDoubleQuestion("Please enter the Double for the starting money of this agent: ");
		startingStock = askDoubleQuestion("Please enter the Double for the starting stock of this agent: ");
		for(int i = 0; i < numAgents; i++) { 
			agentArray[agentsCreated] = new StaticAgent(buyPercent, sellPercent, startingMoney, startingStock);
			agentsCreated++;
		}
		return agentsCreated;
	}
	public int createUpdatingAgents(int agentsCreated, int cycle) {
		double buyPercent = 0.0;
		double sellPercent = 0.0;
		double startingMoney = 0.0;
		double startingStock = 0.0;
		int numAgents = 0;
		do {
			numAgents = askIntQuestion("Please enter the amount of the " + (cycle + 1) + "(nd/st) type of Updating Agent trading in the simulation: ");
			if(numAgents + agentsCreated > agentArray.length) {
				System.out.println("Too many agents! You only have " + (agentArray.length - agentsCreated) + " spots left.");
			}
		}while(numAgents + agentsCreated > agentArray.length);
		
		buyPercent = askDoubleQuestion("Please enter the Double for the buyPercent of this agent (Recommended non-zero under 1): ");
		sellPercent = askDoubleQuestion("Please enter the Double for the sellPercent of this agent (Recommended non-zero under 1): ");
		startingMoney = askDoubleQuestion("Please enter the Double for the starting money of this agent: ");
		startingStock = askDoubleQuestion("Please enter the Double for the starting stock of this agent: ");
		for(int i = 0; i < numAgents; i++) {
			agentArray[agentsCreated] = new UpdatingAgent(buyPercent, sellPercent, startingMoney, startingStock);
			agentsCreated++;
		}
		return agentsCreated;
	}
	public int createStaticBollingerAgents(int agentsCreated, int cycle) {
		double buyPercent = 0.0;
		double sellPercent = 0.0;
		double startingMoney = 0.0;
		double startingStock = 0.0;
		double percentUpdatingBound = 0.0;
		int numAgents = 0;
		do {
			numAgents = askIntQuestion("Please enter the amount of the " + (cycle + 1) + "(nd/st) type of Static Bollinger Agent trading in the simulation: ");
			if(numAgents + agentsCreated > agentArray.length) {
				System.out.println("Too many agents! You only have " + (agentArray.length - agentsCreated) + " spots left.");
			}
		}while(numAgents + agentsCreated > agentArray.length);
		buyPercent = askDoubleQuestion("Please enter the Double for the buyPercent of this agent (Recommended non-zero under 1): ");
		sellPercent = askDoubleQuestion("Please enter the Double for the sellPercent of this agent (Recommended non-zero under 1): ");
		startingMoney = askDoubleQuestion("Please enter the Double for the starting money of this agent: ");
		startingStock = askDoubleQuestion("Please enter the Double for the starting stock of this agent: ");
		percentUpdatingBound = askDoubleQuestion("Please enter the Double for the percent updating bound of this agent *Decides how much or how little Bollinger bands will affect the price* (Recommended non-zero under 1): ");
		for(int i = 0; i < numAgents; i++) {
			agentArray[agentsCreated] = new StaticBollingerAgent(buyPercent, sellPercent, startingMoney, startingStock, percentUpdatingBound);
			agentsCreated++;
		}
		return agentsCreated;
	}
	
	public double askDoubleQuestion(String question) {
		double answer = 0.0;
		boolean properInput = false;
		do {
			System.out.print(question);
			try {
				answer = input.nextDouble(); 
				if(answer < 0) {
					throw new Exception("Number can't be negative");
				}
				properInput = true;
			}
			catch(Exception e) {
				System.out.println("Please enter a Double ex. 10.0");
				input.nextLine();
				properInput = false;
			}
		} while(!properInput); 
		return answer;
	}
	public int askIntQuestion(String question) {
		int answer = 0;
		boolean properInput = false;
		do {
			System.out.print(question);
			try {
				answer = input.nextInt(); 
				if(answer < 0) {
					throw new Exception("Number can't be negative");
				}
				properInput = true;
			}
			catch(Exception e) {
				System.out.println("Please enter an Integer ex. 10");
				input.nextLine();
				properInput = false;
			}
		} while(!properInput); 
		return answer;
	}
	public void setTestBollinger(int testStockPrice) {
		createAgentArrayStaticBollinger(2);
		printAgents();
		upperBollingerCalculation = 20;
		lowerBollingerCalculation = 10;
		stockPrice = testStockPrice;
		System.out.println("Upper Bollinger Band set to " + upperBollingerCalculation + ", Lower Bollinger Band set to " + lowerBollingerCalculation + ". Stock Price set to " + stockPrice + ".");
		parseAndRunPurchaseSellCheck();
		printAgents();
	}
	public void addStockClosingValue(double value) {
		stockClosingHistory.add(value);
	}
	public void printStockClosingHistory() {
		System.out.println("Stock closing history: ");
		for(int i = 0; i < stockClosingHistory.size(); i++) {
			System.out.print("Closing price for day " + (i + 1) + ": " + stockClosingHistory.get(i) + " ");
		}
		System.out.println("");
	}
	public void printBollinger() {
		calculateBollinger();
		System.out.println("Upper bollinger calc: " + upperBollingerCalculation);
		System.out.println("Lower bollinger calc: " + lowerBollingerCalculation);
	}
	public void writeTestTransactions(int numFromSellingArray) {
		try {
			for(int i = 0; i < numFromSellingArray; i++) {
				System.out.println("printing " + sellingArray.get(i).toString());
				fm.writeTransactions(sellingArray.get(i).toString());
			}
				
		}
		catch(Exception e) {
			System.out.println("Index out of bounds for sellingArray, printed entire array in file");
		}
		
	}
	public void initAgentArrayUpdating(int numberOfAgents) {
		for(int i = 0; i < numberOfAgents; i++) {
			agentArray[i] = new UpdatingAgent(.25, .25, 500, 15);
		}
	}
	public void runUpdatingAgentTest() {
		for(int i = 0; i < agentArray.length; i++) {
			UpdatingAgent uAgent = (UpdatingAgent)agentArray[i];
			uAgent.testMarketView(stockPrice, fm);
		}
	}
	public void createAgentArray(int numberOfAgents) {
		for(int i = 0; i < numberOfAgents/2; i++) {
			agentArray[i] = new StaticAgent(.25, .25, 500, 15);
		}
		for(int i = numberOfAgents/2; i < numberOfAgents; i++) {
			agentArray[i] = new StaticBollingerAgent(.25, .25, 500, 15,.3);
		}
	}
	public void createAgentArrayStatic(int numberOfAgents) {
		for(int i = 0; i < numberOfAgents/2; i++) {
			agentArray[i] = new StaticAgent(.25, .25, 500, 15);
		}
		for(int i = numberOfAgents/2; i < numberOfAgents; i++) {
			agentArray[i] = new UpdatingAgent(.25, .25, 500, 15);
		}
	}
	public void createTestDiversityArray() {
		int i = 0;
		int count = 0;
		for(double j = .10; j < 2; j += .05) {
			i = 0;
			while(i < 10) {
				System.out.println(i + " " + j);
				agentArray[count] = new StaticAgent(j, j, 1000, 15);
				agentArray[count + 1] = new StaticBollingerAgent(j,j,1000,15, j + .05);
				count++;
				count++;
				i++;
			}
		}
		
	}
	public void createAgentArrayStaticBollinger(int numberOfAgents) {
		for(int i = 0; i < numberOfAgents; i++) {
			agentArray[i] = new StaticBollingerAgent(.25, .25, 500, 15,.3);
		}
	}
	public void parseAndRunPurchaseSellCheck() {
		for(int i = 0; i < agentArray.length; i++) {
			if(agentArray[i].isStatic() && !agentArray[i].isBollinger()) {
				agentArray[i].runPurchaseSellCheck(stockPrice, i);
			}
			else {
				StaticBollingerAgent bAgent = (StaticBollingerAgent)agentArray[i]; 
				bAgent.runPurchaseSellCheck(stockPrice, upperBollingerCalculation, lowerBollingerCalculation, i);
			}
		}
	}
	public void printAgents() {
		for(GenericAgent x : agentArray) {
			x.printAgent();
		}
	}
	public void printSellingArray() {
		System.out.println("sellingArray: ");
		for(Order x: sellingArray){
			x.printString();
		}
	}
	public void printBuyingArray() {
		System.out.println("buyingArray: ");
		for(Order x: buyingArray){
			x.printString();
		}
	}
	public void startSimulation(int days) {
		stockClosingHistory.add(stockPrice);
		for(int i = 0; i < days; i++) {
			calculateBollinger();
			for(int j = 0; j < 5; j++) {
				System.out.println("Price: " + stockPrice);
				System.out.println("Cycle " + (j + 1));
				for(int k = 0; k < agentArray.length; k++) {
					cycleLoop(k);
				}
				removeSoldBoughtOrders();
			}
			System.out.println("Closing stock price for day " + (i + 1) + ": " + stockPrice);
			stockClosingHistory.add(stockPrice);
		}
		printStockClosingHistory();
	}
	public void testAppendBuyingSellingArray() {
		calculateBollinger();
		for(int k = 0; k < agentArray.length; k++) {
			cycleLoopTestWithoutMarketView(k);
		}
		
	}
	public void cycleLoopTestWithoutMarketView(int k) {
		System.out.println("Agent " + (k + 1));
		agentArray[k].printType();
		Order[] agentOrders;
		System.out.println("Agent is Static: " + agentArray[k].isStatic() + "| Agent is Bollinger: " + agentArray[k].isBollinger());
		agentArray[k].setMarketView(0);
		if(agentArray[k].isStatic() && !agentArray[k].isBollinger()) {
			agentOrders = castAndRunStaticWithoutMarketView(k);
		}
		else if(agentArray[k].isStatic() && agentArray[k].isBollinger()){
			agentOrders =  castAndRunStaticBollingerWithoutMarketView(k);
		}
		else {
			agentOrders = castAndRunUpdatingAgentWithoutMarketView(k);
		}
		for(int orderNum = 0; orderNum < agentOrders.length; orderNum++) {
			Order order = agentOrders[orderNum];
			if(order.getIsSelling()) {
				sellingArray.add(order);
			}
			else {
				buyingArray.add(order);
			}
		}
	}
	public void cycleLoop(int k) {
		System.out.println("Agent " + (k + 1));
		agentArray[k].printType();
		Order[] agentOrders;
		System.out.println("Agent is Static: " + agentArray[k].isStatic() + "| Agent is Bollinger: " + agentArray[k].isBollinger());
		if(agentArray[k].isStatic() && !agentArray[k].isBollinger()) {
			agentOrders = castAndRunStatic(k);
		}
		else if(agentArray[k].isStatic() && agentArray[k].isBollinger()){
			agentOrders =  castAndRunStaticBollinger(k);
		}
		else {
			agentOrders = castAndRunUpdatingAgent(k);
		}
		
		for(int orderNum = 0; orderNum < agentOrders.length; orderNum++) {
			Order order = agentOrders[orderNum];
			if(order.getIsSelling()) {
				checkBuyingArray(order, k);
			}
			else {
				checkSellingArray(order, k);
			}
		}
	}
	public Order[] castAndRunStatic(int k) {
		StaticAgent sAgent = (StaticAgent)agentArray[k];
		return(sAgent.runPurchaseSellCheck(stockPrice, k));
	}
	public Order[] castAndRunStaticWithoutMarketView(int k) {
		StaticAgent sAgent = (StaticAgent)agentArray[k];
		return(sAgent.runPurchaseSellCheckWithoutMarketView(stockPrice, k));
	}
	public Order[] castAndRunStaticBollinger(int k) {
		StaticBollingerAgent bAgent = (StaticBollingerAgent)agentArray[k]; 
		return(bAgent.runPurchaseSellCheck(stockPrice, upperBollingerCalculation, lowerBollingerCalculation, k));
	}
	public Order[] castAndRunStaticBollingerWithoutMarketView(int k) {
		StaticBollingerAgent bAgent = (StaticBollingerAgent)agentArray[k]; 
		return(bAgent.runPurchaseSellCheckWithoutMarketView(stockPrice, upperBollingerCalculation, lowerBollingerCalculation, k));
	}
	public Order[] castAndRunUpdatingAgent(int k) {
		UpdatingAgent uAgent = (UpdatingAgent)agentArray[k];
		return(uAgent.runPurchaseSellCheck(stockPrice, k, fm));
	}
	public Order[] castAndRunUpdatingAgentWithoutMarketView(int k) {
		UpdatingAgent uAgent = (UpdatingAgent)agentArray[k];
		return(uAgent.runPurchaseSellCheckWithoutMarketView(stockPrice, k, fm));
	}
	public void checkBuyingArray(Order order, int agentArrID) {
		boolean isFound = false;
		System.out.println("Is selling!");
		for(int l = 0; l < buyingArray.size(); l++) {
			if(buyingArray.get(l).getPrice() >= order.getPrice() && isFound == false && buyingArray.get(l).getIsSold() == false && buyingArray.get(l).getAgentArrID() != agentArrID) {
				if((agentArray[agentArrID].getStock()) >= 1 && (agentArray[buyingArray.get(l).getAgentArrID()].getMoney() >= order.getPrice() * order.getShares())) {
					System.out.println("Matching Order Found!");
					agentArray[agentArrID].setMoney(agentArray[agentArrID].getMoney() + order.getPrice() * order.getShares());
					System.out.println(agentArray[agentArrID].getMoney());
					agentArray[agentArrID].setStock(agentArray[agentArrID].getStock() - order.getShares());
					agentArray[buyingArray.get(l).getAgentArrID()].setMoney(agentArray[agentArrID].getMoney() - order.getPrice() * order.getShares());
					agentArray[buyingArray.get(l).getAgentArrID()].setStock(agentArray[agentArrID].getStock() + order.getShares());
					buyingArray.get(l).setIsSold(true);
					stockPrice = order.getPrice();
					System.out.println("This is the order price " + order.getPrice());
					fm.writeTransactions(buyingArray.get(l).toString() + " " + (order.getPrice()*order.getShares()));
					isFound = true;
				}
			}
		}
		if(!isFound) {
			System.out.println("Agent " + (agentArrID + 1) + " adding order.");
			sellingArray.add(order);
		}
	}
	public void checkSellingArray(Order order, int agentArrID) {
		boolean isFound = false;
		System.out.println("is buying!");
		for(int l = 0; l < sellingArray.size(); l++) {
			if(sellingArray.get(l).getPrice() <= order.getPrice() && isFound == false && sellingArray.get(l).getIsSold() == false && sellingArray.get(l).getAgentArrID() != agentArrID) {
				if((agentArray[agentArrID].getMoney() >= order.getPrice() * order.getShares()) && (agentArray[sellingArray.get(l).getAgentArrID()].getStock() >= 1)) {
					System.out.println("Matching Order Found!");
					agentArray[agentArrID].setMoney(agentArray[agentArrID].getMoney() - order.getPrice() * order.getShares());
					agentArray[agentArrID].setStock(agentArray[agentArrID].getStock() + order.getShares());
					agentArray[sellingArray.get(l).getAgentArrID()].setMoney(agentArray[agentArrID].getMoney() + order.getPrice() * order.getShares());
					agentArray[sellingArray.get(l).getAgentArrID()].setStock(agentArray[agentArrID].getStock() - order.getShares());
					sellingArray.get(l).setIsSold(true);
					stockPrice = order.getPrice();
					System.out.println("This is the order price " + order.getPrice());
					fm.writeTransactions(sellingArray.get(l).toString() + " " + (order.getPrice()*order.getShares()));
					isFound = true;
				}
			}
		}
		if(!isFound) {
			System.out.println("Agent " + (agentArrID + 1) + " adding order.");
			buyingArray.add(order);
		}
	}
	public void removeSoldBoughtOrders() {
		for(int m = 0; m < sellingArray.size(); m ++) {
			if(sellingArray.get(m).getIsSold() == true) {
				sellingArray.remove(m);
				m--;
			}
		}
		for(int m = 0; m < buyingArray.size(); m ++) {
			if(buyingArray.get(m).getIsSold() == true) {
				buyingArray.remove(m);
				m--;
			}
		}
	}
	public void calculateBollinger() {
		double SMA = 0.0;
		double varianceSquaredAverage = 0.0;
		double standardDeviation = 0.0;
		int bollingerCalculationSize, averageDivideBy = 0;
		boolean overTwenty, zero = false;
		if(stockClosingHistory.size() > 20) {
			bollingerCalculationSize = 20;
			overTwenty = true;
			averageDivideBy = 20;
		}
		else if(stockClosingHistory.size() == 0) {
			bollingerCalculationSize = 0;
			overTwenty = false;
			zero = true;
		}
		else {
			bollingerCalculationSize = stockClosingHistory.size();
			averageDivideBy = stockClosingHistory.size() - 1;
			overTwenty = false;
		}
		if(!zero) {
			for(int i = stockClosingHistory.size() - 1; i >= stockClosingHistory.size() - bollingerCalculationSize; i-- ) {
				SMA += stockClosingHistory.get(i);
				if(overTwenty) {
					varianceSquaredAverage += Math.pow((stockClosingHistory.get(i) - stockClosingHistory.get(i-1)),2);
				}
				else {
					if(i != (stockClosingHistory.size()-bollingerCalculationSize)) {//else if
						varianceSquaredAverage += Math.pow((stockClosingHistory.get(i) - stockClosingHistory.get(i-1)),2);
					}
					
				}
			}		
		}
		addToSMA(SMA, varianceSquaredAverage, standardDeviation, averageDivideBy);
	}
	public void addToSMA(double SMA, double varianceSquaredAverage, double standardDeviation, int averageDivideBy) {
		SMA = SMA/(averageDivideBy + 1);
		System.out.println(SMA + " " + averageDivideBy);
		varianceSquaredAverage = (varianceSquaredAverage/averageDivideBy);
		standardDeviation = Math.sqrt(varianceSquaredAverage);
		if(Double.isNaN(standardDeviation)) {
			standardDeviation = 1;
		}
		upperBollingerCalculation = (SMA + (standardDeviation*2));
		lowerBollingerCalculation = (SMA - (standardDeviation*2));
	}
}
