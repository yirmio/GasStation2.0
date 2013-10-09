package BL.Main;

import gui.CarPanelLable;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import BL.Logs.InstanceFilter;

@SuppressWarnings("serial")
public class Cashier extends Semaphore{
	private static Logger theLogger = Logger.getLogger("mainLogger");
	private static int idCount = 1;	
	private int id;
	private CoffeeHouse coffeeHouse;
	private CarPanelLable carInPay;


	public Cashier(CoffeeHouse coffeeHouse) throws SecurityException, IOException{
		super(1);
		setId(Cashier.idCount++);
		setCoffeeHouse(coffeeHouse);
		InstanceFilter.initLog(this.coffeeHouse, "logs\\coffeehouse.txt", theLogger);
	}

	public CoffeeHouse getCoffeeHouse() {
		return coffeeHouse;
	}

	public void setCoffeeHouse(CoffeeHouse coffeeHouse) {
		this.coffeeHouse = coffeeHouse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void payUp(Car c){
		theLogger.log(Level.INFO,"Car(id:" + c.getCarId() + ") paid " + c.getAmountToPayCoffee() + " at the coffee house", this.coffeeHouse);
		coffeeHouse.addIncome(c.getAmountToPayCoffee());
		getCoffeeHouse().fireAddCarToPayInCashierInUI(c,this.id);//GUI
	}
	
	public CarPanelLable getCarInPay() {
		return carInPay;
	}

	public void setCarInPay(CarPanelLable carInPay) {
		this.carInPay = carInPay;
	}
}
