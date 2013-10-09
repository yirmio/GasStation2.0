/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

/**
 *
 * @author Tal Ohev-Zion
 */
public interface IDBConnector {
    public void updaeIncomings(UpdateTypeEnum type, int carID,  float amount, int locationID);
    public String getPumpsSum();

}
