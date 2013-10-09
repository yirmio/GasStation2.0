package surv.survivors.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import utils.CloseJFrameUtil;

public class GameFrame extends JFrame  {

	private GamePanel mainPanel;

	public GameFrame() {
		try {
			// in order to have Windows Look and Feel
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setTitle("Survivors Game"); // set the frame's title
		
		// set the frame's Close operation
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CloseJFrameUtil.closeApplication(GameFrame.this);
				// NOTE: when we want the 'Class' of the outer class, 'this' doesn't work.
				// Should use <OuterClass>.this
			}
		});

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension();
		frameSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(frameSize);
		
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new GamePanel("Tribe 1", "Tribe 2");
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		setJMenuBar(new GameMenu(this));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public GamePanel getMainPanel() {
		return mainPanel;
	}
}
