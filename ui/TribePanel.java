package surv.survivors.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import survivors.actions.AddSurvivorAction;
import survivors.listeners.TribeListener;

public class TribePanel extends JPanel {
	private String tribeName;

	private JButton btnAddSurvivor;
	private JPanel survivorsInnerPanel;
	private List<TribeListener> allListseners;
	
	public TribePanel(String tribeName) {
		allListseners = new ArrayList<TribeListener>();
		
		this.tribeName = tribeName;
		setLayout(new BorderLayout());
		
		setBorder(BorderFactory.createTitledBorder(tribeName));

		btnAddSurvivor = new JButton(new AddSurvivorAction(this));
		// OR:
		/*btnAddSurvivor = new JButton();
		btnAddSurvivor.setText("Add Survivor");
		btnAddSurvivor.addActionListener(new AddSurvivorAction(this));*/
		add(btnAddSurvivor, BorderLayout.NORTH);
		
		survivorsInnerPanel = new JPanel();
		survivorsInnerPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JScrollPane scroller = new JScrollPane(survivorsInnerPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller, BorderLayout.CENTER);
	}

	public void addSurvivor(String name) {
		survivorsInnerPanel.add(new SurvivorPanel(name, this));
		validate();
		repaint();
	}
	
	public void addSurvivor(SurvivorPanel theSurvivor) {
		survivorsInnerPanel.add(theSurvivor);
		theSurvivor.setTribePanel(this);
		validate();
		repaint();
	}
	
	public void removeSurvivor(SurvivorPanel theSurvivor) {
		survivorsInnerPanel.remove(theSurvivor);
		validate();
		repaint(); 
	}
	
	public void addListener(TribeListener newListener) {
		allListseners.add(newListener);
	}
	
	// this method is triggered from the SurvivorPanel
	public void moveSurvivorToOtherTribe(SurvivorPanel theSurvivorPanel) {
		fireMoveSurvivorTribeEvent(theSurvivorPanel);
	}
	
	private void fireMoveSurvivorTribeEvent(SurvivorPanel theSurvivorPanel) {
		for (TribeListener l : allListseners) {
			l.moveSurvivorTribeEvent(theSurvivorPanel, this);
		}
	}
}
