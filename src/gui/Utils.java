package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import BL.Main.GasStation;

public class Utils {
	public static final String IMAGES_FOLDER = "./../Images/";

	public static Image getImage(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}

		URL imageURL = Utils.class.getResource(name);
		if (imageURL == null) {
			return null;
		}

		return Toolkit.getDefaultToolkit().createImage(imageURL);
	}

	public static ImageIcon getImageIcon(String name) {
		Image image = getImage(IMAGES_FOLDER + name);
		if (image == null) {
			return null;
		}
		return new ImageIcon(image);
	}

	public static void closeApplication(JFrame parent, GasStation bLGasSTation) {
		int result = JOptionPane.showConfirmDialog(parent,
				"Do you want to wait until all cars get's service!", "GoodBye!",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			bLGasSTation.closeStation();
			System.exit(0);
		}
		else if (result == JOptionPane.NO_OPTION) {
			System.exit(0);

		}
	}

	public static void closeApplication(GasStationMenuPanel parent,
			GasStation blGasSTation) {
		int result = JOptionPane.showConfirmDialog(null,
				"Do you want to wait until all cars get's service!", "GoodBye?",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			blGasSTation.closeStation();
			System.exit(0);
		}
		else if (result == JOptionPane.NO_OPTION) {
			System.exit(0);

		}
	}
}
