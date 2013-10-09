package gui;

import gui.CarPanelLable.panelType;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import BL.Main.Car;

@SuppressWarnings("serial")
public class AllCarsPanel extends JPanel {
	private FuelPoolPanel pnlFuelPool;
	private Vector<CarPanelLable> allCars;

	public AllCarsPanel() throws SecurityException, IOException {
		this.allCars = new Vector<CarPanelLable>();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel upPanel = new JPanel();
		add(upPanel);
		this.setPreferredSize(new Dimension(140, 600));
		this.pnlFuelPool = new FuelPoolPanel();
		add(this.pnlFuelPool);
		validate();
		repaint();
	}



	public void insertCarToPanel(Car car) {
		CarPanelLable tmpCarPnl = new CarPanelLable(car, panelType.Allcars);
		this.allCars.add(tmpCarPnl);
		validate();
		repaint();
	}



	public FuelPoolPanel getFuelPoolPanel() {
		return this.pnlFuelPool;
	}

}
