package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import BL.Main.Cashier;

@SuppressWarnings("serial")
public class CashierPanel extends JPanel {
	private int cashierNo;
	private Cashier refCashier;
	private JPanel pnlPayInCashier;
	private CarPanelLable carInPay;

	public CashierPanel(Cashier newCashier) {
		super();
		this.refCashier = newCashier;
		this.cashierNo = newCashier.getId();
		pnlPayInCashier = new JPanel();
		pnlPayInCashier.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Cashier No. "
				+ this.cashierNo, TitledBorder.CENTER, TitledBorder.TOP, null,
				Color.RED));
		this.setSize(100, 100);
		setLayout(new BorderLayout(0, 0));
		add(pnlPayInCashier);
		pnlPayInCashier.setSize(75, 75);
		pnlPayInCashier.setLayout(new BorderLayout(0, 0));
		repaint();
		revalidate();
	}

	public JPanel getPnlPayInCashier() {
		return pnlPayInCashier;
	}

	public int getCashierNo() {
		return cashierNo;
	}

	public CarPanelLable getCarInPay() {
		return carInPay;
	}

	public void setCarInPay(CarPanelLable carInPay) {
		this.carInPay = carInPay;
		repaint();
		revalidate();
	}

	public Cashier getRefCashier() {
		return refCashier;
	}
}
