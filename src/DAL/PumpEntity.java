/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

/**
 *
 * @author Tal Ohev-Zion
 */
@Entity
public class PumpEntity implements Serializable, IDBConnector {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name = "CarID")
    private int carID;
    
    @Column (name = "Amount")
    private int amount;
    
    @Column (name = "PumpID")
    private int pumpID;
    
    @Column (name = "Time")
    private Timestamp time;
  
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the carID
     */
    public int getCarID() {
        return carID;
    }

    /**
     * @param carID the carID to set
     */
    public void setCarID(int carID) {
        this.carID = carID;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the pumpID
     */
    public int getPumpID() {
        return pumpID;
    }

    /**
     * @param pumpID the pumpID to set
     */
    public void setPumpID(int pumpID) {
        this.pumpID = pumpID;
    }

    /**
     * @return the time
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PumpEntity)) {
            return false;
        }
        PumpEntity other = (PumpEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAL.PumpsEntity[ id=" + id + " ]";
    }
    
    public void PumpsEntity (){
    
    }
            
     
    
    public void updaeIncomings(UpdateTypeEnum type, int carID,  float amount, int locationID){
        this.setCarID(carID);
        this.setAmount((int)amount);
        this.setPumpID(locationID);
        this.setTime(new Timestamp(System.currentTimeMillis()));
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GasStation2.0PU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        try{
        em.persist(this);
        em.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        finally{
        em.close();
        }
    }
    public String getPumpsSum(){
    
        return null;
    
    }
}
