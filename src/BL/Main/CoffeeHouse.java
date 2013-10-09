package BL.Main;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import BL.Logs.InstanceFilter;
import ListenersInterfaces.CoffeeHouseBLEventListener;
import ListenersInterfaces.FuelPoolBLEventListener;

public class CoffeeHouse {
	private static Logger theLogger = Logger.getLogger("mainLogger");
	private Vector<Cashier> cashiers;
	private float income;
	private Vector<CoffeeHouseBLEventListener> listeners;

	public CoffeeHouse(int numberOfCashiers) throws SecurityException,
			IOException {
		this.listeners = new Vector<CoffeeHouseBLEventListener>();
		this.cashiers = new Vector<Cashier>();
		for (int i = 0; i < numberOfCashiers; ++i) {
			this.cashiers.add(new Cashier(this));
//			this.fireAddCashiersToUI(numberOfCashiers);
		}
		setIncome(0);
		InstanceFilter.initLog(this, "logs\\coffeehouse.txt", theLogger);
	}

	public void setIncome(float income) {
		this.income = income;
		
	}

	

	public Cashier getCashier() {
		double tmp = Math.random() * cashiers.size();
		
		int index = (int) (tmp % cashiers.size());
		return cashiers.elementAt(index);
	}

	public void addIncome(float amount) {
		this.income += amount;
		fireUpdateIncomeInCoffeeHouseUI(this.income);
	}

	public float getIncome() {
		return this.income;
	}

	public void closeCoffeeHouse() throws InterruptedException {
		for (Cashier c : cashiers) {
			c.acquire();
		}
	}

	// add listeners so when fired will return command to update in view model
	public void registerListener(CoffeeHouseBLEventListener listener) {
		listeners.add(listener);
	}

	// continue the process to update the view in UI via the controller
	public void fireAddCarToAround(Car carToAdd) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.addCarToAroundFromBLEvent(carToAdd);
		}
	}
	public void fireRemoveCarFromAroundInUI(Car carToRemove) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.removeCarFromAroundFromBLEvent(carToRemove);
		}
	}
	public void fireAddCarToCashierLineInUI(Car carToAdd) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.addCarToCashierLineFromBLEvent(carToAdd);
		}
	}
	public void fireRemoveCarFromAllPlacesFromBLEvent(Car carToRemove) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.removeCarFromAllPlacesFromBLEvent(carToRemove);
		}
	}
	public void fireAddCarToPayInCashierInUI(Car carToAdd,int cashierNo) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.addCarToCashierFromBLEvent(carToAdd, cashierNo);
		}
	}
	public void fireRemoveCarFromPayingInCashierInUI(Car carToRemove,int cashierNo) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.removeCarFromCashierFromBLEvent(carToRemove, cashierNo);
		}
	}
	public void fireAddCarToDrinkingInUI(Car carToAdd) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.addCarToDrinkingFromBLEvent(carToAdd);
		}
	}
	public void fireRemoveCarFromDrinkingInUI(Car carToRemove) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.removeCarFromDrinkingFromBLEvent(carToRemove);
		}
	}
	public void fireAddCashierToUI(Cashier newCashier) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.addNewCashierFromBLEvent(newCashier);
		}
	}

	public Vector<Cashier> getCashiers() {
		return this.cashiers;
	}
	private void fireUpdateIncomeInCoffeeHouseUI(float income) {
		for (CoffeeHouseBLEventListener i : listeners) {
			i.updateTotalMoneyCoffeeHouseFromBL(income);
		}
		
	}
}
