package de.hska.centurion;

import java.awt.EventQueue;

import javax.swing.UIManager;

import de.hska.centurion.gui.PlanungstoolGUI;

/**
 * Main class of the planning tool. It contains the main-method which sets the
 * system look and feel and opens the graphical user interface (
 * {@link PlanungstoolGUI}).
 * 
 * @see PlanungstoolGUI
 * 
 * @author Andreas Guentzel
 * 
 */
public class App {

	/**
	 * Launches the application.
	 * 
	 * @param args
	 *            arguments for i18n and other optional parameters
	 */
	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set system l&f as current l&f
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					// Create new instance of the gui
					PlanungstoolGUI gui = new PlanungstoolGUI(args);

					// Show the gui
					gui.getFrameMain().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					PlanungstoolGUI.showErrorDialog("Fehler beim Initialisieren der grafischen Oberfläche", e, null);
				}
			}
		});
	}
}
