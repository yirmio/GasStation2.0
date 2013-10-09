package surv.survivors.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import surv.survivors.actions.MoveSurvivorAction;
//import surv.utils.ImageUtils;

public class SurvivorPanel extends JPanel {

	public static String SURVIVOR_IMAGE = "./FuelTank64x64.jpg";
	
	private JLabel survivorNameAndIcon;
	private JButton btnMoveToOtherTribe;
	private TribePanel theTribePanel;
	
	
	private MoveSurvivorAction moveSurvivorAction;
	
	public SurvivorPanel(String name, TribePanel theTribePanel) {
		setTribePanel(theTribePanel);
		setLayout(new BorderLayout());
		initLabelAndIcon(name);
		add(survivorNameAndIcon, BorderLayout.CENTER);
		
		moveSurvivorAction = new MoveSurvivorAction(this);
		btnMoveToOtherTribe = new JButton(moveSurvivorAction);
		survivorNameAndIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// I want to respond to LEFT button DOUBLE CLICK
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
					moveSurvivorAction.actionPerformed(null); // or to cast e to ActionEvent
			}	
		});
		add(btnMoveToOtherTribe, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(100, 120));
	}
	
	public TribePanel getTribePanel() {
		return theTribePanel;
	}
	
	private void initLabelAndIcon(String name) {
		survivorNameAndIcon = new JLabel();
		survivorNameAndIcon.setText(name);
		survivorNameAndIcon.setIcon(ImageUtils.getImageIcon(SURVIVOR_IMAGE));
		survivorNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		survivorNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		survivorNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		survivorNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		survivorNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}
	
	public void setTribePanel(TribePanel theTribePanel) {
		this.theTribePanel = theTribePanel;
	}
}
