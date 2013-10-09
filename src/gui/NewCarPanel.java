package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import BL.Main.Car;
import BL.Main.Car.CAR_MODEL;
import ListenersInterfaces.GasStationUIEventListener;

@SuppressWarnings("serial")
public class NewCarPanel extends JPanel {

	private JTextField txtID;
	private JCheckBox chckbxWantsFuel;
	private JCheckBox chckbxWantsCoffee;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbxCarModel;
	private JLabel lblCarModelIcon;
	private JPanel pnlMain;
	private JTextField txtAmountToPay;
	private JTextField txtLiters;
	private JLabel lblFuelliters;
	private JLabel lblAmountToPay;
	private Car.CAR_MODEL choosenModel;

	private Vector<GasStationUIEventListener> listeners;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NewCarPanel(final JFrame parent) {
		this.listeners = new Vector<GasStationUIEventListener>();

		setToolTipText("");
		setBorder(new TitledBorder(null, "Add New Car Menu",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		setOpaque(false);

		pnlMain = new JPanel();
		pnlMain.setBorder(new TitledBorder(null, "Properties",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMain.setBounds(22, 27, 139, 300);
		add(pnlMain);
		pnlMain.setLayout(null);

		JLabel lblCarId = new JLabel("Car ID:");
		lblCarId.setBounds(6, 19, 46, 14);
		pnlMain.add(lblCarId);
		lblCarId.setHorizontalAlignment(SwingConstants.LEFT);

		txtID = new JTextField();
		txtID.setBounds(47, 16, 86, 20);
		pnlMain.add(txtID);
		txtID.setColumns(10);

		chckbxWantsFuel = new JCheckBox("WantsFuel");
		chckbxWantsFuel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					txtLiters.setVisible(true);
					lblFuelliters.setVisible(true);

				} else {
					txtLiters.setVisible(false);
					lblFuelliters.setVisible(false);
				}
				repaint();
				revalidate();
			}
		});
		chckbxWantsFuel.setBounds(6, 40, 97, 23);
		pnlMain.add(chckbxWantsFuel);
		chckbxWantsFuel.setHorizontalAlignment(SwingConstants.LEFT);

		chckbxWantsCoffee = new JCheckBox("WantsCoffee");
		chckbxWantsCoffee.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					txtAmountToPay.setVisible(true);
					lblAmountToPay.setVisible(true);

				} else {
					txtAmountToPay.setVisible(false);
					lblAmountToPay.setVisible(false);
				}
				repaint();
				revalidate();
			}
		});
		chckbxWantsCoffee.setBounds(6, 90, 97, 23);
		pnlMain.add(chckbxWantsCoffee);
		chckbxWantsCoffee.setHorizontalAlignment(SwingConstants.LEFT);

		cmbxCarModel = new JComboBox();
		cmbxCarModel.setBounds(6, 185, 127, 20);
		pnlMain.add(cmbxCarModel);

		cmbxCarModel.setModel(new DefaultComboBoxModel(CAR_MODEL.values()));
		cmbxCarModel.setSelectedIndex(0);
		lblCarModelIcon = new JLabel("");
		lblCarModelIcon.setBounds(23, 209, 80, 80);
		pnlMain.add(lblCarModelIcon);
		lblCarModelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarModelIcon.setIcon(new ImageIcon(NewCarPanel.class
				.getResource("/Images/Vwbeatle64.png")));

		JLabel lblChooseCarModel = new JLabel("Choose Car Model:");
		lblChooseCarModel.setBounds(6, 163, 127, 14);
		pnlMain.add(lblChooseCarModel);

		lblFuelliters = new JLabel("Fuel (liters):");
		lblFuelliters.setBounds(6, 69, 58, 14);
		lblFuelliters.setVisible(false);
		pnlMain.add(lblFuelliters);

		txtLiters = new JTextField();
		txtLiters.setBounds(74, 66, 59, 20);
		txtLiters.setVisible(false);
		pnlMain.add(txtLiters);
		txtLiters.setColumns(10);

		txtAmountToPay = new JTextField();
		txtAmountToPay.setBounds(80, 120, 50, 20);
		txtAmountToPay.setVisible(false);
		pnlMain.add(txtAmountToPay);
		txtAmountToPay.setColumns(10);

		lblAmountToPay = new JLabel("Amount to pay:");
		lblAmountToPay.setBounds(6, 123, 75, 14);
		lblAmountToPay.setVisible(false);
		pnlMain.add(lblAmountToPay);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tmpID = 0;
				int amountToPay = 0;
				int litters = 0;
				// No fuel or coffee is selected
				if (!chckbxWantsCoffee.isSelected()
						&& !chckbxWantsFuel.isSelected()) {
					JOptionPane.showMessageDialog(null,
							"You must choose Refueling OR Drinking Coffee!",
							"Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					tmpID = Integer.parseInt(txtID.getText());
					if (chckbxWantsCoffee.isSelected()
							&& txtAmountToPay.getText().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Insert amount to pay!", "Error!",
								JOptionPane.ERROR_MESSAGE);
						txtAmountToPay.requestFocusInWindow();
					} else if (chckbxWantsCoffee.isSelected()
							&& !txtAmountToPay.getText().equals("")) {
						amountToPay = Integer.parseInt(txtAmountToPay.getText());
					}

					if (chckbxWantsFuel.isSelected()
							&& txtLiters.getText().equals("")) {

						JOptionPane.showMessageDialog(null,
								"Insert tank size!", "Error!",
								JOptionPane.ERROR_MESSAGE);
					} else if (chckbxWantsFuel.isSelected()
							&& !txtLiters.getText().equals("")) {
						litters = Integer.parseInt(txtLiters.getText());
					}
					fireAddNewCar(tmpID, amountToPay, litters);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digits Only!",
							"Error!", JOptionPane.ERROR_MESSAGE);
				} finally {
					parent.setVisible(false);
				}

			}
		});
		btnOk.setBounds(22, 338, 47, 23);
		add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.setVisible(false);
			}
		});
		btnCancel.setBounds(96, 338, 65, 23);
		add(btnCancel);
		cmbxCarModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (cmbxCarModel.getSelectedIndex()) {
				case 0: // VWbeatle
					choosenModel = Car.CAR_MODEL.VWbeatle;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.VWBEATLE));
					break;
				case 1: // Peugeot107
					choosenModel = Car.CAR_MODEL.Peugeot107;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.PEUGEOT107));
					break;
				case 2: // CitroenC1
					choosenModel = Car.CAR_MODEL.CitroenC1;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.CITROEN));
					break;
				case 3: // Modern
					choosenModel = Car.CAR_MODEL.Modern;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.MODERN));
					break;
				case 4:// PaganiZonda
					choosenModel = Car.CAR_MODEL.PaganiZonda;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.PAGANI));
					break;
				default:
					choosenModel = Car.CAR_MODEL.DefaultCar;
					lblCarModelIcon.setIcon(Utils
							.getImageIcon(CarPanelLable.CAR_IMAGE));
					break;
				}
				revalidate();
				repaint();

			}
		});
	}

	public void registerListener(GasStationUIEventListener lstnr) {
		this.listeners.add(lstnr);

	}

	private void fireAddNewCar(int tmpID, int amountToPay, int litters) {
		for (GasStationUIEventListener carLstnr : this.listeners) {
			carLstnr.addNewCarFromUIEvent(tmpID, amountToPay, litters,
					choosenModel);
		}
	}
}
