package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import ListenersInterfaces.GasStationUIEventListener;

@SuppressWarnings("serial")
public class GasStationMenuPanel extends JPanel {
	private JMenu FileMenu;
	private JFrame parentFrame;
	private List<GasStationUIEventListener> carUIListener;

	public List<GasStationUIEventListener> getCarUIListener() {
		return carUIListener;
	}

	public GasStationMenuPanel(List<GasStationUIEventListener> allListeners) {
		this.carUIListener = allListeners;

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JMenuBar menuBar = new JMenuBar();
		add(menuBar);

		FileMenu = new JMenu("File");
		FileMenu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(FileMenu);

		JMenuItem ExitMenuItem = new JMenuItem("Exit");
		ExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Exit from menu!");

				Utils.closeApplication(GasStationMenuPanel.this,
						((MainFrame) parentFrame).getBLGasSTation());
			}
		});

		ExitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (SwingUtilities.isLeftMouseButton(arg0)) {
					System.out.println("Exit from menu!");
					Utils.closeApplication(parentFrame,
							((MainFrame) parentFrame).getBLGasSTation());
				}
			}
		});

		JMenuItem AddCarMenuItem = new JMenuItem("Add New Car");
		AddCarMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new JFrame("New Car");
				NewCarPanel newCarPanel = new NewCarPanel(frame);
				// Register all menu CarListeners to the new window
				for (GasStationUIEventListener lstnr : GasStationMenuPanel.this.carUIListener) {
					newCarPanel.registerListener(lstnr);
				}
				frame.setUndecorated(true);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setSize(180, 370);
				frame.getContentPane().add(newCarPanel);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		FileMenu.add(AddCarMenuItem);
		FileMenu.add(ExitMenuItem);

	}

	public void setParentFrame(JFrame parent) {
		this.parentFrame = parent;

	}

}
