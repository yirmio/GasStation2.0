package gui;

import gui.CarPanelLable.panelType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import BL.Main.Car;
import ListenersInterfaces.PumpBLEventListener;

@SuppressWarnings("serial")
public class FuelPumpPanel extends JPanel {
	public static String FUEL_PUMP_IMAGE = "FuelPump64x64.jpg";
	private int numOfCars;
	private int pumpNo;
	private JPanel pnlDetailsAndRefueling;
	private JLabel numAndIcon;
	private JLabel carsQty;
	private JPanel pnlCarRefuel;
	private JPanel pnlCarsPumpLine;
	private Vector<CarPanelLable> allCarsInPumpLineVec;
	private JLabel lblCarInFuel;
	private Vector<PumpBLEventListener> allListeners;
	private JPanel pnlDetails;
	private CarPanelLable refuelingCar;
	private GasStationPanel gasStationUI;

	public FuelPumpPanel(int pumpNo, GasStationPanel gasStationPanel) {
		super();
		this.gasStationUI = gasStationPanel;
		this.pumpNo = pumpNo;
		this.allListeners = new Vector<PumpBLEventListener>();
		this.allCarsInPumpLineVec = new Vector<CarPanelLable>();
		numOfCars = 0;
		// Init main panel
		this.setBorder(new LineBorder(new Color(0, 191, 255)));
		// Init panel details - Embedded in main panel
		pnlDetailsAndRefueling = new JPanel();
		pnlDetailsAndRefueling.setSize(80, 100);
		initLableAndIcon(pumpNo);
		setLayout(new BorderLayout(0, 0));
		pnlDetailsAndRefueling.setLayout(new BorderLayout(0, 0));
		add(pnlDetailsAndRefueling, BorderLayout.WEST);

		// Init pnlCarRefuel - Embedded in main panel; show car refuelling now
		pnlCarRefuel = new JPanel();

		pnlDetailsAndRefueling.add(pnlCarRefuel);
		pnlCarRefuel.setBorder(BorderFactory.createEtchedBorder());
		pnlCarRefuel.setSize(80, 100);
		pnlCarRefuel.setLayout(new BorderLayout(0, 0));
		lblCarInFuel = new JLabel("          Car in Fuel Pump          ");
		lblCarInFuel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarInFuel.setAlignmentY(0.0f);
		pnlCarRefuel.add(lblCarInFuel, BorderLayout.NORTH);

		pnlDetails = new JPanel();
		pnlDetailsAndRefueling.add(pnlDetails, BorderLayout.WEST);
		pnlDetails.setLayout(new BoxLayout(pnlDetails, BoxLayout.Y_AXIS));
		numAndIcon = new JLabel();
		pnlDetails.add(numAndIcon);
		numAndIcon.setText("Fuel Pump " + pumpNo);
		numAndIcon.setIcon(Utils.getImageIcon(FUEL_PUMP_IMAGE));
		numAndIcon.setBorder(BorderFactory.createEtchedBorder());
		numAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		numAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		carsQty = new JLabel();
		carsQty.setVerticalAlignment(SwingConstants.BOTTOM);
		carsQty.setHorizontalAlignment(SwingConstants.TRAILING);
		pnlDetails.add(carsQty);
		carsQty.setText("Cars Qty:" + numOfCars);

		// Init pnlCarsPumpLine - Embedded in carsLineScrollPane;
		pnlCarsPumpLine = new JPanel();
		add(pnlCarsPumpLine, BorderLayout.CENTER);
		pnlCarsPumpLine.setBorder(BorderFactory.createEtchedBorder());
		pnlCarsPumpLine.setSize(240, 75);
		pnlCarsPumpLine.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		validate();
		repaint();
		setVisible(true);
	}

	public JPanel getPnlCarRefuel() {
		return pnlCarRefuel;
	}

	public int getPumpNo() {
		return pumpNo;
	}

	private void initLableAndIcon(int pumpNo) {
	}

	public void addCarToPumpLineInUI(Car carToAdd) {
		CarPanelLable carPnlToAdd = new CarPanelLable(carToAdd, panelType.Pump);
		carsQty.setText("Cars Qty:" + ++numOfCars);
		allCarsInPumpLineVec.add(carPnlToAdd);
		pnlCarsPumpLine.add(carPnlToAdd);
		repaint();
		validate();
	}

	public void removeCarFromPumpLineInUI(Car carToRemove) {
		CarPanelLable carPnlToRemove = findCarPanelInVector(carToRemove);
		if (carPnlToRemove != null) {
			carsQty.setText("Cars Qty:" + --numOfCars);
			pnlCarsPumpLine.remove(carPnlToRemove);
			allCarsInPumpLineVec.remove(carPnlToRemove);
			repaint();
			validate();
		}
	}

	public void addCarForRefuellingInUI(Car carToAdd) {

		this.refuelingCar = new CarPanelLable(carToAdd, panelType.Pump);
		this.gasStationUI.getCoffeeHousePanel().removeCarFromAllPlacesInUI(
				carToAdd);
		this.pnlCarRefuel.add(this.refuelingCar, BorderLayout.CENTER);

		repaint();
		validate();
	}

	public void removeCarFromRefuellingInUI() {

		this.pnlCarRefuel.remove(this.refuelingCar);
		repaint();
		revalidate();
	}

	public CarPanelLable findCarPanelInVector(Car carToFind) {
		for (CarPanelLable c : allCarsInPumpLineVec) {
			if (c.getTheCar() == carToFind) {
				return c;
			}
		}
		return null;
	}

	public void registerListener(PumpBLEventListener gasStationController) {
		this.allListeners.add(gasStationController);

	}
}