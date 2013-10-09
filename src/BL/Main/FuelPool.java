package BL.Main;
import java.util.Observable;
import java.util.Vector;

import BL.Exceptions.*;
import ListenersInterfaces.FuelPoolBLEventListener;


public class FuelPool extends Observable{
	/* FuelReserve:
	 * Each Gas station has a fuel reverse.
	 * Fuel reserve implements the Observer design pattern which notify's the
	 * Gas Station and pumps when currentCapacity is below 20% of the maxCapacity
	 */
	
	public final static float MINIMUM_FUEL = 0.2f;
	private float maxCapacity;
	private float currentCapacity;
	private float fuelSold;
	private boolean callTrack;
	private Vector<FuelPoolBLEventListener> listeners;
	
	public FuelPool(float maxCapacity, float currentCapacity){
		this.maxCapacity = maxCapacity;
		setCurrentCapacity(currentCapacity);
		this.setCallTrack(false);
		this.fuelSold=0;
		listeners = new Vector<FuelPoolBLEventListener>();
	}
	
	/*Calculates how much fuel sold so far*/
	public float getFuelSold(){
		return this.fuelSold;
	}
public void setChanged(){
	super.setChanged();
}
	/* Updates current fuel amount
	 * notify's all observers
	 */
	public synchronized void pumpFuel(float delta) throws AbsException{
		float fuel_after_delta = currentCapacity - delta;
		//Gas station notification  below MINIMUM_FUEL
		if (fuel_after_delta/maxCapacity <= MINIMUM_FUEL && isCallTrack()==false)
		{
			setCallTrack(true);
			super.setChanged();
			super.notifyObservers("Fuel reserve is below " + MINIMUM_FUEL*100 + " percent! calling the fuel track!");
		}
		
		//Pumps notification
		if(fuel_after_delta<0.0f){
			float temp_current = currentCapacity;
			this.fuelSold+=currentCapacity;
			setCurrentCapacity(0.0f);
			throw new NoFuelLeft("Not enough fuel, only " + temp_current + "/" + Math.abs(delta) + " fueled", temp_current);
		}
		
		this.fuelSold+=delta;
		setCurrentCapacity(fuel_after_delta);
		fireFillPoolEvent((int)fuel_after_delta);//update in gui
	}
	
	
	/*only setters and getters are below*/
	public float getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(float maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public float getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(float currentCapacity) {
		this.currentCapacity = currentCapacity;
		fireSetCurrentQuantityEvent(currentCapacity, this.maxCapacity);
	}
	
	public boolean isCallTrack() {
		return callTrack;
	}

	public void setCallTrack(boolean callTrack) {
		this.callTrack = callTrack;
	}

	public void fuelTrackLeft(){
		this.currentCapacity = this.maxCapacity;
		setCallTrack(false);
		fireSetCurrentQuantityEvent(this.maxCapacity,this.maxCapacity);
	}
	
	//add listeners so when fired will return command to update in view model
	public void registerListener(FuelPoolBLEventListener listener){
		listeners.add(listener);
	}
	
	//continue the process to update the view in UI via the controller
	private void fireFillPoolEvent(int capacity) {
		for (FuelPoolBLEventListener i:listeners){
		}
		
	}
	private void fireSetCurrentQuantityEvent(float qty, float max){
		if (this.listeners != null) {
			for (FuelPoolBLEventListener i : listeners) {
				i.setNewCurrentQuantityFromBLEvent(qty,max);
			}
		}
	}
}
