package de.hska.centurion;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.categories.CompletedOrders;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.Batch;
import de.hska.centurion.domain.input.components.CompletedOrder;
import de.hska.centurion.domain.input.components.CycleOrder;
import de.hska.centurion.domain.input.components.MissingPart;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceOrder;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;
import de.hska.centurion.gui.PlanungstoolGUI;
import de.hska.centurion.io.XmlParser;

/**
 *
 */
public class App {

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {

		// Set the look and feel to users OS LaF.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlanungstoolGUI gui = new PlanungstoolGUI(args);
					gui.getFrameMain().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
