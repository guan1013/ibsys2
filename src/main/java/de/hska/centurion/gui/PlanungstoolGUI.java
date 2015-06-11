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
import java.util.ResourceBundle;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.EtchedBorder;

public class PlanungstoolGUI {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("de.hska.centurion.gui.messages"); //$NON-NLS-1$

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
		frmIbsysiiplanungstoolGruppe.setBounds(100, 100, 893, 738);
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
		internalFrame_1.setBounds(10, 305, 867, 371);
		frmIbsysiiplanungstoolGruppe.getContentPane().add(internalFrame_1);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		internalFrame_1.getContentPane().add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panelStep1 = new JPanel();
		tabbedPane_1.addTab("Schritt 1", null, panelStep1, null);
		panelStep1.setLayout(null);
		
		JLabel lblStep1Title = new JLabel("Schritt 1: Prognose");
		lblStep1Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Title.setBounds(10, 11, 826, 23);
		panelStep1.add(lblStep1Title);
		
		JLabel lblStep1P1Title = new JLabel("P1 (Kinderfahrrad)");
		lblStep1P1Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P1Title.setBounds(10, 100, 141, 23);
		panelStep1.add(lblStep1P1Title);
		
		JLabel lblStep1P2Title = new JLabel("P2 (Damenfahrrad)");
		lblStep1P2Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P2Title.setBounds(10, 160, 141, 23);
		panelStep1.add(lblStep1P2Title);
		
		JLabel lblStep1P3Title = new JLabel("P3 (Herrenfahrrad)");
		lblStep1P3Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P3Title.setBounds(10, 220, 141, 23);
		panelStep1.add(lblStep1P3Title);
		
		JLabel lblStep1Periode1Title = new JLabel("Periode n+1");
		lblStep1Periode1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode1Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode1Title.setBounds(132, 66, 141, 23);
		panelStep1.add(lblStep1Periode1Title);
		
		JLabel lblStep1Periode2Title = new JLabel("Periode n+2");
		lblStep1Periode2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode2Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode2Title.setBounds(269, 66, 141, 23);
		panelStep1.add(lblStep1Periode2Title);
		
		JLabel lblStep1Periode3Title = new JLabel("Periode n+3");
		lblStep1Periode3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode3Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode3Title.setBounds(420, 66, 141, 23);
		panelStep1.add(lblStep1Periode3Title);
		
		JLabel lblStep1Periode4Title = new JLabel("Periode n+4");
		lblStep1Periode4Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode4Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode4Title.setBounds(571, 66, 141, 23);
		panelStep1.add(lblStep1Periode4Title);
		
		JSpinner spinnerStep1P1Periode1 = new JSpinner();
		spinnerStep1P1Periode1.setBounds(153, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode1);
		
		JSpinner spinnerStep1P2Periode1 = new JSpinner();
		spinnerStep1P2Periode1.setBounds(153, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode1);
		
		JSpinner spinnerStep1P3Periode1 = new JSpinner();
		spinnerStep1P3Periode1.setBounds(153, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode1);
		
		JSpinner spinnerStep1P1Periode2 = new JSpinner();
		spinnerStep1P1Periode2.setBounds(290, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode2);
		
		JSpinner spinnerStep1P2Periode2 = new JSpinner();
		spinnerStep1P2Periode2.setBounds(290, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode2);
		
		JSpinner spinnerStep1P3Periode2 = new JSpinner();
		spinnerStep1P3Periode2.setBounds(290, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode2);
		
		JSpinner spinnerStep1P1Periode3 = new JSpinner();
		spinnerStep1P1Periode3.setBounds(441, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode3);
		
		JSpinner spinnerStep1P2Periode3 = new JSpinner();
		spinnerStep1P2Periode3.setBounds(441, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode3);
		
		JSpinner spinnerStep1P3Periode3 = new JSpinner();
		spinnerStep1P3Periode3.setBounds(441, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode3);
		
		JSpinner spinnerStep1P1Periode4 = new JSpinner();
		spinnerStep1P1Periode4.setBounds(592, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode4);
		
		JSpinner spinnerStep1P2Periode4 = new JSpinner();
		spinnerStep1P2Periode4.setBounds(592, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode4);
		
		JSpinner spinnerStep1P3Periode4 = new JSpinner();
		spinnerStep1P3Periode4.setBounds(592, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode4);
		
		JButton btnStep1NextStep = new JButton("Schritt 2: Vertriebswunsch >>");
		btnStep1NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep1NextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStep1NextStep.setBounds(616, 268, 220, 35);
		panelStep1.add(btnStep1NextStep);
		
		JPanel panelStep2 = new JPanel();
		panelStep2.setLayout(null);
		tabbedPane_1.addTab("Schritt 2", null, panelStep2, null);
		
		JLabel lblStep2Title = new JLabel("Schritt 2: Vertriebswunsch & Direktverkauf");
		lblStep2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep2Title.setBounds(10, 11, 826, 23);
		panelStep2.add(lblStep2Title);
		
		JLabel lblStep2P1Title = new JLabel("P1 (Kinderfahrrad)");
		lblStep2P1Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P1Title.setBounds(10, 100, 141, 23);
		panelStep2.add(lblStep2P1Title);
		
		JLabel lblStep2P2Title = new JLabel("P2 (Damenfahrrad)");
		lblStep2P2Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P2Title.setBounds(10, 160, 141, 23);
		panelStep2.add(lblStep2P2Title);
		
		JLabel lblStep2P3Title = new JLabel("P3 (Herrenfahrrad)");
		lblStep2P3Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P3Title.setBounds(10, 220, 141, 23);
		panelStep2.add(lblStep2P3Title);
		
		JLabel lblStep2SalesTitle = new JLabel("Vertriebswunsch");
		lblStep2SalesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2SalesTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep2SalesTitle.setBounds(132, 66, 141, 23);
		panelStep2.add(lblStep2SalesTitle);
		
		JLabel lblStep2DirectSalesTitle = new JLabel("Direktverkauf");
		lblStep2DirectSalesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2DirectSalesTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep2DirectSalesTitle.setBounds(269, 66, 141, 23);
		panelStep2.add(lblStep2DirectSalesTitle);
		
		JSpinner spinnerStep2P1Sales = new JSpinner();
		spinnerStep2P1Sales.setBounds(153, 103, 120, 20);
		panelStep2.add(spinnerStep2P1Sales);
		
		JSpinner spinnerStep2P1DirectSales = new JSpinner();
		spinnerStep2P1DirectSales.setBounds(290, 103, 120, 20);
		panelStep2.add(spinnerStep2P1DirectSales);
		
		JSpinner spinnerStep2P2Sales = new JSpinner();
		spinnerStep2P2Sales.setBounds(153, 163, 120, 20);
		panelStep2.add(spinnerStep2P2Sales);
		
		JSpinner spinnerStep2P3Sales = new JSpinner();
		spinnerStep2P3Sales.setBounds(153, 223, 120, 20);
		panelStep2.add(spinnerStep2P3Sales);
		
		JSpinner spinnerStep2P2DirectSales = new JSpinner();
		spinnerStep2P2DirectSales.setBounds(290, 163, 120, 20);
		panelStep2.add(spinnerStep2P2DirectSales);
		
		JSpinner spinnerStep2P3DirectSales = new JSpinner();
		spinnerStep2P3DirectSales.setBounds(290, 223, 120, 20);
		panelStep2.add(spinnerStep2P3DirectSales);
		
		JButton btnStep2NextStep = new JButton("Schritt 3: Planbestand >>");
		btnStep2NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep2NextStep.setBounds(616, 266, 220, 35);
		panelStep2.add(btnStep2NextStep);
		
		JButton buttonStep2PrevStep = new JButton("<< Schritt 1: Prognose");
		buttonStep2PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonStep2PrevStep.setBounds(10, 266, 220, 35);
		panelStep2.add(buttonStep2PrevStep);
		
		JPanel panelStep3 = new JPanel();
		panelStep3.setLayout(null);
		tabbedPane_1.addTab("Schritt 3", null, panelStep3, null);
		
		JLabel lblStep3Title = new JLabel("Schritt 3: Planbestand");
		lblStep3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep3Title.setBounds(10, 11, 826, 23);
		panelStep3.add(lblStep3Title);
		
		JLabel lblStep3P1Title = new JLabel("P1 (Kinderfahrrad)");
		lblStep3P1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P1Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P1Title.setBounds(20, 45, 258, 23);
		panelStep3.add(lblStep3P1Title);
		
		JLabel lblStep3P2Title = new JLabel("P2 (Damenfahrrad)");
		lblStep3P2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P2Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P2Title.setBounds(298, 45, 258, 23);
		panelStep3.add(lblStep3P2Title);
		
		JLabel lblStep3P3Title = new JLabel("P3 ( Herrenfahrrad)");
		lblStep3P3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P3Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P3Title.setBounds(578, 45, 258, 23);
		panelStep3.add(lblStep3P3Title);
		
		JLabel lblStep3P1 = new JLabel("P1");
		lblStep3P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P1.setBounds(20, 79, 29, 23);
		panelStep3.add(lblStep3P1);
		
		JLabel lblStep3P2 = new JLabel("P2");
		lblStep3P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P2.setBounds(298, 79, 29, 23);
		panelStep3.add(lblStep3P2);
		
		JLabel lblStep3P3 = new JLabel("P3");
		lblStep3P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P3.setBounds(578, 79, 29, 23);
		panelStep3.add(lblStep3P3);
		
		JLabel lblStep3E4 = new JLabel("E4");
		lblStep3E4.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E4.setBounds(20, 101, 29, 23);
		panelStep3.add(lblStep3E4);
		
		JLabel lblStep3E5 = new JLabel("E5");
		lblStep3E5.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E5.setBounds(298, 101, 29, 23);
		panelStep3.add(lblStep3E5);
		
		JLabel lblStep3E6 = new JLabel("E6");
		lblStep3E6.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E6.setBounds(578, 101, 29, 23);
		panelStep3.add(lblStep3E6);
		
		JLabel lblStep3E7 = new JLabel("E7");
		lblStep3E7.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E7.setBounds(20, 124, 29, 23);
		panelStep3.add(lblStep3E7);
		
		JLabel lblStep3E8 = new JLabel("E8");
		lblStep3E8.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E8.setBounds(298, 124, 29, 23);
		panelStep3.add(lblStep3E8);
		
		JLabel lblStep3E9 = new JLabel("E9");
		lblStep3E9.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E9.setBounds(578, 124, 29, 23);
		panelStep3.add(lblStep3E9);
		
		JLabel lblStep3E10 = new JLabel("E10");
		lblStep3E10.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E10.setBounds(20, 146, 29, 23);
		panelStep3.add(lblStep3E10);
		
		JLabel lblStep3E11 = new JLabel("E11");
		lblStep3E11.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E11.setBounds(298, 146, 29, 23);
		panelStep3.add(lblStep3E11);
		
		JLabel lblStep3E12 = new JLabel("E12");
		lblStep3E12.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E12.setBounds(578, 146, 29, 23);
		panelStep3.add(lblStep3E12);
		
		JLabel lblStep3E13 = new JLabel("E13");
		lblStep3E13.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E13.setBounds(20, 169, 29, 23);
		panelStep3.add(lblStep3E13);
		
		JLabel lblStep3E14 = new JLabel("E14");
		lblStep3E14.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E14.setBounds(298, 169, 29, 23);
		panelStep3.add(lblStep3E14);
		
		JLabel lblStep3E15 = new JLabel("E15");
		lblStep3E15.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E15.setBounds(578, 169, 29, 23);
		panelStep3.add(lblStep3E15);
		
		JLabel lblStep3E16P1 = new JLabel("E16");
		lblStep3E16P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E16P1.setBounds(20, 194, 29, 23);
		panelStep3.add(lblStep3E16P1);
		
		JLabel lblStep3E16P2 = new JLabel("E16");
		lblStep3E16P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E16P2.setBounds(298, 194, 29, 23);
		panelStep3.add(lblStep3E16P2);
		
		JLabel lblStep3E16P3 = new JLabel("E16");
		lblStep3E16P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E16P3.setBounds(578, 194, 29, 23);
		panelStep3.add(lblStep3E16P3);
		
		JLabel lblStep3E17P1 = new JLabel("E17");
		lblStep3E17P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E17P1.setBounds(154, 79, 29, 23);
		panelStep3.add(lblStep3E17P1);
		
		JLabel lblStep3E17P2 = new JLabel("E17");
		lblStep3E17P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E17P2.setBounds(432, 79, 29, 23);
		panelStep3.add(lblStep3E17P2);
		
		JLabel lblStep3E17P3 = new JLabel("E17");
		lblStep3E17P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E17P3.setBounds(712, 79, 29, 23);
		panelStep3.add(lblStep3E17P3);
		
		JLabel lblStep3E18 = new JLabel("E18");
		lblStep3E18.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E18.setBounds(154, 101, 29, 23);
		panelStep3.add(lblStep3E18);
		
		JLabel lblStep3E19 = new JLabel("E19");
		lblStep3E19.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E19.setBounds(432, 101, 29, 23);
		panelStep3.add(lblStep3E19);
		
		JLabel lblStep3E20 = new JLabel("E20");
		lblStep3E20.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E20.setBounds(712, 101, 29, 23);
		panelStep3.add(lblStep3E20);
		
		JLabel lblStep3E26P1 = new JLabel("E26");
		lblStep3E26P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E26P1.setBounds(154, 124, 29, 23);
		panelStep3.add(lblStep3E26P1);
		
		JLabel lblStep3E26P2 = new JLabel("E26");
		lblStep3E26P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E26P2.setBounds(432, 124, 29, 23);
		panelStep3.add(lblStep3E26P2);
		
		JLabel lblStep3E26P3 = new JLabel("E26");
		lblStep3E26P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E26P3.setBounds(712, 124, 29, 23);
		panelStep3.add(lblStep3E26P3);
		
		JLabel lblStep3E29 = new JLabel("E29");
		lblStep3E29.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E29.setBounds(712, 146, 29, 23);
		panelStep3.add(lblStep3E29);
		
		JLabel lblStep3E30 = new JLabel("E30");
		lblStep3E30.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E30.setBounds(712, 169, 29, 23);
		panelStep3.add(lblStep3E30);
		
		JLabel lblStep3E31 = new JLabel("E31");
		lblStep3E31.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E31.setBounds(712, 194, 29, 23);
		panelStep3.add(lblStep3E31);
		
		JLabel lblStep3E49 = new JLabel("E49");
		lblStep3E49.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E49.setBounds(154, 146, 29, 23);
		panelStep3.add(lblStep3E49);
		
		JLabel lblStep3E50 = new JLabel("E50");
		lblStep3E50.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E50.setBounds(154, 169, 29, 23);
		panelStep3.add(lblStep3E50);
		
		JLabel lblStep3E51 = new JLabel("E51");
		lblStep3E51.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E51.setBounds(154, 194, 29, 23);
		panelStep3.add(lblStep3E51);
		
		JLabel lblStep3E54 = new JLabel("E54");
		lblStep3E54.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E54.setBounds(432, 146, 29, 23);
		panelStep3.add(lblStep3E54);
		
		JLabel lblStep3E55 = new JLabel("E55");
		lblStep3E55.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E55.setBounds(432, 169, 29, 23);
		panelStep3.add(lblStep3E55);
		
		JLabel lblStep3E56 = new JLabel("E56");
		lblStep3E56.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E56.setBounds(432, 194, 29, 23);
		panelStep3.add(lblStep3E56);
		
		JLabel lblStep3P1PartsToBeProduced = new JLabel("250");
		lblStep3P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P1PartsToBeProduced.setBounds(115, 79, 29, 23);
		panelStep3.add(lblStep3P1PartsToBeProduced);
		
		JLabel lblStep3P2PartsToBeProduced = new JLabel("250");
		lblStep3P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P2PartsToBeProduced.setBounds(393, 79, 29, 23);
		panelStep3.add(lblStep3P2PartsToBeProduced);
		
		JLabel lblStep3P3PartsToBeProduced = new JLabel("250");
		lblStep3P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P3PartsToBeProduced.setBounds(673, 79, 29, 23);
		panelStep3.add(lblStep3P3PartsToBeProduced);
		
		JLabel lblStep3E4PartsToBeProduced = new JLabel("250");
		lblStep3E4PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E4PartsToBeProduced.setBounds(115, 101, 29, 23);
		panelStep3.add(lblStep3E4PartsToBeProduced);
		
		JLabel lblStep3E5PartsToBeProduced = new JLabel("250");
		lblStep3E5PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E5PartsToBeProduced.setBounds(393, 101, 29, 23);
		panelStep3.add(lblStep3E5PartsToBeProduced);
		
		JLabel lblStep3E6PartsToBeProduced = new JLabel("250");
		lblStep3E6PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E6PartsToBeProduced.setBounds(673, 101, 29, 23);
		panelStep3.add(lblStep3E6PartsToBeProduced);
		
		JLabel lblStep3E7PartsToBeProduced = new JLabel("250");
		lblStep3E7PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E7PartsToBeProduced.setBounds(115, 124, 29, 23);
		panelStep3.add(lblStep3E7PartsToBeProduced);
		
		JLabel lblStep3E8PartsToBeProduced = new JLabel("250");
		lblStep3E8PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E8PartsToBeProduced.setBounds(393, 124, 29, 23);
		panelStep3.add(lblStep3E8PartsToBeProduced);
		
		JLabel lblStep3E9PartsToBeProduced = new JLabel("250");
		lblStep3E9PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E9PartsToBeProduced.setBounds(673, 124, 29, 23);
		panelStep3.add(lblStep3E9PartsToBeProduced);
		
		JLabel lblStep3E10PartsToBeProduced = new JLabel("250");
		lblStep3E10PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E10PartsToBeProduced.setBounds(115, 146, 29, 23);
		panelStep3.add(lblStep3E10PartsToBeProduced);
		
		JLabel lblStep3E11PartsToBeProduced = new JLabel("250");
		lblStep3E11PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E11PartsToBeProduced.setBounds(393, 146, 29, 23);
		panelStep3.add(lblStep3E11PartsToBeProduced);
		
		JLabel lblStep3E12PartsToBeProduced = new JLabel("250");
		lblStep3E12PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E12PartsToBeProduced.setBounds(673, 146, 29, 23);
		panelStep3.add(lblStep3E12PartsToBeProduced);
		
		JLabel lblStep3E13PartsToBeProduced = new JLabel("250");
		lblStep3E13PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E13PartsToBeProduced.setBounds(115, 169, 29, 23);
		panelStep3.add(lblStep3E13PartsToBeProduced);
		
		JLabel lblStep3E14PartsToBeProduced = new JLabel("250");
		lblStep3E14PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E14PartsToBeProduced.setBounds(393, 169, 29, 23);
		panelStep3.add(lblStep3E14PartsToBeProduced);
		
		JLabel lblStep3E15PartsToBeProduced = new JLabel("250");
		lblStep3E15PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E15PartsToBeProduced.setBounds(673, 169, 29, 23);
		panelStep3.add(lblStep3E15PartsToBeProduced);
		
		JLabel lblStep3E16P1PartsToBeProduced = new JLabel("250");
		lblStep3E16P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E16P1PartsToBeProduced.setBounds(115, 192, 29, 23);
		panelStep3.add(lblStep3E16P1PartsToBeProduced);
		
		JLabel lblStep3E16P2PartsToBeProduced = new JLabel("250");
		lblStep3E16P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E16P2PartsToBeProduced.setBounds(393, 192, 29, 23);
		panelStep3.add(lblStep3E16P2PartsToBeProduced);
		
		JLabel lblStep3E16P3PartsToBeProduced = new JLabel("250");
		lblStep3E16P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E16P3PartsToBeProduced.setBounds(673, 192, 29, 23);
		panelStep3.add(lblStep3E16P3PartsToBeProduced);
		
		JLabel lblStep3E17P1PartsToBeProduced = new JLabel("250");
		lblStep3E17P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E17P1PartsToBeProduced.setBounds(249, 79, 29, 23);
		panelStep3.add(lblStep3E17P1PartsToBeProduced);
		
		JLabel lblStep3E17P2PartsToBeProduced = new JLabel("250");
		lblStep3E17P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E17P2PartsToBeProduced.setBounds(527, 79, 29, 23);
		panelStep3.add(lblStep3E17P2PartsToBeProduced);
		
		JLabel lblStep3E17P3PartsToBeProduced = new JLabel("250");
		lblStep3E17P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E17P3PartsToBeProduced.setBounds(807, 79, 29, 23);
		panelStep3.add(lblStep3E17P3PartsToBeProduced);
		
		JLabel lblStep3E18PartsToBeProduced = new JLabel("250");
		lblStep3E18PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E18PartsToBeProduced.setBounds(249, 101, 29, 23);
		panelStep3.add(lblStep3E18PartsToBeProduced);
		
		JLabel lblStep3E19PartsToBeProduced = new JLabel("250");
		lblStep3E19PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E19PartsToBeProduced.setBounds(527, 101, 29, 23);
		panelStep3.add(lblStep3E19PartsToBeProduced);
		
		JLabel lblStep3E20PartsToBeProduced = new JLabel("250");
		lblStep3E20PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E20PartsToBeProduced.setBounds(807, 101, 29, 23);
		panelStep3.add(lblStep3E20PartsToBeProduced);
		
		JLabel lblStep3E26P1PartsToBeProduced = new JLabel("250");
		lblStep3E26P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E26P1PartsToBeProduced.setBounds(249, 124, 29, 23);
		panelStep3.add(lblStep3E26P1PartsToBeProduced);
		
		JLabel lblStep3E26P2PartsToBeProduced = new JLabel("250");
		lblStep3E26P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E26P2PartsToBeProduced.setBounds(527, 124, 29, 23);
		panelStep3.add(lblStep3E26P2PartsToBeProduced);
		
		JLabel lblStep3E26P3PartsToBeProduced = new JLabel("250");
		lblStep3E26P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E26P3PartsToBeProduced.setBounds(807, 124, 29, 23);
		panelStep3.add(lblStep3E26P3PartsToBeProduced);
		
		JLabel lblStep3E29PartsToBeProduced = new JLabel("250");
		lblStep3E29PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E29PartsToBeProduced.setBounds(807, 146, 29, 23);
		panelStep3.add(lblStep3E29PartsToBeProduced);
		
		JLabel lblStep3E30PartsToBeProduced = new JLabel("250");
		lblStep3E30PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E30PartsToBeProduced.setBounds(807, 169, 29, 23);
		panelStep3.add(lblStep3E30PartsToBeProduced);
		
		JLabel lblStep3E31PartsToBeProduced = new JLabel("250");
		lblStep3E31PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E31PartsToBeProduced.setBounds(807, 192, 29, 23);
		panelStep3.add(lblStep3E31PartsToBeProduced);
		
		JLabel lblStep3E49PartsToBeProduced = new JLabel("250");
		lblStep3E49PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E49PartsToBeProduced.setBounds(249, 146, 29, 23);
		panelStep3.add(lblStep3E49PartsToBeProduced);
		
		JLabel lblStep3E50PartsToBeProduced = new JLabel("250");
		lblStep3E50PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E50PartsToBeProduced.setBounds(249, 169, 29, 23);
		panelStep3.add(lblStep3E50PartsToBeProduced);
		
		JLabel lblStep3E51PartsToBeProduced = new JLabel("250");
		lblStep3E51PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E51PartsToBeProduced.setBounds(249, 192, 29, 23);
		panelStep3.add(lblStep3E51PartsToBeProduced);
		
		JLabel lblStep3E54PartsToBeProduced = new JLabel("250");
		lblStep3E54PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E54PartsToBeProduced.setBounds(527, 146, 29, 23);
		panelStep3.add(lblStep3E54PartsToBeProduced);
		
		JLabel lblStep3E55PartsToBeProduced = new JLabel("250");
		lblStep3E55PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E55PartsToBeProduced.setBounds(527, 169, 29, 23);
		panelStep3.add(lblStep3E55PartsToBeProduced);
		
		JLabel lblStep3E56PartsToBeProduced = new JLabel("250");
		lblStep3E56PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E56PartsToBeProduced.setBounds(527, 192, 29, 23);
		panelStep3.add(lblStep3E56PartsToBeProduced);
		
		JSpinner spinnerStep3P1 = new JSpinner();
		lblStep3P1.setLabelFor(spinnerStep3P1);
		spinnerStep3P1.setBounds(50, 82, 60, 20);
		panelStep3.add(spinnerStep3P1);
		
		JSpinner spinnerStep3P2 = new JSpinner();
		spinnerStep3P2.setBounds(328, 82, 60, 20);
		panelStep3.add(spinnerStep3P2);
		
		JSpinner spinnerStep3P3 = new JSpinner();
		spinnerStep3P3.setBounds(608, 82, 60, 20);
		panelStep3.add(spinnerStep3P3);
		
		JSpinner spinnerStep3E4 = new JSpinner();
		spinnerStep3E4.setBounds(50, 104, 60, 20);
		panelStep3.add(spinnerStep3E4);
		
		JSpinner spinnerStep3E5 = new JSpinner();
		spinnerStep3E5.setBounds(328, 104, 60, 20);
		panelStep3.add(spinnerStep3E5);
		
		JSpinner spinnerStep3E6 = new JSpinner();
		spinnerStep3E6.setBounds(608, 104, 60, 20);
		panelStep3.add(spinnerStep3E6);
		
		JSpinner spinnerStep3E7 = new JSpinner();
		spinnerStep3E7.setBounds(50, 127, 60, 20);
		panelStep3.add(spinnerStep3E7);
		
		JSpinner spinnerStep3E8 = new JSpinner();
		spinnerStep3E8.setBounds(328, 127, 60, 20);
		panelStep3.add(spinnerStep3E8);
		
		JSpinner spinnerStep3E9 = new JSpinner();
		spinnerStep3E9.setBounds(608, 127, 60, 20);
		panelStep3.add(spinnerStep3E9);
		
		JSpinner spinnerStep3E10 = new JSpinner();
		spinnerStep3E10.setBounds(50, 149, 60, 20);
		panelStep3.add(spinnerStep3E10);
		
		JSpinner spinnerStep3E11 = new JSpinner();
		spinnerStep3E11.setBounds(328, 149, 60, 20);
		panelStep3.add(spinnerStep3E11);
		
		JSpinner spinnerStep3E12 = new JSpinner();
		spinnerStep3E12.setBounds(608, 149, 60, 20);
		panelStep3.add(spinnerStep3E12);
		
		JSpinner spinnerStep3E13 = new JSpinner();
		spinnerStep3E13.setBounds(50, 172, 60, 20);
		panelStep3.add(spinnerStep3E13);
		
		JSpinner spinnerStep3E14 = new JSpinner();
		spinnerStep3E14.setBounds(328, 172, 60, 20);
		panelStep3.add(spinnerStep3E14);
		
		JSpinner spinnerStep3E15 = new JSpinner();
		spinnerStep3E15.setBounds(608, 172, 60, 20);
		panelStep3.add(spinnerStep3E15);
		
		JSpinner spinnerStep3E16P1 = new JSpinner();
		spinnerStep3E16P1.setBounds(50, 194, 60, 20);
		panelStep3.add(spinnerStep3E16P1);
		
		JSpinner spinnerStep3E16P2 = new JSpinner();
		spinnerStep3E16P2.setBounds(328, 194, 60, 20);
		panelStep3.add(spinnerStep3E16P2);
		
		JSpinner spinnerStep3E16P3 = new JSpinner();
		spinnerStep3E16P3.setBounds(608, 194, 60, 20);
		panelStep3.add(spinnerStep3E16P3);
		
		JSpinner spinnerStep3E17P1 = new JSpinner();
		spinnerStep3E17P1.setBounds(184, 82, 60, 20);
		panelStep3.add(spinnerStep3E17P1);
		
		JSpinner spinnerStep3E17P2 = new JSpinner();
		spinnerStep3E17P2.setBounds(462, 82, 60, 20);
		panelStep3.add(spinnerStep3E17P2);
		
		JSpinner spinnerStep3E17P3 = new JSpinner();
		spinnerStep3E17P3.setBounds(742, 82, 60, 20);
		panelStep3.add(spinnerStep3E17P3);
		
		JSpinner spinnerStep3E18 = new JSpinner();
		spinnerStep3E18.setBounds(184, 104, 60, 20);
		panelStep3.add(spinnerStep3E18);
		
		JSpinner spinnerStep3E119 = new JSpinner();
		spinnerStep3E119.setBounds(462, 104, 60, 20);
		panelStep3.add(spinnerStep3E119);
		
		JSpinner spinnerStep3E20 = new JSpinner();
		spinnerStep3E20.setBounds(742, 104, 60, 20);
		panelStep3.add(spinnerStep3E20);
		
		JSpinner spinnerStep3E26P1 = new JSpinner();
		spinnerStep3E26P1.setBounds(184, 127, 60, 20);
		panelStep3.add(spinnerStep3E26P1);
		
		JSpinner spinnerStep3E26P2 = new JSpinner();
		spinnerStep3E26P2.setBounds(462, 127, 60, 20);
		panelStep3.add(spinnerStep3E26P2);
		
		JSpinner spinnerStep3E26P3 = new JSpinner();
		spinnerStep3E26P3.setBounds(742, 127, 60, 20);
		panelStep3.add(spinnerStep3E26P3);
		
		JSpinner spinnerStep3E29 = new JSpinner();
		spinnerStep3E29.setBounds(742, 149, 60, 20);
		panelStep3.add(spinnerStep3E29);
		
		JSpinner spinnerStep3E30 = new JSpinner();
		spinnerStep3E30.setBounds(742, 172, 60, 20);
		panelStep3.add(spinnerStep3E30);
		
		JSpinner spinnerStep3E31 = new JSpinner();
		spinnerStep3E31.setBounds(742, 194, 60, 20);
		panelStep3.add(spinnerStep3E31);
		
		JSpinner spinnerStep3E49 = new JSpinner();
		spinnerStep3E49.setBounds(184, 149, 60, 20);
		panelStep3.add(spinnerStep3E49);
		
		JSpinner spinnerStep3E50 = new JSpinner();
		spinnerStep3E50.setBounds(184, 172, 60, 20);
		panelStep3.add(spinnerStep3E50);
		
		JSpinner spinnerStep3E51 = new JSpinner();
		spinnerStep3E51.setBounds(184, 194, 60, 20);
		panelStep3.add(spinnerStep3E51);
		
		JSpinner spinnerStep3E54 = new JSpinner();
		spinnerStep3E54.setBounds(462, 149, 60, 20);
		panelStep3.add(spinnerStep3E54);
		
		JSpinner spinnerStep3E55 = new JSpinner();
		spinnerStep3E55.setBounds(462, 172, 60, 20);
		panelStep3.add(spinnerStep3E55);
		
		JSpinner spinnerStep3E56 = new JSpinner();
		spinnerStep3E56.setBounds(462, 194, 60, 20);
		panelStep3.add(spinnerStep3E56);
		
		JButton buttonStep3NextStep = new JButton("Schritt 4: Auftragsreihenfolge >>");
		buttonStep3NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonStep3NextStep.setBounds(616, 266, 220, 35);
		panelStep3.add(buttonStep3NextStep);
		
		JButton buttonStep3PrevStep = new JButton("<< Schritt 2: Vertriebswunsch");
		buttonStep3PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonStep3PrevStep.setBounds(10, 266, 220, 35);
		panelStep3.add(buttonStep3PrevStep);
		
		JButton btnStep3Recalculate = new JButton("Neu berechnen");
		btnStep3Recalculate.setBounds(328, 266, 182, 35);
		panelStep3.add(btnStep3Recalculate);
		
		JPanel panelStep4 = new JPanel();
		tabbedPane_1.addTab("Schritt 4", null, panelStep4, null);
		panelStep4.setLayout(null);
		
		JLabel lblSchrittAuftragreihenfolge = new JLabel("Schritt 4: Auftragsreihenfolge");
		lblSchrittAuftragreihenfolge.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittAuftragreihenfolge.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittAuftragreihenfolge.setBounds(10, 11, 826, 23);
		panelStep4.add(lblSchrittAuftragreihenfolge);
		
		JButton button = new JButton("<< Schritt 3: Planbestand");
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setBounds(10, 268, 220, 35);
		panelStep4.add(button);
		
		JButton btnSchrittKapazitten = new JButton("Schritt 5: Kapazitäten >>");
		btnSchrittKapazitten.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSchrittKapazitten.setBounds(616, 268, 220, 35);
		panelStep4.add(btnSchrittKapazitten);
		
		JList list = new JList();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"E4 -- 50x", "E5 -- 100x"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(254, 45, 153, 210);
		panelStep4.add(list);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(PlanungstoolGUI.class.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnNewButton.setBounds(417, 82, 124, 35);
		panelStep4.add(btnNewButton);
		
		JButton btnSplitten = new JButton("Splitten");
		btnSplitten.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSplitten.setBounds(417, 128, 124, 35);
		panelStep4.add(btnSplitten);
		
		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(PlanungstoolGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		button_2.setBounds(417, 174, 124, 35);
		panelStep4.add(button_2);
		
		JPanel panelStep5 = new JPanel();
		tabbedPane_1.addTab("Schritt 5", null, panelStep5, null);
		panelStep5.setLayout(null);
		
		JLabel lblSchrittKapazittsplanung = new JLabel("Schritt 5: Kapazitätsplanung");
		lblSchrittKapazittsplanung.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittKapazittsplanung.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittKapazittsplanung.setBounds(10, 11, 826, 23);
		panelStep5.add(lblSchrittKapazittsplanung);
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
