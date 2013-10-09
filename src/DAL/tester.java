package DAL;

import Controller.JPADBController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




public class tester {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        IDBConnector test = new JPADBController();
//        ApplicationContext ttt = new ClassPathXmlApplicationContext("springWriter.xml");
        
        test.updaeIncomings(UpdateTypeEnum.CoffeeHouse , 500, 200, 5);
        test.updaeIncomings(UpdateTypeEnum.Pump	, 123, 200, 5);

        
    }
    
}
