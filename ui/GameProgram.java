package surv.survivors.ui;

import javax.swing.SwingUtilities;

public class GameProgram{

	public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame();
            }
        });
    }

}
