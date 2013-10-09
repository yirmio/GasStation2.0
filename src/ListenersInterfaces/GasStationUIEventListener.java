package ListenersInterfaces;

import BL.Main.Car.CAR_MODEL;

public interface GasStationUIEventListener {

	void addNewCarFromUIEvent(int tmpID, int amountToPay, int litters,
			CAR_MODEL choosenModel);

}
