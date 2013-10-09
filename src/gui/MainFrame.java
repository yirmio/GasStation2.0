package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import BL.Main.GasStation;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private GasStationPanel pnlMainGasStation;
	private GasStation BLGasSTation;

	public GasStation getBLGasSTation() {
		return BLGasSTation;
	}

	public void setBLGasSTation(GasStation bLGasSTation) {
		this.BLGasSTation = bLGasSTation;
		this.pnlMainGasStation.setBLGasStation(bLGasSTation);
	}

	public MainFrame() throws SecurityException, IOException {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("GasStation");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Utils.closeApplication(MainFrame.this, BLGasSTation);
			}
		});

		// Adding GasStationPanel
		getContentPane().setLayout(new BorderLayout());
		pnlMainGasStation = new GasStationPanel("all cars", "pumps", "coffee",
				this);
		pnlMainGasStation.setBorder(null);
		getContentPane().add(pnlMainGasStation, BorderLayout.CENTER);
		setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.75),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.95));
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public GasStationPanel getGasStationPanel() {
		return pnlMainGasStation;
	}

}
