package surv.survivors.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import survivors.actions.AddSurvivorAction;
import survivors.actions.CloseWindowAction;

public class GameMenu extends JMenuBar {
	
	private GameFrame mainFrame;
	
	public GameMenu(GameFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new CloseWindowAction(mainFrame));
		fileMenu.add(exitMenuItem);
		this.add(fileMenu);

		JMenu addSurvivor = new JMenu("Add");
		JMenuItem addToTribe1 = new JMenuItem("Add To Tribe 1");
		addToTribe1.addActionListener(new AddSurvivorAction(mainFrame.getMainPanel().getTribe1()));
		
		JMenuItem addToTribe2 = new JMenuItem("Add To Tribe 2");
		addToTribe2.addActionListener(new AddSurvivorAction(mainFrame.getMainPanel().getTribe2()));
		
		addSurvivor.add(addToTribe1);
		addSurvivor.add(addToTribe2);
		this.add(addSurvivor);
		
		
	}
}
