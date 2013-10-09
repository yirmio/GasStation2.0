package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ListenersInterfaces.FuelPoolUIEventListener;

@SuppressWarnings("serial")
public class FuelPoolPanel extends JPanel {
	public static String FUEL_POOL_IMAGE = "FuelTank64x64.jpg";
	public static String FUEL_TRUCK_IMAGE = "TheFuelTank64x64.png";
	private List<FuelPoolUIEventListener> allListeners;
	private JLabel fuelPoolQtyAndIcon;
	private JButton cmdFill;
	private String currentQuantity;
	private String maxQuantity;
	private boolean iconChanged;

	// c'tor
	public FuelPoolPanel() {
		super();
		this.currentQuantity = "0";
		this.maxQuantity = "0";
		allListeners = new LinkedList<FuelPoolUIEventListener>();
		initLableAndIcon();
		setLayout(new BorderLayout());
		add(fuelPoolQtyAndIcon, BorderLayout.CENTER);
		cmdFill = new JButton("Fill Pool");
		cmdFill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireFillPoolPressed();

			}
		});
		add(cmdFill, BorderLayout.SOUTH);
	}

	private void initLableAndIcon() {
		fuelPoolQtyAndIcon = new JLabel();
		fuelPoolQtyAndIcon.setText("Fuel Quantity: " + this.maxQuantity + " / "
				+ this.maxQuantity);// will
		// show
		// 0
		// when
		// program
		// start
		fuelPoolQtyAndIcon.setIcon(Utils.getImageIcon(FUEL_POOL_IMAGE));
		this.iconChanged = false;
		fuelPoolQtyAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		fuelPoolQtyAndIcon.setBorder(BorderFactory.createEtchedBorder());
		fuelPoolQtyAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		fuelPoolQtyAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		fuelPoolQtyAndIcon.setSize(70, 80);
	}

	private void fireFillPoolPressed() {
		for (FuelPoolUIEventListener l : allListeners) {
			l.fillFuelPoolFromUIEvent(); // Initiate all listeners
		}
	}

	public void registerListener(FuelPoolUIEventListener listener) {
		allListeners.add(listener);
	}

	public void setCurrentQuantity(float qty, float max) {
		this.currentQuantity = String.format("%.0f", qty);
		this.maxQuantity = String.format("%.0f", max);
		this.fuelPoolQtyAndIcon.setText("Fuel Quantity: "
				+ this.currentQuantity + " / " + this.maxQuantity);
		this.fuelPoolQtyAndIcon.repaint();
		if (this.currentQuantity.equals(this.maxQuantity)) {
			this.cmdFill.setEnabled(false);
		} else {
			this.cmdFill.setEnabled(true);
		}
		this.cmdFill.revalidate();
		this.cmdFill.repaint();

	}

	public void changeIcon() {
		if (iconChanged) {
			this.fuelPoolQtyAndIcon
					.setIcon(Utils.getImageIcon(FUEL_POOL_IMAGE));
			this.iconChanged = false;
		} else {
			this.fuelPoolQtyAndIcon.setIcon(Utils
					.getImageIcon(FUEL_TRUCK_IMAGE));
			this.iconChanged = true;
		}
		repaint();
		revalidate();
	}

	public void setNewText(String string) {
		this.fuelPoolQtyAndIcon.setText(string);
		repaint();
		revalidate();
	}
}
