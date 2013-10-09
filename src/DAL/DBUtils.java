package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.mysql.jdbc.PreparedStatement;

public class DBUtils {
	private static DBUtils instace = null;
	private static Connection connection;
	private static PreparedStatement statement;
	private static String dbUrl;
//	public static enum updateType{CoffeeHouse,Pump};
	public DBUtils() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			dbUrl = "jdbc:mysql://localhost/gasstation";
			connection = DriverManager.getConnection(dbUrl, "root", "");

			System.out.println("Database connection established");
				
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBUtils getInstace(){
		if (instace == null){
			instace = new DBUtils();
		}
		
		return instace;
	}
	
	public void updaeIncomings(UpdateTypeEnum type, int carID,  float amount, int locationID){
		try {
			if (type == UpdateTypeEnum.Pump ) {
				statement = (PreparedStatement) connection.prepareStatement("INSERT INTO gasstation.pumps (carid,pumpid,amount,time) VALUES (?,?,?,?)");
			}
			else if (type == UpdateTypeEnum.CoffeeHouse) {
				statement = (PreparedStatement) connection.prepareStatement("INSERT INTO gasstation.coffeehouse (carid,cashierid,amount,time) VALUES (?,?,?,?)");
			}
			
			statement.setInt(1, carID);
			statement.setInt(2, locationID);
			statement.setInt(3,(int)amount);
			statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
public String getPumpsSum(){
	ResultSet result = null;
	try {
		statement = (PreparedStatement) connection.prepareStatement("SELECT SUM( amount ) FROM pumps");
		result = statement.executeQuery();
		result.next();
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		return (result.getString(1));
	} catch (SQLException e) {
		// TODO Auto-generated catch block
return null;	}
}
}

