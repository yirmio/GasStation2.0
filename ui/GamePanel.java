package surv.survivors.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import survivors.listeners.TribeListener;

public class GamePanel extends JPanel implements TribeListener {

	private JSplitPane tribesSplitter;
	private TribePanel tribe1, tribe2;

	public GamePanel(String tribe1Name, String tribe2Name) {
		setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = new Dimension();
		panelSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(panelSize);

		tribesSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		tribe1 = new TribePanel(tribe1Name);
		tribe2 = new TribePanel(tribe2Name);

		tribesSplitter.setLeftComponent(tribe1);
		tribesSplitter.setRightComponent(tribe2);

		tribesSplitter.setResizeWeight(0.5); // distributes the extra space when resizing.
											 // without it the proportions will not be saved during resize

		registerToTribesEvents();
		add(tribesSplitter, BorderLayout.CENTER);
	}

	public TribePanel getTribe1() {
		return tribe1;
	}

	public TribePanel getTribe2() {
		return tribe2;
	}

	private void moveSurvivorToOtherTribe(SurvivorPanel survivorPanel,
			TribePanel srcTribePanel) {
		if (tribe1 == srcTribePanel) {
			tribe1.removeSurvivor(survivorPanel);
			tribe2.addSurvivor(survivorPanel);
		} else {
			tribe2.removeSurvivor(survivorPanel);
			tribe1.addSurvivor(survivorPanel);
		}
	}

	private void registerToTribesEvents() {
		tribe1.addListener(this);
		tribe2.addListener(this);
	}

	@Override
	public void moveSurvivorTribeEvent(SurvivorPanel survivorPanel,
			TribePanel srcTribePanel) {
		moveSurvivorToOtherTribe(survivorPanel, srcTribePanel);
	}
}
