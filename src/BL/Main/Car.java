package BL.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import BL.Logs.InstanceFilter;
import ListenersInterfaces.CarBLEventListener;
import ListenersInterfaces.PumpBLEventListener;

public class Car extends Thread {
	public static enum coffeeHousePositionEnum {
		around, inLine, cashRegister;
	}

	public static enum CAR_MODEL {
		VWbeatle, Peugeot107, CitroenC1, Modern, PaganiZonda,DefaultCar;
		public static CAR_MODEL getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }	
	
	
	};

	private static Logger theLogger = Logger.getLogger("mainLogger");

	private CAR_MODEL carModel;
	private int carId;
	private Pump pump;
	private float gasNeeded;
	private CoffeeHouse coffeehouse;
	private float amountToPayCoffee;
	private boolean wantsCoffee = false;
	private boolean wantsFuel = false;
	private coffeeHousePositionEnum coffeeHouseLocation;
	public Semaphore busy; // busy is acquired when pumping gas or paying at the
							// cash register
	private Vector<CarBLEventListener> allListenes;

	
	
	

	public Car(int id) throws SecurityException, IOException {
		/*
		 * Constructor gets only car id when reading the XML file the following
		 * methods will be called as needed: setWantsCoffee, setWantFuel
		 */
		this.allListenes = new Vector<CarBLEventListener>();
		setCarId(id);
		this.carModel = CAR_MODEL.getRandom();
		this.busy = new Semaphore(1);
		BL.Logs.InstanceFilter.initLog(this, "logs\\car\\car" + this.carId
				+ ".txt", theLogger);
		fireAddCarToAllCarsPanelFromBL(this);
	}

	private void fireAddCarToAllCarsPanelFromBL(Car car) {
		for (CarBLEventListener bl : this.allListenes) {
			bl.addCarToAllCarsFromBLEvent(car);
		}
	}


	public void setWantsCoffee(CoffeeHouse coffeeHouse,
			float amountToPayCoffee, coffeeHousePositionEnum coffeeHouseLocation) {
		this.wantsCoffee = true;
		setCoffeeHouseLocation(coffeeHouseLocation);
		setAmountToPayCoffee(amountToPayCoffee);
		setCoffehouse(coffeeHouse);
	}

	public void setWantFuel(Pump pump, float gasNeeded) {
		this.wantsFuel = true;
		setPump(pump);
		setGasNeeded(gasNeeded);
	}

	public coffeeHousePositionEnum getCoffeeHouseLocation() {
		return coffeeHouseLocation;
	}

	public void setCoffeeHouseLocation(
			coffeeHousePositionEnum coffeeHouseLocation) {
		this.coffeeHouseLocation = coffeeHouseLocation;
	}

	public Pump getPump() {
		return pump;
	}

	public void setPump(Pump pump) {
		this.pump = pump;
	}

	public CoffeeHouse getCoffehouse() {
		return coffeehouse;
	}

	public void setCoffehouse(CoffeeHouse coffehouse) {
		this.coffeehouse = coffehouse;
	}

	public float getGasNeeded() {
		return gasNeeded;
	}

	public void setGasNeeded(float gas_needed) {
		this.gasNeeded = gas_needed;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getCarId() {
		return this.carId;
	}

	public float getAmountToPayCoffee() {
		return amountToPayCoffee;
	}

	public void setAmountToPayCoffee(float amountToPayCoffe) {
		this.amountToPayCoffee = amountToPayCoffe;
	}

	public void fuelUp() throws InterruptedException {
		theLogger.log(Level.INFO, "Car(id: " + this.carId
				+ ") wants to fuel up.", this);

		synchronized (this) {
			int queue_size = this.pump.addWaitingCar(this);
			CoffeeThread drinkingCoffee = new CoffeeThread();
			if (wantsCoffee) {
				if (queue_size > 1) { // not first in line, free to go to the
										// coffee shop
					drinkingCoffee.start();
				}
			}
			this.pump.listenToQueue();
			wait(); // pump is ready

			// if car is at the coffee house, may interrupt only if not paying
			// in the cash register (in other words: if not busy)
			if (wantsCoffee) { // check if wants coffee
				if (busy.tryAcquire()) { // not paying in the cash register
					busy.release();
					drinkingCoffee.interrupt();
					while (drinkingCoffee.isAlive())
						; // wait until client leaves the coffee house
				}
			}
		}

		// start the gas fueling
		synchronized (pump) {
			float gas = this.pump.fuelUp(gasNeeded); // returns how much we
														// fueled (relays on
														// fuel reserve)
			pump.fireRemoveCarFromPumpLineFromBLEvent(this, pump.getPumpId());// GUI
coffeehouse.fireRemoveCarFromAllPlacesFromBLEvent(this);
			pump.fireRefuelCarFromBLEvent(pump.getPumpId(), this);// GUI
			long fuelTime = (long) gas * 100; // time is a function of gas
												// fueled
			theLogger.log(Level.INFO, "Car(id: " + this.carId
					+ ") is fueling at pump " + pump.getPumpId(), this);
			Thread.sleep(fuelTime); // go to sleep ! (fueling...)
			pump.fireRemoveCarFromRefuelingUIEvent(pump.getPumpId());// GUI
			pump.fireUpdateTotalPumpsMoneyFromBLEvent(gasNeeded);
		
			theLogger
					.log(Level.INFO, "Car(id: " + this.carId
							+ ") is done fueling (" + gas + "/" + gasNeeded
							+ ")", this);
			pump.notifyAll(); // notify the pump we are done here
		}
	}

	public void coffeeUp() throws InterruptedException {
		theLogger.log(Level.INFO, "Car(id: " + Car.this.carId
				+ ") is entering the coffee house", Car.this);
		Cashier cashier = null;
		switch (this.coffeeHouseLocation) {
		case around:
			theLogger.log(Level.INFO, "car(id: " + Car.this.carId
					+ ") is looking around the coffee house", Car.this);
			getCoffehouse().fireAddCarToAround(this); // GUI
			sleep((long) (amountToPayCoffee * 100)); // looking around is
														// function of
														// amountToPayCoffee
			this.coffeeHouseLocation = coffeeHousePositionEnum.inLine; // set
																		// next
																		// position
			// no break we would like to check the next case

		case inLine:
			getCoffehouse().fireRemoveCarFromAroundInUI(this);// GUI
			getCoffehouse().fireAddCarToCashierLineInUI(this);// GUI
			theLogger.log(Level.INFO, "Car(id: " + Car.this.carId
					+ ") is in line to pay", Car.this);
			sleep((long) (amountToPayCoffee * 100));
			cashier = coffeehouse.getCashier(); // get a random cashier
			cashier.acquire();
			busy.acquire(); // from this point interrupt from pump can't occur
			this.coffeeHouseLocation = coffeeHousePositionEnum.cashRegister; // set
																				// next
																				// position
			// no break we would like to check the next case

		case cashRegister:
			getCoffehouse().fireRemoveCarFromAllPlacesFromBLEvent(this);// GUI
			getCoffehouse().fireAddCarToPayInCashierInUI(this, cashier.getId());// GUI
			theLogger.log(Level.INFO, "Car(id: " + Car.this.carId
					+ ") is paying for the merchandise", Car.this);
			sleep((long) (amountToPayCoffee * 100));
			cashier.payUp(this); // pay for coffee
			cashier.release();
			busy.release();
			getCoffehouse().fireRemoveCarFromPayingInCashierInUI(this,
					cashier.getId());// GUI
			break;

		default:
			break;
		}
		getCoffehouse().fireAddCarToDrinkingInUI(this);// GUI (Already removed
														// from cashier)
		theLogger.log(Level.INFO, "Car(id: " + Car.this.carId
				+ ") is leaving the coffee house", Car.this);
	}

	@Override
	public void run() {
		try {
			if (wantsFuel) {
				fuelUp();
			} else if (wantsCoffee) {
				coffeeUp();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		theLogger.log(Level.INFO, "Car(id: " + this.carId
				+ ") is leaving the GasStation", this);
	}

	// if pump is busy and wants coffee we'll open a new thread
	class CoffeeThread extends Thread {
		@Override
		public void run() {
			try {
				coffeeUp();
				synchronized (Car.this.pump) {
					Car.this.pump.notifyAll();
				}
			} catch (InterruptedException e) {

				theLogger.log(Level.INFO, "Car(id: " + Car.this.carId
						+ ") is intterupted and leaving the coffee house",
						Car.this);
				getCoffehouse().fireRemoveCarFromAllPlacesFromBLEvent(Car.this);
			}
			Car.this.wantsCoffee = false;
		}

	}
	public CAR_MODEL getCarModel() {
		return carModel;
	}
}
