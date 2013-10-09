/* Release Notes:
 * XML File was modified, we added 'name' attribute to the gas station
 * in addition 'cofee' attribute was changed to 'coffee'
 * 
 * Instructions:
 * Enter q to close the GasStation and view a financial report.
 * Note that the Gas station will be closed only after taking care of the current clients.
 * 
 * Bugs:
 * No known bugs.
 * 
 */
package BL.Main;


import gui.MainFrame;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import BL.Exceptions.*;
import BL.Init.*;
import BL.Logs.LogFormatter;
import Controller.GasStationController;
import Controller.JPADBController;
import DAL.IDBConnector;

public class Program {
	
	private static Logger theLogger = Logger.getLogger("mainLogger");
	private static FileHandler theFileHandler;
	private static ConsoleHandler theConsoleHandler;
	
	public static void main(String[] args) {
		try {
//			log_init();
//			theLogger.info("Welcome!");
//			theLogger.info("Press any key to open the GasStation.");
//			theLogger.info("Enter q to close the GasStation and view a financial report.\n\t\t\tNote that the Gasstation will be closed only after taking care of the current clients");
//			System.in.read(); //wait for input to open the station
			MainFrame stationView = new MainFrame();
			GasStation station = InitFromXmlFile.readFile("GasStationData.xml",stationView);
			stationView.setBLGasSTation(station);
//			GasStationController stationController =  new GasStationController(station, stationView.getMainPanel()); 
			//Init bl to ui
//			for (Pump pump : station.getPumps()) {
//				pump.fireAddPumpToUIEvent(pump.getPumpId());
//				for (Car c : pump.getCarsLine()) {
//					pump.fireAddCarToPumpLineInUIEvent(c, pump.getPumpId());
//					
//				}
//			}
//			
//			for (Cashier cash : station.getCoffeeHouse().getCashiers()) {
//				//TODO add cashiers.
//			}
//			
//			char c;
//			do{
//				c = (char) System.in.read(); //wait for input to close the station
//			}while(c!='q');
		//	station.closeStation();
			
			String report = "\n=================\nFinancial Report\n"
					+ "CoffeeHouse Income:\t"+station.getCoffeeHouseIncome()
					+ "\nPumps Income      :\t"+station.getPumpsIncome()
					+ "\nTotal             :\t" + station.getTotalIncome();
					
			theLogger.info(report);
			
		} catch (initExceptions e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		catch (java.lang.IllegalArgumentException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void log_init() throws SecurityException, IOException{
		theFileHandler = new FileHandler("logs\\mainlog.txt", false);
		theFileHandler.setFormatter(new LogFormatter());
		theConsoleHandler = new ConsoleHandler();
		theConsoleHandler.setFormatter(new LogFormatter());
		theLogger.addHandler(theFileHandler);
		theLogger.addHandler(theConsoleHandler);
		theLogger.setUseParentHandlers(false);
	}
}
