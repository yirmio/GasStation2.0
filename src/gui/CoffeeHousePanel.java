package gui;

import gui.CarPanelLable.panelType;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;
import BL.Main.Car;
import BL.Main.Cashier;
import java.awt.Component;
import java.util.Vector;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class CoffeeHousePanel extends JPanel {
	private JLabel lblCofeeHouseDetails;
	private JPanel pnlAround;
	private JScrollPane aroundScrollPane;
	private JPanel pnlAllCashiers;
	private JScrollPane allCashiersScrollPane;
	private JPanel pnlCashierLine;
	private JScrollPane cashierLineScrollPane;
	private JPanel pnlDrinking;
	private JScrollPane drinkingScrollPane;
	private JLabel lblCarsInAround;
	private JLabel lblCarsInCashier;
	private JLabel lblCarsThatDrinking;
	private JLabel lblAllCashiers;
	private Vector<CarPanelLable> carsInPnlCashierLineVec;
	private Vector<CarPanelLable> carsInPnlDrinkingVec;
	private Vector<CarPanelLable> carsInPnlAroundVec;
	private Vector<CashierPanel> cashierPnlVector;

	public CoffeeHousePanel() {
		super();
		this.carsInPnlCashierLineVec = new Vector<CarPanelLable>();
		this.carsInPnlAroundVec = new Vector<CarPanelLable>();
		this.carsInPnlDrinkingVec = new Vector<CarPanelLable>();
		this.cashierPnlVector = new Vector<CashierPanel>();

		// init pnlMainCoffeeHouse
		this.setPreferredSize(new Dimension(400, 400));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lblCofeeHouseDetails = new JLabel("Total from Coffee House: 0$");
		lblCofeeHouseDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lblCofeeHouseDetails);

		// Init pnlAround - Embedded in aroundScrollPane
		pnlAround = new JPanel();
		pnlAround.setBackground(new Color(255, 250, 205));
		pnlAround.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlAround.setSize(new Dimension(400, 70));

		// Init aroundScrollPane - Embedded in pnlMainCoffeeHouse
		aroundScrollPane = new JScrollPane(pnlAround);
		pnlAround.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		aroundScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		aroundScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.add(aroundScrollPane);
		lblCarsInAround = new JLabel("Shopping Area");
		lblCarsInAround.setHorizontalAlignment(SwingConstants.CENTER);
		aroundScrollPane.setColumnHeaderView(lblCarsInAround);

		// Init pnlAllCashiers - Embedded in allCashiersScrollPane
		pnlAllCashiers = new JPanel();
		pnlAllCashiers.setBackground(new Color(240, 248, 255));
		pnlAllCashiers.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		pnlAllCashiers.setSize(new Dimension(400, 100));

		// Init pnlCashierLine - Embedded in cashierLineScrollPane
		pnlCashierLine = new JPanel();
		pnlCashierLine.setBackground(new Color(127, 255, 212));
		pnlCashierLine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		pnlCashierLine.setSize(new Dimension(400, 70));

		// Init cashierLineScrollPane - Embedded in pnlMainCoffeeHouse
		cashierLineScrollPane = new JScrollPane(pnlCashierLine);
		cashierLineScrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		pnlCashierLine.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		cashierLineScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		cashierLineScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(cashierLineScrollPane);
		lblCarsInCashier = new JLabel("\rCashiers Line");
		lblCarsInCashier.setHorizontalAlignment(SwingConstants.CENTER);
		cashierLineScrollPane.setColumnHeaderView(lblCarsInCashier);

		// Init allCashiersScrollPane - Embedded in pnlMainCoffeeHouse
		allCashiersScrollPane = new JScrollPane(pnlAllCashiers);
		allCashiersScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnlAllCashiers
				.setLayout(new BoxLayout(pnlAllCashiers, BoxLayout.X_AXIS));
		allCashiersScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(allCashiersScrollPane);
		lblAllCashiers = new JLabel("All Cashiers");
		lblAllCashiers.setHorizontalAlignment(SwingConstants.CENTER);
		allCashiersScrollPane.setColumnHeaderView(lblAllCashiers);

		// Init pnlDrinking - Embedded in drinkingScrollPane
		pnlDrinking = new JPanel();
		pnlDrinking.setBackground(new Color(255, 228, 225));
		pnlDrinking
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlDrinking.setSize(new Dimension(400, 70));

		// Init drinkingScrollPane - Embedded in pnlMainCoffeeHouse
		drinkingScrollPane = new JScrollPane(pnlDrinking);
		pnlDrinking.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		drinkingScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		drinkingScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.add(drinkingScrollPane);
		lblCarsThatDrinking = new JLabel("Drinking Area");
		lblCarsThatDrinking.setHorizontalAlignment(SwingConstants.CENTER);
		drinkingScrollPane.setColumnHeaderView(lblCarsThatDrinking);
		setVisible(true);
		repaint();
		revalidate();
	}

	public void addCarToAroundPanelInUI(Car carToAdd) {
		CarPanelLable carPnlToAdd = new CarPanelLable(carToAdd,
				panelType.Around);
		carsInPnlAroundVec.add(carPnlToAdd);
		pnlAround.add(carPnlToAdd);
		repaint();
		revalidate();
	}

	public void removeCarFromAroundPanelInUI(Car carToRemove) {
		CarPanelLable carPnlToRemove = findCarPanelInAroundVector(carToRemove);
		if (carPnlToRemove != null) {
			pnlAround.remove(carPnlToRemove);
			carsInPnlAroundVec.remove(carPnlToRemove);
			repaint();
			revalidate();
		}
	}

	public CarPanelLable findCarPanelInAroundVector(Car carToFind) {
		for (CarPanelLable c : carsInPnlAroundVec) {
			if (c.getTheCar() == carToFind) {
				return c;
			}
		}
		return null;
	}

	public void addCarToDrinkingPanelInUI(Car carToAdd) {
		CarPanelLable carPnlToAdd = new CarPanelLable(carToAdd,
				panelType.Drinking);
		carsInPnlDrinkingVec.add(carPnlToAdd);
		pnlDrinking.add(carPnlToAdd);
		repaint();
		revalidate();
	}

	public void removeCarFromDrinkingPanelInUI(Car carToRemove) {
		CarPanelLable carPnlToRemove = findCarPanelInDrinkingVector(carToRemove);
		if (carPnlToRemove != null) {
			pnlDrinking.remove(carPnlToRemove);
			carsInPnlDrinkingVec.remove(carPnlToRemove);
			repaint();
			revalidate();
		}
	}

	public CarPanelLable findCarPanelInDrinkingVector(Car carToFind) {
		for (CarPanelLable c : carsInPnlDrinkingVec) {
			if (c.getTheCar() == carToFind) {
				return c;
			}
		}
		return null;
	}

	public void addCarToCashierLinePanelInUI(Car carToAdd) {
		CarPanelLable carPnlToAdd = new CarPanelLable(carToAdd,
				panelType.CashierLine);
		carsInPnlCashierLineVec.add(carPnlToAdd);
		pnlCashierLine.add(carPnlToAdd);

		repaint();
		revalidate();
	}

	public void removeCarFromAllPlacesInUI(Car carToRemove) {
		CarPanelLable carPnlToRemove = findCarPanelInCashierLineVector(carToRemove);
		if (carPnlToRemove != null) {
			pnlCashierLine.remove(carPnlToRemove);
			carsInPnlCashierLineVec.remove(carPnlToRemove);

		}
		CarPanelLable tmpCarPanel;
		tmpCarPanel = this.findCarPanelInAroundVector(carToRemove);
		if (tmpCarPanel != null) {
			this.removeCarFromAroundPanelInUI(carToRemove);
		}
		tmpCarPanel = this.findCarPanelInDrinkingVector(carToRemove);
		if (tmpCarPanel != null) {
			this.removeCarFromDrinkingPanelInUI(carToRemove);
		}
		repaint();
		revalidate();
	}

	public CarPanelLable findCarPanelInCashierLineVector(Car carToFind) {
		for (CarPanelLable c : carsInPnlCashierLineVec) {
			if (c.getTheCar() == carToFind) {
				return c;
			}
		}
		return null;
	}

	public void addCarToCashierPanelInUI(Car carToAdd, int cashierNo) {
		CarPanelLable carPnlToAdd = new CarPanelLable(carToAdd, panelType.Pay);
		CashierPanel cashierPanel = findCashierPanelInVector(cashierNo);

		if (cashierPanel != null) {
			cashierPanel.getPnlPayInCashier().add(carPnlToAdd,
					BorderLayout.CENTER);
			if (cashierPanel.getCarInPay() != null) {
				cashierPanel.getPnlPayInCashier().remove(
						cashierPanel.getCarInPay());
			}
			cashierPanel.setCarInPay(carPnlToAdd);
			revalidate();
		}
	}

	public void removeCarFromCashierPanelInUI(Car carToRemove, int cashierNo) {

		CashierPanel cashierPanel = findCashierPanelInVector(cashierNo);
		cashierPanel.getPnlPayInCashier().remove(cashierPanel.getCarInPay());
		repaint();
		validate();
	}

	public CashierPanel findCashierPanelInVector(int numOfCashier) {
		for (CashierPanel p : cashierPnlVector) {
			if (p.getCashierNo() == numOfCashier) {
				return p;
			}
		}
		return null;
	}

	public void addNewCashierToUI(Cashier newCashier) {
		CashierPanel p = new CashierPanel(newCashier);
		cashierPnlVector.add(p);

		pnlAllCashiers.add(p.getPnlPayInCashier(), BorderLayout.SOUTH);
		repaint();
		revalidate();
	}

	public void updateTotalMoney(float moneyToSet) {
		lblCofeeHouseDetails.setText("Total from Coffee House: " + moneyToSet
				+ "$");
	}
}
