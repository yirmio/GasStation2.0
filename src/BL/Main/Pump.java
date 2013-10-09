package BL.Main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import BL.Exceptions.AbsException;
import BL.Exceptions.NoFuelLeft;
import BL.Logs.InstanceFilter;
import ListenersInterfaces.FuelPoolBLEventListener;
import ListenersInterfaces.PumpListBLEventListener;
import ListenersInterfaces.PumpBLEventListener;

public class Pump extends Thread {
	private static Logger theLogger = Logger.getLogger("mainLogger");

	private int pumpId;
	private FuelPool fuelPool; // note: not static because maybe we'd like to
								// add more gas stations
	private Queue<Car> cars_queue;
	private boolean isOpen;
	private Vector<PumpListBLEventListener> listenersPumpList;
	private Vector<PumpBLEventListener> listenersPump;

	public Pump(int id, FuelPool fuelreserve, GasStation gasStation)
			throws SecurityException, IOException {
		setId(id);
		openPump();
		setFuelPool(fuelreserve);
		this.cars_queue = new LinkedList<Car>();
		InstanceFilter.initLog(this, "logs\\pump\\pump" + this.pumpId + ".txt",
				theLogger);
		theLogger.log(Level.INFO, "New Pump created and ready with id: "
				+ this.pumpId, this);
		this.listenersPump = new Vector<PumpBLEventListener>();
		this.listenersPumpList = new Vector<PumpListBLEventListener>();
	}

	public boolean isOpen() {
		return isOpen;
	}

	public FuelPool getFuelPool() {
		return fuelPool;
	}

	public void setFuelPool(FuelPool fuelReserve) {
		this.fuelPool = fuelReserve;
	}

	public int getPumpId() {
		return pumpId;
	}

	public void setId(int id) {
		this.pumpId = id;
	}

	public void closePump() {
		theLogger.log(Level.INFO, "Pump: " + this.pumpId
				+ " will be closed after queue will clear", this);
		this.isOpen = false;
		synchronized (this) {
			this.notifyAll();
		}
	}

	public void openPump() {
		theLogger
				.log(Level.INFO, "Pump(id: " + this.pumpId + ") is open", this);
		this.isOpen = true;
	}

	public int addWaitingCar(Car car) {
		cars_queue.add(car);
		this.fireAddCarToPumpLineInUIEvent(car, this.pumpId);// add car to pump
																// line in GUI
		theLogger.log(Level.INFO, "Car(id:" + car.getCarId()
				+ ") is queued for pump " + this.pumpId, this);
		return cars_queue.size();
	}

	public float fuelUp(float amount) {
		float actualAmount = amount;
		try {
			this.fuelPool.pumpFuel(amount);
		} catch (AbsException e) {
			actualAmount = ((NoFuelLeft) e).getLeftover();
		}
		theLogger.log(Level.INFO, "Pump(id:" + this.pumpId + ") reserved "
				+ actualAmount + " fuel for queued car", this);
		return actualAmount;
	}

	public synchronized void listenToQueue() {
		if (cars_queue.size() >= 1) {
			notify(); // there's a car waiting. (release self when waiting for
						// customers)
		}
	}

	public void notifyCar() {
		try {
			Car firstCar = cars_queue.peek(); // get the first car waiting
			if (firstCar != null) {
				theLogger.log(Level.INFO,
						"Pump(id:" + this.pumpId + ") is notifying car(id: "
								+ firstCar.getCarId() + ")", this);
				if (firstCar.busy.tryAcquire()) { // acquire should be true only
													// if client is currently
													// paying (at the cash
													// register)
					synchronized (firstCar) {
						firstCar.notifyAll(); // clearance to pump gas
					}
					synchronized (this) {
						firstCar.busy.release();
						wait(); // waiting for car to finish
					}
				} else { // loosing turn, pushed to the end of the line
					theLogger.log(Level.INFO, "Car(id: " + firstCar.getCarId()
							+ ") pushed to the end of the line.", this);
					cars_queue.add(firstCar); // added to the end of the queue
					this.fireRemoveCarFromPumpLineFromBLEvent(firstCar, this.pumpId);//GUI remove and add again
					this.fireAddCarToPumpLineInUIEvent(firstCar, this.pumpId);
				}
			}
			cars_queue.remove(); // remove firstCar from queue
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// checks if car queue is not empty and there are no busy cars
	private boolean checkLine() {
		if (!cars_queue.isEmpty()) {
			for (Car c : cars_queue) {
				if (c.busy.tryAcquire()) {
					c.busy.release();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (this.isOpen || this.cars_queue.size() > 0) {

			if (checkLine()) {
				notifyCar();
			} else {
				synchronized (this) { // waiting for car to wake me up
										// (addWaitingCar method)
					try {
						theLogger.log(Level.INFO, "Pump(id: " + this.pumpId
								+ ") is waiting for customers", this);
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// add listeners so when fired will return command to update in view model
	public void registerListenerPumpLine(PumpListBLEventListener listener) {
		listenersPumpList.add(listener);
	}

	// add listeners so when fired will return command to update in view model
	public void registerListenerPump(PumpBLEventListener listener) {
		listenersPump.add(listener);
	}

	// continue the process to update the view in UI via the controller
	public void fireAddPumpToUIEvent(int pumpID) {
		for (PumpListBLEventListener i : listenersPumpList) {
			i.addPumpToListFromBLEvent(pumpID);
		}
	}

	// continue the process to update the view in UI via the controller
	public void fireAddCarToPumpLineInUIEvent(Car carToAdd, int pumpLine) {
		for (PumpBLEventListener i : listenersPump) {
			i.addCarToPumpLineFromBLEvent(carToAdd, pumpLine);
		}

	}

	// continue the process to update the view in UI via the controller
	public void fireRemoveCarFromPumpLineFromBLEvent(Car carToRemove, int pumpLine) {
		for (PumpBLEventListener i : listenersPump) {
			i.removeCarFromPumpLineFromBLEvent(carToRemove, pumpLine);
		}
	}

	// continue the process to update the view in UI via the controller
	public void fireRefuelCarFromBLEvent(int pumpNo, Car car) {
		for (PumpBLEventListener i : listenersPump) {
			i.addCarForRefuellingFromBLEvent(pumpNo,car);
		}
	}

	// continue the process to update the view in UI via the controller
	public void fireRemoveCarFromRefuelingUIEvent(int pumpNo) {
		for (PumpBLEventListener i : listenersPump) {
			i.removeCarFromRefuellingFromBLEvent(pumpNo);
		}
	}
	
	public void fireUpdateTotalPumpsMoneyFromBLEvent(float totalMoneyPumps) {
		for (PumpBLEventListener i : listenersPump) {
			i.updateTotalMoneyPumpsFromBLEvent(totalMoneyPumps);
		}
		
	}
	
	public Queue<Car> getCarsLine() {
		return this.cars_queue;
	}
}
