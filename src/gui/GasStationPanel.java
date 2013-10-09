package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import BL.Main.GasStation;
import Controller.GasStationController;
import ListenersInterfaces.GasStationUIEventListener;

@SuppressWarnings("serial")
public class GasStationPanel extends JPanel {
	private AllCarsPanel allCarsPanel;
	private FuelPumpListPanel fuelPumpsListPanel;
	private CoffeeHousePanel coffeeHousePanel;
	private JFrame parent;
	private GasStation BLGasStation;

	public GasStation getBLGasStation() {
		return BLGasStation;
	}

	public void setBLGasStation(GasStation bLGasStation) {
		BLGasStation = bLGasStation;
	}

	private List<GasStationUIEventListener> allListeners;
	private GasStationMenuPanel gasStationMenuPanel;
	public AllCarsPanel getAllCarsPanel() {
		return allCarsPanel;
	}

	public FuelPumpListPanel getFuelPumpsListPanel() {
		return this.fuelPumpsListPanel;
	}

	public CoffeeHousePanel getCoffeeHousePanel() {
		return this.coffeeHousePanel;
	}

	public GasStationPanel(String allCarsStr, String fuelPumpsListStr,
			String coffeeHousePanelStr, MainFrame mainFrame)
			throws SecurityException, IOException {
		this.parent = mainFrame;
		// Init Listeners List
		this.allListeners = new ArrayList<GasStationUIEventListener>();

		this.setPreferredSize(new Dimension(1200, 600));
		setLayout(new BorderLayout(0, 0));

		allCarsPanel = new AllCarsPanel();
		add(allCarsPanel, BorderLayout.WEST);

		this.coffeeHousePanel = new CoffeeHousePanel();
		add(this.coffeeHousePanel, BorderLayout.EAST);

		this.fuelPumpsListPanel = new FuelPumpListPanel(this);
		add(this.fuelPumpsListPanel, BorderLayout.CENTER);

		this.gasStationMenuPanel = new GasStationMenuPanel(allListeners);
		this.gasStationMenuPanel.setParentFrame(this.parent);
		add(gasStationMenuPanel, BorderLayout.NORTH);

		
	}

	public void registerListener(GasStationController gasStationController) {
		this.allListeners.add(gasStationController);
	}
}
