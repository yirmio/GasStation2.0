package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import ListenersInterfaces.PumpListBLEventListener;

@SuppressWarnings("serial")
public class FuelPumpListPanel extends JPanel {

	private JPanel pnlPumps;
	private JLabel lblDetails;
	private JScrollPane pumpsScrollPane;
	private Vector<FuelPumpPanel> fuelPumpPanelVector;
	private Vector<PumpListBLEventListener> allListeners;
	private float totalMoneyFromPumps;
	private GasStationPanel gasStationPanel;

	public FuelPumpListPanel(GasStationPanel gasStationPanel) {
		super();
		this.gasStationPanel = gasStationPanel;
		this.fuelPumpPanelVector = new Vector<FuelPumpPanel>();
		this.allListeners = new Vector<PumpListBLEventListener>();
		this.setPreferredSize(new Dimension(400, 600));
		setLayout(new BorderLayout(0, 0));
		totalMoneyFromPumps = 0;
		lblDetails = new JLabel("Total from All Pumps: " + totalMoneyFromPumps
				+ "$");
		lblDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lblDetails, BorderLayout.NORTH);
		pnlPumps = new JPanel();
		pnlPumps.setBackground(new Color(255, 250, 205));
		pnlPumps.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlPumps.setSize(200, 450);
		pnlPumps.setLayout(new BoxLayout(pnlPumps, BoxLayout.Y_AXIS));
		pumpsScrollPane = new JScrollPane(pnlPumps);
		pumpsScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pumpsScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(pumpsScrollPane);
		validate();
		repaint();
		setVisible(true);
	}

	public void addPumpsToListInUI(int pumpNo) {
		FuelPumpPanel p = new FuelPumpPanel(pumpNo, this.gasStationPanel);
		fuelPumpPanelVector.add(p);
		pnlPumps.add(p, BorderLayout.SOUTH);
		repaint();
		validate();
		setVisible(true);
	}

	public FuelPumpPanel findPumpPanelInVector(int numOfPump) {
		for (FuelPumpPanel p : fuelPumpPanelVector) {
			if (p.getPumpNo() == numOfPump) {
				return p;
			}
		}
		return null;
	}

	public void setTotalMoney(float setMoney) {
		lblDetails.setToolTipText("Total:" + setMoney);
	}

	public Vector<FuelPumpPanel> getAllPumpsPanels() {
		return this.fuelPumpPanelVector;
	}

	public void registerListener(PumpListBLEventListener gasStationController) {
		this.allListeners.add(gasStationController);
	}

	public void updateTotalMoney(float moneyToSet) {
		totalMoneyFromPumps += moneyToSet;
		this.lblDetails.setText("Total from all Fuel Pumps: "
				+ totalMoneyFromPumps + "$");
	}
}
