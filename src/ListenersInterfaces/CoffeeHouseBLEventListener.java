package ListenersInterfaces;

import BL.Main.Car;
import BL.Main.Cashier;

public interface CoffeeHouseBLEventListener {
	public void addNewCashierFromBLEvent(Cashier newCashier);

	public void addCarToAroundFromBLEvent(Car carToAdd);

	public void removeCarFromAroundFromBLEvent(Car carToRemove);

	public void addCarToDrinkingFromBLEvent(Car carToAdd);

	public void removeCarFromDrinkingFromBLEvent(Car carToRemove);

	public void addCarToCashierLineFromBLEvent(Car carToAdd);

	public void removeCarFromAllPlacesFromBLEvent(Car carToRemove);

	public void addCarToCashierFromBLEvent(Car carToAdd, int cashierNo);

	public void removeCarFromCashierFromBLEvent(Car carToRemove, int cashierNo);

	public void updateTotalMoneyCoffeeHouseFromBL(float moneyToSet);
}
