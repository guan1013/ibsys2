package de.hska.centurion.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JInternalFrame;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.IdleTimeCostsSum;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;
import de.hska.centurion.io.XmlInputParser;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;

public class PlanungstoolGUI {

	private JFrame frmIbsysiiplanungstoolGruppe;
	private JTable tableWarehouse;
	private JTable tableInwardStockMovement;
	private JTable tableFutureInwardStockMovement;
	private JTable tableIdleTimeCosts;
	private JTable tableWaitingListWorkstations;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlanungstoolGUI window = new PlanungstoolGUI();
					window.frmIbsysiiplanungstoolGruppe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlanungstoolGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIbsysiiplanungstoolGruppe = new JFrame();
		frmIbsysiiplanungstoolGruppe
				.setIconImage(Toolkit
						.getDefaultToolkit()
						.getImage(
								PlanungstoolGUI.class
										.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmIbsysiiplanungstoolGruppe.setResizable(false);
		frmIbsysiiplanungstoolGruppe
				.setTitle("IBSYS-II-Planungstool - Gruppe Centurion - SS 2015");
		frmIbsysiiplanungstoolGruppe.setBounds(100, 100, 799, 738);
		frmIbsysiiplanungstoolGruppe
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmIbsysiiplanungstoolGruppe.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Datei");
		menuBar.add(mnFile);

		final JInternalFrame internalFrame = new JInternalFrame("Ergebnisse");
		internalFrame
				.setFrameIcon(new ImageIcon(
						PlanungstoolGUI.class
								.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif")));
		internalFrame.setBounds(10, 11, 774, 286);
		frmIbsysiiplanungstoolGruppe.getContentPane().add(internalFrame);
		internalFrame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 738, 235);
		internalFrame.getContentPane().add(tabbedPane);

		JPanel panelWarehouse = new JPanel();
		tabbedPane.addTab("Akt. Lagerbestand", null, panelWarehouse, null);
		panelWarehouse.setLayout(null);

		JScrollPane scrollPaneWarehouse = new JScrollPane();
		scrollPaneWarehouse.setBounds(0, 22, 733, 185);
		panelWarehouse.add(scrollPaneWarehouse);

		tableWarehouse = new JTable();
		tableWarehouse
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPaneWarehouse.setViewportView(tableWarehouse);
		tableWarehouse.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Artikel", "Menge", "Startmenge",
						"Menge/Startmenge", "Preis", "Lagerwert" }));

		final JLabel labelTotalStockValue = new JLabel(
				"Keine Ergebnis-Datei geladen");
		labelTotalStockValue.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalStockValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTotalStockValue.setBounds(10, 0, 713, 23);
		panelWarehouse.add(labelTotalStockValue);

		JPanel panelInwardStockMovement = new JPanel();
		tabbedPane.addTab("Akt. Lagerzugang Bestellungen", null,
				panelInwardStockMovement, null);
		panelInwardStockMovement.setLayout(null);

		JScrollPane scrollPaneInwardStockMovement = new JScrollPane();
		scrollPaneInwardStockMovement.setBounds(0, 0, 733, 207);
		panelInwardStockMovement.add(scrollPaneInwardStockMovement);

		tableInwardStockMovement = new JTable();
		scrollPaneInwardStockMovement.setViewportView(tableInwardStockMovement);
		tableInwardStockMovement.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "Auftragsnr.", "Modus",
						"Artikel", "Menge", "Beendet (P-T-S-M)", "Materialk.",
						"Bestellk.", "Gesamtk.", "St\u00FCckk." }));
		tableInwardStockMovement.getColumnModel().getColumn(0)
				.setResizable(false);
		tableInwardStockMovement.getColumnModel().getColumn(0)
				.setPreferredWidth(103);
		tableInwardStockMovement.getColumnModel().getColumn(1)
				.setResizable(false);
		tableInwardStockMovement.getColumnModel().getColumn(4)
				.setPreferredWidth(106);
		tableInwardStockMovement.getColumnModel().getColumn(5)
				.setPreferredWidth(91);

		JPanel panelFutureInwardStockMovement = new JPanel();
		tabbedPane.addTab("Ausstehende Bestellungen", null,
				panelFutureInwardStockMovement, null);
		panelFutureInwardStockMovement.setLayout(null);

		JScrollPane scrollPaneFutureInwardStockMovement = new JScrollPane();
		scrollPaneFutureInwardStockMovement.setBounds(0, 0, 733, 207);
		panelFutureInwardStockMovement.add(scrollPaneFutureInwardStockMovement);

		tableFutureInwardStockMovement = new JTable();
		scrollPaneFutureInwardStockMovement
				.setViewportView(tableFutureInwardStockMovement);
		tableFutureInwardStockMovement.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "Auftragsnr.", "Modus",
						"Artikel", "Menge", "Lagerzugang",
						"Lagerzugang (Abweichung)" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { true, true, true, true,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		JPanel panelIdleTimeCosts = new JPanel();
		tabbedPane.addTab("Leerzeitenkosten", null, panelIdleTimeCosts, null);
		panelIdleTimeCosts.setLayout(null);

		JScrollPane scrollPaneIdleTimeCosts = new JScrollPane();
		scrollPaneIdleTimeCosts.setBounds(0, 0, 733, 207);
		panelIdleTimeCosts.add(scrollPaneIdleTimeCosts);

		tableIdleTimeCosts = new JTable();
		scrollPaneIdleTimeCosts.setViewportView(tableIdleTimeCosts);
		tableIdleTimeCosts.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Arbeitsplatz", "R\u00FCstvorg\u00E4nge",
						"Leerzeit (min)", "Lohnleerkosten", "Lohnkosten",
						"Masch.Stillstandskosten" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		JPanel panelWaitingListWorkstations = new JPanel();
		tabbedPane.addTab("Warteliste Arbeitsplatz", null,
				panelWaitingListWorkstations, null);
		panelWaitingListWorkstations.setLayout(null);

		JScrollPane scrollPaneWorklingListWorkstations = new JScrollPane();
		scrollPaneWorklingListWorkstations.setBounds(0, 0, 733, 196);
		panelWaitingListWorkstations.add(scrollPaneWorklingListWorkstations);

		tableWaitingListWorkstations = new JTable();
		scrollPaneWorklingListWorkstations
				.setViewportView(tableWaitingListWorkstations);
		tableWaitingListWorkstations.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "Arbeitsplatz", "Periode",
						"Fertigungsauftrag", "Los", "Teil", "Menge",
						"Zeitbedarf" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5567778224840912024L;
			boolean[] columnEditables = new boolean[] { false, true, false,
					false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JPanel panelWaitingListStock = new JPanel();
		tabbedPane.addTab("Warteliste Material", null, panelWaitingListStock, null);
		panelWaitingListStock.setLayout(null);
		
		table = new JTable();
		table.setBounds(0, 0, 733, 196);
		panelWaitingListStock.add(table);
		tableWaitingListWorkstations.getColumnModel().getColumn(0)
				.setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(1)
				.setPreferredWidth(51);
		tableWaitingListWorkstations.getColumnModel().getColumn(2)
				.setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(2)
				.setPreferredWidth(103);
		tableWaitingListWorkstations.getColumnModel().getColumn(3)
				.setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(3)
				.setPreferredWidth(50);
		tableWaitingListWorkstations.getColumnModel().getColumn(4)
				.setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(4)
				.setPreferredWidth(44);
		tableWaitingListWorkstations.getColumnModel().getColumn(5)
				.setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(5)
				.setPreferredWidth(54);
		tableWaitingListWorkstations.getColumnModel().getColumn(6)
				.setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(0).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(1).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(2).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(3).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(4).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(5).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(5).setPreferredWidth(128);
		tableFutureInwardStockMovement.getColumnModel().getColumn(0)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(1)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(2)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(3)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(4)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(5)
				.setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(5)
				.setMinWidth(26);
		internalFrame.setVisible(true);

		JMenuItem mntmLoadResultXml = new JMenuItem("XML-Datei laden");
		mntmLoadResultXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();

				// Zeige Dialog zum Auswählen einer Datei
				int returnVal = fc.showOpenDialog(frmIbsysiiplanungstoolGruppe);

				// Warten bis der Benutzer eine Datei ausgewählt hat
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					// Ausgewählte Datei abfragen
					File file = fc.getSelectedFile();

					Results results = null;

					try {

						// Datei als Result-Objekt parsen
						results = XmlInputParser.parseXmlFile(file.getPath());
					} catch (Exception ex) {
						results = null;
						JOptionPane.showMessageDialog(
								frmIbsysiiplanungstoolGruppe, ex.getMessage());
						return;
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AUSGABE / ANZEIGE ERGEBNISSE

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// ALLGEMEINES

					// Titel des Ergebnis-Frames setzen
					internalFrame.setTitle("Ergebnisse - Periode "
							+ results.getPeriod() + " | Gruppe "
							+ results.getGroup());

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// LAGERHAUS

					// Gesamtwert in Label anzeigen
					labelTotalStockValue.setText("Gesamtwert: "
							+ results.getWarehouseStock()
									.getTotalStockValueString());

					// Bisherige angezeigte Daten entfernen
					DefaultTableModel model = (DefaultTableModel) tableWarehouse
							.getModel();
					removeAllRowsFromTable(model);

					// Alle Artikel im Lagerhaus auflisten
					List<Article> articles = results.getWarehouseStock()
							.getArticles();

					for (Article article : articles) {
						model.addRow(new Object[] { article.getId(),
								article.getAmount(), article.getStartAmount(),
								article.getPctString(),
								article.getPriceString(),
								article.getStockValueString() });
					}

					// Clean up
					articles = null;
					model = null;

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AKTUELLER LAGERZUGANG BESTELLUNGEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableInwardStockMovement
							.getModel();
					removeAllRowsFromTable(model);

					// Alle angekommenen Bestellungen auflisten
					List<Order> orders = results.getInwardStockMovement()
							.getOrders();

					for (Order order : orders) {
						model.addRow(new Object[] {
								new String(order.getOrderPeriod() + "-"
										+ order.getId()),
								getModeAsString(order.getMode()),
								order.getArticle(), order.getAmount(), "???",
								order.getMaterialCostsStr(),
								order.getOrderCostsStr(),
								order.getEntireCostsStr(),
								order.getPieceCostsStr() });
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AUSSTEHENDE BESTELLUNGEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableFutureInwardStockMovement
							.getModel();
					removeAllRowsFromTable(model);

					// Alle ausstehenden Bestellungen auflisten
					orders = results.getFutureInwardStockMovement().getOrders();

					for (Order order : orders) {
						model.addRow(new Object[] {
								new String(order.getOrderPeriod() + "-"
										+ order.getId()), order.getMode(),
								order.getArticle(), order.getAmount(), "???",
								"???" });
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// LEERZEITEN KOSTEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableIdleTimeCosts.getModel();
					removeAllRowsFromTable(model);

					// Alle Leerzeiten Kosten auflisten
					List<WorkplaceCosts> workplaceCosts = results
							.getIdleTimeCosts().getWorkplaces();

					for (WorkplaceCosts wC : workplaceCosts) {
						model.addRow(new Object[] { wC.getId(),
								wC.getSetupEvents(), wC.getIdleTime(),
								wC.getWageIdleTimeCostsStr(),
								wC.getWageCostsStr(),
								wC.getMachineIdleTimeCostsStr() });
					}

					// Summe ausgeben
					IdleTimeCostsSum sum = results.getIdleTimeCosts().getSum();
					model.addRow(new Object[] { "SUMME", sum.getSetupEvents(),
							sum.getIdleTime(), sum.getWageIdleTimeCostsStr(),
							sum.getWageCostsStr(),
							sum.getMachineIdleTimeCostsStr() });

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// WARTELISTE ARBEITSPLATZ

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableWaitingListWorkstations
							.getModel();
					removeAllRowsFromTable(model);

					// Alle Wartelisten anzeigen
					List<WorkplaceWaiting> workplacesWaiting = results
							.getWaitingListWorkstations().getWorkplaces();
					for (WorkplaceWaiting wW : workplacesWaiting) {

						if (wW.getWaitingList() != null) {

							model.addRow(new Object[] {
									wW.getId(),
									wW.getWaitingList().getPeriod(),
									wW.getWaitingList().getOrder(),
									wW.getWaitingList().getFirstBatch()
											+ "-"
											+ wW.getWaitingList()
													.getLastBatch(),
									wW.getWaitingList().getItem(),
									wW.getWaitingList().getAmount(),
									wW.getTimeNeed() });
						} else {

							model.addRow(new Object[] { wW.getId() });
						}
					}

				}
			}
		});
		mnFile.add(mntmLoadResultXml);
		frmIbsysiiplanungstoolGruppe.getContentPane().setLayout(null);

		JInternalFrame internalFrame_1 = new JInternalFrame("Planung");
		internalFrame_1.setBounds(10, 305, 774, 371);
		frmIbsysiiplanungstoolGruppe.getContentPane().add(internalFrame_1);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		internalFrame_1.getContentPane().add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Schritt 1", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblSchrittPrognosen = new JLabel("Schritt 1: Prognose");
		lblSchrittPrognosen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittPrognosen.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittPrognosen.setBounds(10, 11, 733, 23);
		panel.add(lblSchrittPrognosen);
		
		JLabel lblPkinderfahrrad = new JLabel("P1 (Kinderfahrrad)");
		lblPkinderfahrrad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPkinderfahrrad.setBounds(10, 100, 141, 23);
		panel.add(lblPkinderfahrrad);
		
		JLabel lblPdamenfahrrad = new JLabel("P2 (Damenfahrrad)");
		lblPdamenfahrrad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPdamenfahrrad.setBounds(10, 160, 141, 23);
		panel.add(lblPdamenfahrrad);
		
		JLabel lblPherrenfahrrad = new JLabel("P3 (Herrenfahrrad)");
		lblPherrenfahrrad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPherrenfahrrad.setBounds(10, 220, 141, 23);
		panel.add(lblPherrenfahrrad);
		
		JLabel lblPeriodeN = new JLabel("Periode n+1");
		lblPeriodeN.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodeN.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPeriodeN.setBounds(132, 66, 141, 23);
		panel.add(lblPeriodeN);
		
		JLabel lblPeriodeN_1 = new JLabel("Periode n+2");
		lblPeriodeN_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodeN_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPeriodeN_1.setBounds(269, 66, 141, 23);
		panel.add(lblPeriodeN_1);
		
		JLabel lblPeriodeN_2 = new JLabel("Periode n+3");
		lblPeriodeN_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodeN_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPeriodeN_2.setBounds(420, 66, 141, 23);
		panel.add(lblPeriodeN_2);
		
		JLabel lblPeriodeN_3 = new JLabel("Periode n+4");
		lblPeriodeN_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodeN_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPeriodeN_3.setBounds(571, 66, 141, 23);
		panel.add(lblPeriodeN_3);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(153, 103, 120, 20);
		panel.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(153, 163, 120, 20);
		panel.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(153, 223, 120, 20);
		panel.add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(290, 103, 120, 20);
		panel.add(spinner_3);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(290, 163, 120, 20);
		panel.add(spinner_4);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setBounds(290, 223, 120, 20);
		panel.add(spinner_5);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setBounds(441, 103, 120, 20);
		panel.add(spinner_6);
		
		JSpinner spinner_7 = new JSpinner();
		spinner_7.setBounds(441, 163, 120, 20);
		panel.add(spinner_7);
		
		JSpinner spinner_8 = new JSpinner();
		spinner_8.setBounds(441, 223, 120, 20);
		panel.add(spinner_8);
		
		JSpinner spinner_9 = new JSpinner();
		spinner_9.setBounds(592, 103, 120, 20);
		panel.add(spinner_9);
		
		JSpinner spinner_10 = new JSpinner();
		spinner_10.setBounds(592, 163, 120, 20);
		panel.add(spinner_10);
		
		JSpinner spinner_11 = new JSpinner();
		spinner_11.setBounds(592, 223, 120, 20);
		panel.add(spinner_11);
		
		JButton btnSchritt = new JButton("Schritt 2: Verkaufszahlen >>");
		btnSchritt.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSchritt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSchritt.setBounds(523, 266, 220, 35);
		panel.add(btnSchritt);
		internalFrame_1.setVisible(true);

	}

	private void removeAllRowsFromTable(DefaultTableModel model) {
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	private String getModeAsString(int modeInt) {
		/*
		 * mode 5: normal mode 4: fast mode 3: JIT mode 2: cheap vendor mode 1:
		 * special order
		 */
		String[] modes = new String[] { "?", "special order", "cheap vendor",
				"JIT", "fast", "normal" };

		return modes[modeInt] + " (" + modeInt + ")";
	}
}
