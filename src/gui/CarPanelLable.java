package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BL.Main.Car;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridLayout;

@SuppressWarnings({ "serial", "unused" })
public class CarPanelLable extends JPanel {
	public static String CAR_IMAGE = "car64x64.png";
	public static String COFFEE_IMAGE = "DrinkCoffee64X64.png";
	public static String INLINE_IMAGE = "InLine64X64.png";
	public static String AROUND_IMAGE = "Around64X64.png";
	public static String PAY_IMAGE = "Pay64X64.jpg";
	public static String CITROEN = "citroen-c164.png";
	public static String VWBEATLE = "Vwbeatle64.png";
	public static String PEUGEOT107 = "peugeot10764.png";
	public static String MODERN = "modern64.png";
	public static String PAGANI = "pagani-zonda64.png";

	public static enum panelType {
		Allcars, Pump, Around, CashierLine, Pay, Drinking
	};

	private panelType thisPanelType;
	private JLabel carIcon;
	private JTextField txtCarStatus;
	private JButton btnAction;
	private FuelPumpPanel theFuelPumpPanel;
	private CoffeeHousePanel theCoffeeHousePanel;
	private Car theCar;
	private JTextField txtCarID;

	public CarPanelLable(Car theCar, panelType thePanelType) {
		super();
		this.theCar = theCar;
		this.thisPanelType = thePanelType;
		initLabelAndIcon(theCar);
		setLayout(new BorderLayout());
		add(carIcon, BorderLayout.CENTER);
		txtCarID = new JTextField("ID: "
				+ String.valueOf(this.theCar.getCarId()));
		txtCarID.setEditable(false);
		txtCarID.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtCarID, BorderLayout.NORTH);
		txtCarID.setColumns(5);
		setVisible(true);
	}

	private void initLabelAndIcon(Car c) {
		carIcon = new JLabel();
		carIcon.setLabelFor(carIcon);
		switch (this.thisPanelType) {
		case Around:
			carIcon.setIcon(Utils.getImageIcon(AROUND_IMAGE));
			break;
		case CashierLine:
			carIcon.setIcon(Utils.getImageIcon(INLINE_IMAGE));
			break;
		case Pay:
			carIcon.setIcon(Utils.getImageIcon(PAY_IMAGE));
			break;
		case Drinking:
			carIcon.setIcon(Utils.getImageIcon(COFFEE_IMAGE));
			break;
		default:
			initCarIcon(c);
			break;
		}
		carIcon.setHorizontalAlignment(SwingConstants.CENTER);
		carIcon.setBorder(BorderFactory.createEtchedBorder());
		carIcon.setHorizontalTextPosition(JLabel.CENTER);
		carIcon.setPreferredSize(new Dimension(70, 70));
		carIcon.repaint();
		carIcon.revalidate();
	}

	private void initCarIcon(Car c) {
		switch (c.getCarModel()) {
		case CitroenC1:
			carIcon.setIcon(Utils.getImageIcon(CITROEN));
			break;
		case Modern:
			carIcon.setIcon(Utils.getImageIcon(MODERN));
			break;
		case PaganiZonda:
			carIcon.setIcon(Utils.getImageIcon(PAGANI));
			break;
		case Peugeot107:
			carIcon.setIcon(Utils.getImageIcon(PEUGEOT107));
			break;
		case VWbeatle:
			carIcon.setIcon(Utils.getImageIcon(VWBEATLE));
			break;
		default:
			carIcon.setIcon(Utils.getImageIcon(CAR_IMAGE));
			break;
		}
	}

	public Car getTheCar() {
		return this.theCar;
	}
}
