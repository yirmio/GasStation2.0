package BL.Main;
//TODO: Add more Hummus
//TODO: Rearrange LOGGERS


import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import ListenersInterfaces.FuelPoolBLEventListener;
import ListenersInterfaces.GasStationBLEventListener;


public class GasStation implements Observer{

	private static Logger theLogger = Logger.getLogger("mainLogger");
	private static int idCount = 0;
	private static int pumpIdCount = 1;
	private int id;
	private String name; 
	private Vector<Pump> pumps;
	private float pricePerLiter;
	private CoffeeHouse coffeeHouse;
	private FuelPool fuelReserve;
	private Vector<GasStationBLEventListener> listeners;
	
	public GasStation(int id, String name, float pricePerLiter, FuelPool fuelreserve, CoffeeHouse coffeeHouse) throws Exception {
		setId(GasStation.idCount++);
		setName(name);
		setPricePerLiter(pricePerLiter);
		setFuelReserve(fuelreserve);
		setCoffeeHouse(coffeeHouse);
		this.pumps = new Vector<Pump>();
		this.listeners = new Vector<GasStationBLEventListener>();
		
		this.fuelReserve.addObserver(this);
		theLogger.info(this.name + " Gas Station is now open!");
	}

	@Override
	public void update(Observable o, Object arg){
		if (o instanceof FuelPool){
			//notification from fuel pool, meaning: we're 20% of max capacity - call the fuel track!
			theLogger.warning((String)arg);
			Thread fuelTruck = new Thread() {
			    public void run() {
				       try {
				    	synchronized(fuelReserve){
				    		Random rand = new Random(); 
							long timeToArrive = ((long)rand.nextInt(5)*1000);
							theLogger.info("Fuel track will arrive in " + timeToArrive/1000 + " seconds");
							sleep(timeToArrive);
							theLogger.info("Fuel track arrvied, starting to fuel...");
							fireFuelTrakArrivedFromBLEvent();
							
							sleep(5000);
							fuelReserve.fuelTrackLeft();
							theLogger.info("Fuel track is done fueling");
							fireFuelTrackLeftFromBLEvent();
				    	}
				       } 
				       catch (InterruptedException e) {
						e.printStackTrace();
					}
			    }
			};
			fuelTruck.start();
		}
	}
	
	protected void fireFuelTrackLeftFromBLEvent() {
for (GasStationBLEventListener lstnr : this.listeners) {
	lstnr.FuelTrakLeftFromBLEvent();
}		
	}



	private void fireFuelTrakArrivedFromBLEvent() {
for (GasStationBLEventListener lstnr : this.listeners) {
	lstnr.FuelTrakArrivedFromBLEvent();
}		
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public float getPricePerLiter() {
		return pricePerLiter;
	}
	public void setPricePerLiter(float pricePerLiter) {
		this.pricePerLiter = pricePerLiter;
	}

	public CoffeeHouse getCoffeeHouse() {
		return coffeeHouse;
	}
	public void setCoffeeHouse(CoffeeHouse coffeeHouse) {
		this.coffeeHouse = coffeeHouse;
	}

	public void addPump(){
		try {
			Pump pump = new Pump(pumpIdCount++, fuelReserve, this);
			pumps.add(pump);
			pump.fireAddPumpToUIEvent(pumpIdCount);// add pump to GUI
			pump.start();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void enterGasStation(Car car){
		theLogger.log(Level.INFO,"car(id: " +car.getCarId() + ") is entering the GasStation", car);
		car.start();
	}

	public FuelPool getFuelReserve() {
		return fuelReserve;
	}

	public void setFuelReserve(FuelPool fuelReserve) {
		this.fuelReserve = fuelReserve;
	}
	
	//Fuel at chosen PumpID
	public Pump getPumpAt(int id) {
		try{
			return pumps.elementAt(id-1);
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public void closeStation(){
		theLogger.info("Closing Station.. Note that the Gasstation will be closed only after taking care of the current clients");		
			
		for (Pump p: pumps)
				p.closePump();

		
		for (Pump p: pumps){
			try {
				p.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.coffeeHouse.closeCoffeeHouse();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		theLogger.info("Finished closing all services......");
	}
	
	public float getPumpsIncome(){
		float totalMoneyPumps = this.fuelReserve.getFuelSold() * this.pricePerLiter;
		return totalMoneyPumps;
	}
	


	public float getCoffeeHouseIncome(){
		return this.coffeeHouse.getIncome();
	}
	
	public float getTotalIncome(){
		return getPumpsIncome()+getCoffeeHouseIncome();
	}
	public Vector<Pump> getPumps() {
		return pumps;
	}
	
	public void registerListener(GasStationBLEventListener lstnr){
		this.listeners.add(lstnr);
	}
}
