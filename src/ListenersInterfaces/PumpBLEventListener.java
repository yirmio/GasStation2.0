package ListenersInterfaces;

import BL.Main.Car;

public interface PumpBLEventListener {
	public void addCarToPumpLineFromBLEvent(Car carToAdd, int pumpLine);

	public void removeCarFromPumpLineFromBLEvent(Car carToRemove, int pumpLine);

	public void addCarForRefuellingFromBLEvent(int pumpNo, Car carToAdd);

	public void removeCarFromRefuellingFromBLEvent(int pumpNo);

	public void updateTotalMoneyPumpsFromBLEvent(float moneyToSet);
}
