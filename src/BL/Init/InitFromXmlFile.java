package BL.Init;

import gui.MainFrame;
import BL.Exceptions.initExceptions;
import BL.Main.*;
import Controller.GasStationController;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/*
 * Name: InitFromXmlFile
 * description: Parse an XML file and stores it's content in a Station object
 * 
 */

public class InitFromXmlFile {

	public static enum CarActions {
		WantsCoffee, WantsFuel;
	}

	public InitFromXmlFile() {
	} // static class

	public static GasStation readFile(String filePath, MainFrame stationView)
			throws NumberFormatException, Exception, initExceptions {

		Document mxlDoc = getDocument(filePath); // get xml file

		// get GasStation
		Element gasStationElement = (Element) (mxlDoc.getDocumentElement()); // get
																				// root
																				// element

		// check theGasStation
		if (!gasStationElement.hasAttribute("name"))
			alertMissingAttribute(gasStationElement, "name");
		if (!gasStationElement.hasAttribute("numOfPumps"))
			alertMissingAttribute(gasStationElement, "numOfPumps");
		if (!gasStationElement.hasAttribute("pricePerLiter"))
			alertMissingAttribute(gasStationElement, "pricePerLiter");

		// get MainFuelPool
		Element fuelPoolElement = (Element) (gasStationElement
				.getElementsByTagName("MainFuelPool").item(0)); // get the first
																// occurrence of
																// MainFuelPool
		// check MainFuelPool
		if (fuelPoolElement == null)
			alertMissingNode(gasStationElement, "MainFuelPool");
		if (!fuelPoolElement.hasAttribute("maxCapacity"))
			alertMissingAttribute(fuelPoolElement, "maxCapacity");
		if (!fuelPoolElement.hasAttribute("currentCapacity"))
			alertMissingAttribute(fuelPoolElement, "currentCapacity");

		// get CoffeeHouse
		Element coffeeHouseElement = (Element) (gasStationElement
				.getElementsByTagName("CoffeeHouse").item(0)); // get the first
																// occurrence of
																// coffeeHouseElement
		// check CoffeeHouse
		if (coffeeHouseElement == null)
			alertMissingNode(gasStationElement, "CoffeeHouse");
		if (!coffeeHouseElement.hasAttribute("numOfCashier"))
			alertMissingAttribute(coffeeHouseElement, "numOfCashier");

		// create new FuelPool
		FuelPool newFuelPool = new FuelPool(Float.parseFloat(fuelPoolElement
				.getAttribute("maxCapacity")), Float.parseFloat(fuelPoolElement
				.getAttribute("currentCapacity")));

		// create new CoffeeHouse
		CoffeeHouse newCoffeeHouse = new CoffeeHouse(
				Integer.parseInt(coffeeHouseElement
						.getAttribute("numOfCashier")));

		// create new GasStation
		GasStation newGasStation = new GasStation(100,
				gasStationElement.getAttribute("name"),
				Float.parseFloat(gasStationElement
						.getAttribute("pricePerLiter")), newFuelPool,
				newCoffeeHouse);

		// adding pumps to station
		int numOfPumps = Integer.parseInt(gasStationElement
				.getAttribute("numOfPumps"));
		for (int i = 0; i < numOfPumps; i++) {
			newGasStation.addPump();
		}

		// Create GasStation Controller After all the cashiers and pump built
		// and ready to get the cars into them
		GasStationController stationController = new GasStationController(
				newGasStation, stationView.getGasStationPanel());
		// Add cashiers to UI
		for (Cashier cash : newCoffeeHouse.getCashiers()) {
			newCoffeeHouse.fireAddCashierToUI(cash);
		}
		// Add pump to UI
		for (Pump pump : newGasStation.getPumps()) {
			pump.fireAddPumpToUIEvent(pump.getPumpId());
		}
		// get Cars
		Element carsElement = (Element) (gasStationElement
				.getElementsByTagName("Cars").item(0)); // get the first
														// occurrence of Cars
		if (carsElement == null)
			alertMissingNode(gasStationElement, "Cars");

		// run through cars
		NodeList carsNodeList = carsElement.getElementsByTagName("Car"); // get
																			// list
																			// of
																			// Car
																			// (yes
																			// car
																			// not
																			// cars)
		for (int i = 0; i < carsNodeList.getLength(); i++) {
			Element carElement = (Element) (carsNodeList.item(i));

			int carId = Integer.parseInt(carElement.getAttribute("id"));
			Car newcar = new Car(carId);
			Pump pump = null;
			Car.coffeeHousePositionEnum coffeHouseLocation;
			float coffeHouseToPay = 0.0f;
			float gasNeeded = 0.0f;

			// run through car action
			NodeList carActions = carElement.getChildNodes(); // get all car
																// actions
			for (int j = 0; j < carActions.getLength(); j++) {

				if (carActions.item(j).getNodeType() == Node.ELEMENT_NODE) {

					Element actionElement = (Element) carActions.item(j);

					switch (CarActions.valueOf(actionElement.getNodeName())) {
					case WantsCoffee: {
						coffeHouseLocation = Car.coffeeHousePositionEnum
								.valueOf(actionElement
										.getAttribute("locationInCoffeeHouse"));
						coffeHouseToPay = Float.parseFloat(actionElement
								.getAttribute("amountToPay"));
						newcar.setWantsCoffee(newCoffeeHouse, coffeHouseToPay,
								coffeHouseLocation);
						// TODO - add new car to coffee house
						break;
					}
					case WantsFuel: {
						gasNeeded = Float.parseFloat(actionElement
								.getAttribute("numOfLiters"));
						pump = newGasStation
								.getPumpAt(Integer.parseInt(actionElement
										.getAttribute("pumpNum")));
						newcar.setWantFuel(pump, gasNeeded);
						// TODO - add newcar to fuelpump
//						newcar.getPump().fireAddCarToPumpLineInUIEvent(newcar,
//								newcar.getPump().getPumpId());
						// stationView.getMainPanel().getAllCarsPanel().registerListener(stationController);
						stationController.addCarToAllCarsFromBLEvent(newcar);
						break;
					}
					default: {
						break;
					}
					}
				}
			}

			newGasStation.enterGasStation(newcar);
		}

		return newGasStation;
	}

	private static void alertMissingAttribute(Element elm, String expected)
			throws initExceptions {
		throw new initExceptions("Invalid XML Format ! missing attribute "
				+ expected + " in " + elm.getNodeName());
	}

	private static void alertMissingNode(Element elm, String expected)
			throws initExceptions {
		throw new initExceptions("Invalid XML Format ! missing node "
				+ expected + " in " + elm.getNodeName());
	}

	private static Document getDocument(String docString) throws initExceptions {

		Document doc = null;
		try {
			// create a document reading rules
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);

			// read file using giving rules
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(docString));
		} catch (Exception e) {
			throw new initExceptions(
					"Unable to open the provided XML:\n file error: "
							+ e.getMessage());
		}
		return doc;
	}
}