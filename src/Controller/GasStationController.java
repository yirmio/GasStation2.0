package Controller;

import gui.FuelPoolPanel;
import gui.FuelPumpListPanel;
import gui.FuelPumpPanel;
import gui.GasStationPanel;
import java.io.IOException;
import BL.Main.Car;
import BL.Main.Car.CAR_MODEL;
import BL.Main.Cashier;
import BL.Main.FuelPool;
import BL.Main.GasStation;
import BL.Main.Pump;
import ListenersInterfaces.CarBLEventListener;
import ListenersInterfaces.CoffeeHouseBLEventListener;
import ListenersInterfaces.FuelPoolBLEventListener;
import ListenersInterfaces.FuelPoolUIEventListener;
import ListenersInterfaces.GasStationBLEventListener;
import ListenersInterfaces.GasStationUIEventListener;
import ListenersInterfaces.PumpBLEventListener;
import ListenersInterfaces.PumpListBLEventListener;

public class GasStationController implements FuelPoolUIEventListener,
		FuelPoolBLEventListener, CarBLEventListener, PumpListBLEventListener,
		PumpBLEventListener, CoffeeHouseBLEventListener,
		GasStationBLEventListener, GasStationUIEventListener {
	private GasStation gasStationBL;
	private GasStationPanel gasStationUI;
	private FuelPoolPanel fuelPoolView;
	private FuelPumpListPanel fuelPumpListUI;

	public GasStationController(GasStation gasStationBL,
			GasStationPanel gasStationUI) {
		super();
		this.gasStationBL = gasStationBL;
		this.gasStationUI = gasStationUI;
		this.fuelPoolView = gasStationUI.getAllCarsPanel().getFuelPoolPanel();
		this.fuelPumpListUI = gasStationUI.getFuelPumpsListPanel();

		// Register BL Listeners
		this.gasStationBL.registerListener(this);
		for (Pump i : this.gasStationBL.getPumps()) {// Register Pumps
			i.registerListenerPump(this);
			i.registerListenerPumpLine(this);
		}
		this.gasStationBL.getCoffeeHouse().registerListener(this);// Register
																	// CoffeeHouse

		this.gasStationBL.getFuelReserve().registerListener(this);// Register
																	// FuelPool

		// Register UI Listeners
		this.gasStationUI.registerListener(this);
		this.fuelPoolView.registerListener(this);// Add the GS to the listeners
													// list. so on clicking it
													// will initiate the process

		for (FuelPumpPanel fpnl : this.gasStationUI.getFuelPumpsListPanel()
				.getAllPumpsPanels()) {
			fpnl.registerListener(this);
		}
		this.fuelPumpListUI.registerListener(this);
	}

	@Override
	public void fillFuelPoolFromUIEvent() {// initiated by "fireFillPoolPressed"
											// in UI
		this.gasStationBL.getFuelReserve().setCallTrack(true);
		this.gasStationBL.getFuelReserve().setChanged();
		this.gasStationBL.getFuelReserve().notifyObservers(
				"Fuel reserve is below " + FuelPool.MINIMUM_FUEL * 100
						+ " percent! calling the fuel track!");

	}

	@Override
	public void addCarToAllCarsFromBLEvent(Car c) {
		this.gasStationUI.getAllCarsPanel().insertCarToPanel(c);
	}

	public void removeCarFromPumpLineFromBLEvent(Car carToRemove, int pumpNo) {
		FuelPumpPanel tmpPumpPnl = fuelPumpListUI.findPumpPanelInVector(pumpNo);
		if (tmpPumpPnl != null) {
			tmpPumpPnl.removeCarFromPumpLineInUI(carToRemove);
		}
	}

	public void addCarForRefuellingInUI(Car carToAdd, int pumpNo) {
		FuelPumpPanel tmpPumpPnl = fuelPumpListUI.findPumpPanelInVector(pumpNo);
		if (tmpPumpPnl != null) {
			tmpPumpPnl.addCarForRefuellingInUI(carToAdd);
		}
	}

	public void removeCarFromRefuellingFromBLEvent(int pumpNo) {
		FuelPumpPanel tmpPumpPnl = fuelPumpListUI.findPumpPanelInVector(pumpNo);
		if (tmpPumpPnl != null) {
			tmpPumpPnl.removeCarFromRefuellingInUI();
		}
	}

	public void addPumpToListFromBLEvent(int pumpNo) {
		fuelPumpListUI.addPumpsToListInUI(pumpNo);
	}

	@Override
	public void addCarForRefuellingFromBLEvent(int pumpNo, Car carToAdd) {
		FuelPumpPanel tmpPumpPnl = fuelPumpListUI.findPumpPanelInVector(pumpNo);
		if (tmpPumpPnl != null) {
			tmpPumpPnl.addCarForRefuellingInUI(carToAdd);
		}
	}

	@Override
	public void addCarToAroundFromBLEvent(Car carToAdd) {
		gasStationUI.getCoffeeHousePanel().addCarToAroundPanelInUI(carToAdd);
	}

	@Override
	public void removeCarFromAroundFromBLEvent(Car carToRemove) {
		gasStationUI.getCoffeeHousePanel().removeCarFromAroundPanelInUI(
				carToRemove);
	}

	@Override
	public void addCarToDrinkingFromBLEvent(Car carToAdd) {
		gasStationUI.getCoffeeHousePanel().addCarToDrinkingPanelInUI(carToAdd);
	}

	@Override
	public void removeCarFromDrinkingFromBLEvent(Car carToRemove) {
		gasStationUI.getCoffeeHousePanel().removeCarFromDrinkingPanelInUI(
				carToRemove);
	}

	@Override
	public void addCarToCashierLineFromBLEvent(Car carToAdd) {
		gasStationUI.getCoffeeHousePanel().addCarToCashierLinePanelInUI(
				carToAdd);
	}

	@Override
	public void removeCarFromAllPlacesFromBLEvent(Car carToRemove) {
		gasStationUI.getCoffeeHousePanel().removeCarFromAllPlacesInUI(
				carToRemove);
	}

	@Override
	public void addCarToCashierFromBLEvent(Car carToAdd, int cashierNo) {
		gasStationUI.getCoffeeHousePanel().addCarToCashierPanelInUI(carToAdd,
				cashierNo);
	}

	@Override
	public void removeCarFromCashierFromBLEvent(Car carToRemove, int cashierNo) {
		gasStationUI.getCoffeeHousePanel().removeCarFromCashierPanelInUI(
				carToRemove, cashierNo);
	}

	@Override
	public void addCarToPumpLineFromBLEvent(Car carToAdd, int pumpLine) {
		this.gasStationUI.getFuelPumpsListPanel()
				.findPumpPanelInVector(pumpLine).addCarToPumpLineInUI(carToAdd);

	}

	@Override
	public void addNewCashierFromBLEvent(Cashier newCashier) {
		gasStationUI.getCoffeeHousePanel().addNewCashierToUI(newCashier);
	}

	@Override
	public void setNewCurrentQuantityFromBLEvent(float qty, float max) {
		gasStationUI.getAllCarsPanel().getFuelPoolPanel()
				.setCurrentQuantity(qty, max);
	}

	@Override
	public void FuelTrakArrivedFromBLEvent() {
		gasStationUI.getParent().getParent().setEnabled(false);
		gasStationUI.getAllCarsPanel().getFuelPoolPanel().changeIcon();
		gasStationUI.getAllCarsPanel().getFuelPoolPanel()
				.setNewText("Filling the fuel pool");
	}

	@Override
	public void FuelTrakLeftFromBLEvent() {
		gasStationUI.setEnabled(true);
		gasStationUI.getAllCarsPanel().getFuelPoolPanel().changeIcon();

	}

	@Override
	public void updateTotalMoneyCoffeeHouseFromBL(float moneyToSet) {
		gasStationUI.getCoffeeHousePanel().updateTotalMoney(moneyToSet);
	}

	public void updateTotalMoneyPumpsFromBLEvent(Car car,float moneyToSet) {
		gasStationUI.getFuelPumpsListPanel().updateTotalMoney(
				moneyToSet * gasStationBL.getPricePerLiter());
	}

	@Override
	public void addNewCarFromUIEvent(int tmpID, int amountToPay, int litters,
			CAR_MODEL choosenModel) {
		try {
			Car newcar = new Car(tmpID);
			Pump pump = null;
			Car.coffeeHousePositionEnum coffeHouseLocation = null;
			float coffeHouseToPay = 0.0f;
			float gasNeeded = 0.0f;
			// Wants coffee
			if (amountToPay > 0) {
				coffeHouseLocation = Car.coffeeHousePositionEnum.around;
				coffeHouseToPay = amountToPay;
                                newcar.setWantsCoffee(this.gasStationBL.getCoffeeHouse(),
						coffeHouseToPay, coffeHouseLocation);
				
			}
                        newcar.setCoffehouse(this.gasStationBL.getCoffeeHouse());

			// Wants refuel
			if (litters > 0) {
				gasNeeded = litters;
				int tmp = (int) (Math.random() * this.gasStationBL.getPumps()
						.size()) + 1;
				pump = this.gasStationBL.getPumpAt(tmp);
				newcar.setWantFuel(pump, gasNeeded);

			}
			this.gasStationBL.enterGasStation(newcar);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
