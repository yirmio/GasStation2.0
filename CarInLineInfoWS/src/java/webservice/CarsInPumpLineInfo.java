/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;


/**
 *
 * @author Tal Ohev-Zion
 */

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(serviceName = "NoOfCarsInPump")
public class CarsInPumpLineInfo {

    @WebMethod(operationName = "check_number_of_cars")
    public int showNumCarInPumpLine(@WebParam(name = "Enter_number_of_pump") int pumpNo){
        return pumpNo;
    }
}
