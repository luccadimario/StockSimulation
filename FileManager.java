/*********************************************************
 * Filename: FileManager
 * Author: Carmen DiMario
 * Created: 11/7/23
 * Modified: 
 * 
 * Purpose: 
 * Controls the reading from and writing to the file that holds the transactions
 * 
 * Attributes:
 * 		-filename: String
 * 
 * Methods: 
 * 		+<<constructor>> FileManager(String)
 * 		+writeTransactions(String): void
 * 		+getTransactionArray(): String[][]
 * 		
 * 
 *********************************************************/
import java.io.*;
import java.util.ArrayList;

public class FileManager {
	String filename;
	//to be implemented.
	public FileManager(String filename) {
		this.filename = filename;
		
	}
	
	public void writeTransactions(String transaction) {
		try {
			File file = new File(filename);
			if(!file.exists()){
	    	 	file.createNewFile();
	    	  }
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(transaction);
			bw.newLine();
			
			bw.close();
			fw.close();
		} catch (Exception e) {
			System.out.println("Exception occured in writeTransactions() method. ");
			e.printStackTrace();
		}
	}
	public String[][] getTransactionArray() {
		ArrayList<String> fileData = new ArrayList<String>();
		try {
			File file = new File(filename);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String row;
			
			while ( (row = br.readLine()) != null) {
				fileData.add(row);
			}
			
			br.close();
			fr.close();
			String[][] transactionArray = new String[fileData.size()][6];
			for(int i = 0; i < fileData.size(); i++) {
				transactionArray[i] = fileData.get(i).split(" ");
			}
			return transactionArray;
		} catch (Exception e) {
			System.out.println("Exception occured in getTransactionArray() method. ");
			e.printStackTrace();
			return null;
		}
	}
}
