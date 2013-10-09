
package Controller;

import DAL.CoffeeHouseEntity;
import DAL.IDBConnector;
import DAL.PumpEntity;
import DAL.UpdateTypeEnum;
import java.sql.Timestamp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADBController implements IDBConnector{
    
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
