/*********************************************************
 * Filename: SSMain
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 11/17/23
 * 
 * Purpose: 
 * Main for the stock market simulation
 * 
 * Attributes:
 * 
 * Methods: 
 * 		
 * 
 *********************************************************/
public class SSMain {
	public static void main(String[] args) {
		Market market = new Market();
		market.userSimulationCreation();
		
		
		/*Can run a manual simulation by using market constructor Market(int stockPrice, int numberOfAgents) price must be less than 500 due to the agent creation method only giving them $500
		 *and then running createAgentArray(int numberOfAgents)
		 *then run startSimulation(int numberOfDays)*/

		
	}
}
