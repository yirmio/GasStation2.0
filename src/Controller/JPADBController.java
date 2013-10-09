
package Controller;

import BL.Main.Car;
import BL.Main.Cashier;
import BL.Main.GasStation;
import BL.Main.Pump;
import DAL.CoffeeHouseEntity;
import DAL.IDBConnector;
import DAL.PumpEntity;
import DAL.UpdateTypeEnum;
import ListenersInterfaces.CoffeeHouseBLEventListener;
import ListenersInterfaces.GasStationBLEventListener;
import ListenersInterfaces.PumpBLEventListener;
import java.sql.Timestamp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADBController implements IDBConnector, PumpBLEventListener, GasStationBLEventListener, CoffeeHouseBLEventListener{
    private GasStation gasStationBL;
    //CTOR
    public JPADBController(GasStation gasStationBL){
        this.gasStationBL = gasStationBL;
        // Register Pumps
        for (Pump p : gasStationBL.getPumps()) {
            p.registerListenerPump(this);
        }
        gasStationBL.getCoffeeHouse().registerListener(this);
    }
    @Override
    public void updaeIncomings(UpdateTypeEnum type, int carID, float amount, int locationID) {
        PumpEntity newPumpEntity = null;
        CoffeeHouseEntity newCoffeeHouseEntity = null;
        //Create the new entitiy to insert to th DB
        switch (type) {
            case Pump:
                newPumpEntity = new PumpEntity();
                newPumpEntity.setCarID(carID);
                newPumpEntity.setAmount((int)amount);
                newPumpEntity.setPumpID(locationID);
                newPumpEntity.setTime(new Timestamp(System.currentTimeMillis()));
                break;
            case CoffeeHouse:
                newCoffeeHouseEntity = new CoffeeHouseEntity();
                newCoffeeHouseEntity.setCarID(carID);
                newCoffeeHouseEntity.setAmount((int)amount);
                newCoffeeHouseEntity.setCashierID(locationID);
                newCoffeeHouseEntity.setTime(new Timestamp(System.currentTimeMillis()));
                break;
        }
        //Create EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GasStation2.0PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            if (newPumpEntity != null){
                em.persist(newPumpEntity);
            }
            else if (newCoffeeHouseEntity != null){
                em.persist(newCoffeeHouseEntity);
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        finally{
            em.close();
        }
        
        
        
        
    }
    
    @Override
    public String getPumpsSum() {
        //TODO - implement
        return null;
    }
    
    @Override
    public void addCarToPumpLineFromBLEvent(Car carToAdd, int pumpLine) {
        //Not Needed
    }
    
    @Override
    public void removeCarFromPumpLineFromBLEvent(Car carToRemove, int pumpLine) {
        //Not Needed
    }
    
    @Override
    public void addCarForRefuellingFromBLEvent(int pumpNo, Car carToAdd) {
        //Not Needed
    }
    
    @Override
    public void removeCarFromRefuellingFromBLEvent(int pumpNo) {
        //Not Needed
    }
    
    @Override
    //Update DB
    public void updateTotalMoneyPumpsFromBLEvent(Car car, float moneyToSet) {
        this.updaeIncomings(UpdateTypeEnum.Pump, car.getCarId(), moneyToSet*this.gasStationBL.getPricePerLiter(), car.getPump().getPumpId());
    }
    
    @Override
    public void FuelTrakArrivedFromBLEvent() {
        //Not Needed
    }
    
    @Override
    public void FuelTrakLeftFromBLEvent() {
        //Not Needed
    }

    @Override
    public void addNewCashierFromBLEvent(Cashier newCashier) {
        //Not Needed
    }

    @Override
    public void addCarToAroundFromBLEvent(Car carToAdd) {
        //Not Needed
    }

    @Override
    public void removeCarFromAroundFromBLEvent(Car carToRemove) {
        //Not Needed
    }

    @Override
    public void addCarToDrinkingFromBLEvent(Car carToAdd) {
        //Not Needed
    }

    @Override
    public void removeCarFromDrinkingFromBLEvent(Car carToRemove) {
        //Not Needed
    }

    @Override
    public void addCarToCashierLineFromBLEvent(Car carToAdd) {
        //Not Needed
    }

    @Override
    public void removeCarFromAllPlacesFromBLEvent(Car carToRemove) {
        //Not Needed
    }

    @Override
    public void addCarToCashierFromBLEvent(Car carToAdd, int cashierNo) {
        //Not Needed
    }

    @Override
    public void removeCarFromCashierFromBLEvent(Car carToRemove, int cashierNo) {
        //Not Needed
    }

    @Override
    public void updateTotalMoneyCoffeeHouseFromBL(float moneyToSet) {
        //Not Needed
    }
    
}
