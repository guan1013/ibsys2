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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import de.hska.centurion.domain.gui.Forecast;
import de.hska.centurion.domain.gui.SafetyStock;
import de.hska.centurion.domain.gui.Sales;
import de.hska.centurion.domain.gui.UserInput;
import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.Article;
import de.hska.centurion.domain.input.components.IdleTimeCostsSum;
import de.hska.centurion.domain.input.components.Order;
import de.hska.centurion.domain.input.components.WorkplaceCosts;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;
import de.hska.centurion.domain.output.Production;
import de.hska.centurion.io.XmlInputParser;
import de.hska.centurion.services.production.ProductionService;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.xml.bind.JAXBException;

public class PlanungstoolGUI {

	private JFrame frmIbsysiiplanungstoolGruppe;
	private JTable tableWarehouse;
	private JTable tableInwardStockMovement;
	private JTable tableFutureInwardStockMovement;
	private JTable tableIdleTimeCosts;
	private JTable tableWaitingListWorkstations;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_35;
	private JTextField textField_36;
	private JTextField textField_37;
	private JTextField textField_38;
	private JTextField textField_39;
	private JTextField textField_40;
	private JTextField textField_41;
	private JTextField textField_42;
	private JTextField textField_43;
	private JTextField textField_44;
	private JTextField textField_45;
	private JTextField textField_46;
	private JTextField textField_47;
	private JTextField textField_48;
	private JTextField textField_49;
	private JTextField textField_50;
	private JTextField textField_51;
	private JTextField textField_52;
	private JTextField textField_53;
	private JTextField textField_54;
	private JTextField textField_55;

	private JTabbedPane tabbedPanePlanning;

	private JLabel lblStep1Periode1Title;

	private JLabel lblStep1Periode2Title;

	private JLabel lblStep1Periode3Title;

	private JLabel lblStep1Periode4Title;

	private JSpinner spinnerStep1P1Periode1;

	private JSpinner spinnerStep1P2Periode1;

	private JSpinner spinnerStep1P3Periode1;

	private JSpinner spinnerStep1P1Periode2;

	private JSpinner spinnerStep1P2Periode2;

	private JSpinner spinnerStep1P3Periode2;

	private JSpinner spinnerStep1P1Periode3;

	private JSpinner spinnerStep1P2Periode3;

	private JSpinner spinnerStep1P3Periode3;

	private JSpinner spinnerStep1P1Periode4;

	private JSpinner spinnerStep1P2Periode4;

	private JSpinner spinnerStep1P3Periode4;
	private JSpinner spinnerStep2P1Sales;
	private JSpinner spinnerStep2P1DirectSales;
	private JSpinner spinnerStep2P2Sales;
	private JSpinner spinnerStep2P3Sales;
	private JSpinner spinnerStep2P2DirectSales;
	private JSpinner spinnerStep2P3DirectSales;

	private HashMap<Integer, JPanel> stepsMap;

	private HashMap<String, SafetyStockEntity> safetyStockFormular;

	/*
	 * CURRENTLY LOADED XML-RESULT FILE
	 */
	private Results results = null;

	/*
	 * PLACE TO STORE ALL USER INPUT FROM THE GUI
	 */
	private UserInput userInput;

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
		stepsMap = new HashMap<>();
		userInput = new UserInput();
		safetyStockFormular = new HashMap<String, SafetyStockEntity>();
		userInput.setSafetyStock(new SafetyStock(100));
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
		internalFrame.setBounds(10, 11, 867, 286);
		frmIbsysiiplanungstoolGruppe.getContentPane().add(internalFrame);
		internalFrame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 831, 235);
		internalFrame.getContentPane().add(tabbedPane);

		JPanel panelWarehouse = new JPanel();
		tabbedPane.addTab("Akt. Lagerbestand", null, panelWarehouse, null);
		panelWarehouse.setLayout(null);

		JScrollPane scrollPaneWarehouse = new JScrollPane();
		scrollPaneWarehouse.setBounds(0, 25, 826, 182);
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
		labelTotalStockValue.setBounds(20, 0, 806, 23);
		panelWarehouse.add(labelTotalStockValue);

		JPanel panelInwardStockMovement = new JPanel();
		tabbedPane.addTab("Akt. Lagerzugang Bestellungen", null,
				panelInwardStockMovement, null);
		panelInwardStockMovement.setLayout(null);

		JScrollPane scrollPaneInwardStockMovement = new JScrollPane();
		scrollPaneInwardStockMovement.setBounds(0, 0, 826, 207);
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
		scrollPaneFutureInwardStockMovement.setBounds(0, 0, 826, 207);
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
		scrollPaneIdleTimeCosts.setBounds(0, 0, 826, 207);
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
		scrollPaneWorklingListWorkstations.setBounds(0, 0, 826, 207);
		panelWaitingListWorkstations.add(scrollPaneWorklingListWorkstations);

		tableWaitingListWorkstations = new JTable();
		scrollPaneWorklingListWorkstations
				.setRowHeaderView(tableWaitingListWorkstations);
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
		tabbedPane.addTab("Warteliste Material", null, panelWaitingListStock,
				null);
		panelWaitingListStock.setLayout(null);

		table = new JTable();
		table.setBounds(0, 0, 826, 196);
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

		final PlanungstoolGUI gui = this;

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

					gui.switchToStep(1);
					gui.displayPeriod(results.getPeriod());
					gui.setResults(results);
				}
			}
		});
		mnFile.add(mntmLoadResultXml);
		frmIbsysiiplanungstoolGruppe.getContentPane().setLayout(null);

		JInternalFrame internalFrame_1 = new JInternalFrame("Planung");
		internalFrame_1.setBounds(10, 305, 867, 371);
		frmIbsysiiplanungstoolGruppe.getContentPane().add(internalFrame_1);

		tabbedPanePlanning = new JTabbedPane(JTabbedPane.TOP);
		internalFrame_1.getContentPane().add(tabbedPanePlanning,
				BorderLayout.CENTER);

		JPanel panelStep1 = new JPanel();
		tabbedPanePlanning.addTab("Schritt 1", null, panelStep1, null);
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

		lblStep1Periode1Title = new JLabel("Periode n+1");
		lblStep1Periode1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode1Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode1Title.setBounds(132, 66, 141, 23);
		panelStep1.add(lblStep1Periode1Title);

		lblStep1Periode2Title = new JLabel("Periode n+2");
		lblStep1Periode2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode2Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode2Title.setBounds(269, 66, 141, 23);
		panelStep1.add(lblStep1Periode2Title);

		lblStep1Periode3Title = new JLabel("Periode n+3");
		lblStep1Periode3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode3Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode3Title.setBounds(420, 66, 141, 23);
		panelStep1.add(lblStep1Periode3Title);

		lblStep1Periode4Title = new JLabel("Periode n+4");
		lblStep1Periode4Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode4Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep1Periode4Title.setBounds(571, 66, 141, 23);
		panelStep1.add(lblStep1Periode4Title);

		spinnerStep1P1Periode1 = new JSpinner();
		spinnerStep1P1Periode1.setBounds(153, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode1);

		spinnerStep1P2Periode1 = new JSpinner();
		spinnerStep1P2Periode1.setBounds(153, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode1);

		spinnerStep1P3Periode1 = new JSpinner();
		spinnerStep1P3Periode1.setBounds(153, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode1);

		spinnerStep1P1Periode2 = new JSpinner();
		spinnerStep1P1Periode2.setBounds(290, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode2);

		spinnerStep1P2Periode2 = new JSpinner();
		spinnerStep1P2Periode2.setBounds(290, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode2);

		spinnerStep1P3Periode2 = new JSpinner();
		spinnerStep1P3Periode2.setBounds(290, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode2);

		spinnerStep1P1Periode3 = new JSpinner();
		spinnerStep1P1Periode3.setBounds(441, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode3);

		spinnerStep1P2Periode3 = new JSpinner();
		spinnerStep1P2Periode3.setBounds(441, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode3);

		spinnerStep1P3Periode3 = new JSpinner();
		spinnerStep1P3Periode3.setBounds(441, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode3);

		spinnerStep1P1Periode4 = new JSpinner();
		spinnerStep1P1Periode4.setBounds(592, 103, 120, 20);
		panelStep1.add(spinnerStep1P1Periode4);

		spinnerStep1P2Periode4 = new JSpinner();
		spinnerStep1P2Periode4.setBounds(592, 163, 120, 20);
		panelStep1.add(spinnerStep1P2Periode4);

		spinnerStep1P3Periode4 = new JSpinner();
		spinnerStep1P3Periode4.setBounds(592, 223, 120, 20);
		panelStep1.add(spinnerStep1P3Periode4);

		JButton btnStep1NextStep = new JButton("Schritt 2: Vertriebswunsch >>");
		btnStep1NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep1NextStep.setBounds(616, 268, 220, 35);
		panelStep1.add(btnStep1NextStep);

		JPanel panelStep2 = new JPanel();
		panelStep2.setLayout(null);
		tabbedPanePlanning.addTab("Schritt 2", null, panelStep2, null);

		JLabel lblStep2Title = new JLabel(
				"Schritt 2: Vertriebswunsch & Direktverkauf");
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

		spinnerStep2P1Sales = new JSpinner();
		spinnerStep2P1Sales.setBounds(153, 103, 120, 20);
		panelStep2.add(spinnerStep2P1Sales);

		spinnerStep2P1DirectSales = new JSpinner();
		spinnerStep2P1DirectSales.setBounds(290, 103, 120, 20);
		panelStep2.add(spinnerStep2P1DirectSales);

		spinnerStep2P2Sales = new JSpinner();
		spinnerStep2P2Sales.setBounds(153, 163, 120, 20);
		panelStep2.add(spinnerStep2P2Sales);

		spinnerStep2P3Sales = new JSpinner();
		spinnerStep2P3Sales.setBounds(153, 223, 120, 20);
		panelStep2.add(spinnerStep2P3Sales);

		spinnerStep2P2DirectSales = new JSpinner();
		spinnerStep2P2DirectSales.setBounds(290, 163, 120, 20);
		panelStep2.add(spinnerStep2P2DirectSales);

		spinnerStep2P3DirectSales = new JSpinner();
		spinnerStep2P3DirectSales.setBounds(290, 223, 120, 20);
		panelStep2.add(spinnerStep2P3DirectSales);

		JButton btnStep2NextStep = new JButton("Schritt 3: Planbestand >>");
		btnStep2NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep2NextStep.setBounds(616, 266, 220, 35);
		panelStep2.add(btnStep2NextStep);

		JButton btnStep2PrevStep = new JButton("<< Schritt 1: Prognose");
		btnStep2PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep2PrevStep.setBounds(10, 266, 220, 35);
		panelStep2.add(btnStep2PrevStep);

		JPanel panelStep3 = new JPanel();
		panelStep3.setLayout(null);
		tabbedPanePlanning.addTab("Schritt 3", null, panelStep3, null);

		JLabel lblStep3Title = new JLabel("Schritt 3: Planbestand");
		lblStep3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep3Title.setBounds(10, 11, 826, 23);
		panelStep3.add(lblStep3Title);

		JLabel lblStep3P1Title = new JLabel("P1 (Kinderfahrrad)");
		lblStep3P1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P1Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P1Title.setBounds(20, 108, 258, 23);
		panelStep3.add(lblStep3P1Title);

		JLabel lblStep3P2Title = new JLabel("P2 (Damenfahrrad)");
		lblStep3P2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P2Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P2Title.setBounds(298, 108, 258, 23);
		panelStep3.add(lblStep3P2Title);

		JLabel lblStep3P3Title = new JLabel("P3 ( Herrenfahrrad)");
		lblStep3P3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3P3Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P3Title.setBounds(578, 108, 258, 23);
		panelStep3.add(lblStep3P3Title);

		JLabel lblStep3P1 = new JLabel("P1");
		lblStep3P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P1.setBounds(20, 142, 29, 23);
		panelStep3.add(lblStep3P1);

		JLabel lblStep3P2 = new JLabel("P2");
		lblStep3P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P2.setBounds(298, 142, 29, 23);
		panelStep3.add(lblStep3P2);

		JLabel lblStep3P3 = new JLabel("P3");
		lblStep3P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P3.setBounds(578, 142, 29, 23);
		panelStep3.add(lblStep3P3);

		JLabel lblStep3E4 = new JLabel("E4");
		lblStep3E4.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E4.setBounds(20, 164, 29, 23);
		panelStep3.add(lblStep3E4);

		JLabel lblStep3E5 = new JLabel("E5");
		lblStep3E5.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E5.setBounds(298, 164, 29, 23);
		panelStep3.add(lblStep3E5);

		JLabel lblStep3E6 = new JLabel("E6");
		lblStep3E6.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E6.setBounds(578, 164, 29, 23);
		panelStep3.add(lblStep3E6);

		JLabel lblStep3E7 = new JLabel("E7");
		lblStep3E7.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E7.setBounds(20, 187, 29, 23);
		panelStep3.add(lblStep3E7);

		JLabel lblStep3E8 = new JLabel("E8");
		lblStep3E8.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E8.setBounds(298, 187, 29, 23);
		panelStep3.add(lblStep3E8);

		JLabel lblStep3E9 = new JLabel("E9");
		lblStep3E9.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E9.setBounds(578, 187, 29, 23);
		panelStep3.add(lblStep3E9);

		JLabel lblStep3E10 = new JLabel("E10");
		lblStep3E10.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E10.setBounds(20, 209, 29, 23);
		panelStep3.add(lblStep3E10);

		JLabel lblStep3E11 = new JLabel("E11");
		lblStep3E11.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E11.setBounds(298, 209, 29, 23);
		panelStep3.add(lblStep3E11);

		JLabel lblStep3E12 = new JLabel("E12");
		lblStep3E12.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E12.setBounds(578, 209, 29, 23);
		panelStep3.add(lblStep3E12);

		JLabel lblStep3E13 = new JLabel("E13");
		lblStep3E13.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E13.setBounds(20, 232, 29, 23);
		panelStep3.add(lblStep3E13);

		JLabel lblStep3E14 = new JLabel("E14");
		lblStep3E14.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E14.setBounds(298, 232, 29, 23);
		panelStep3.add(lblStep3E14);

		JLabel lblStep3E15 = new JLabel("E15");
		lblStep3E15.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E15.setBounds(578, 232, 29, 23);
		panelStep3.add(lblStep3E15);

		JLabel lblStep3E16 = new JLabel("E16");
		lblStep3E16.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E16.setBounds(268, 60, 29, 23);
		panelStep3.add(lblStep3E16);

		JLabel lblStep3E17 = new JLabel("E17");
		lblStep3E17.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E17.setBounds(402, 60, 29, 23);
		panelStep3.add(lblStep3E17);

		JLabel lblStep3E18 = new JLabel("E18");
		lblStep3E18.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E18.setBounds(154, 142, 29, 23);
		panelStep3.add(lblStep3E18);

		JLabel lblStep3E19 = new JLabel("E19");
		lblStep3E19.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E19.setBounds(432, 142, 29, 23);
		panelStep3.add(lblStep3E19);

		JLabel lblStep3E20 = new JLabel("E20");
		lblStep3E20.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E20.setBounds(712, 142, 29, 23);
		panelStep3.add(lblStep3E20);

		JLabel lblStep3E26 = new JLabel("E26");
		lblStep3E26.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E26.setBounds(534, 60, 29, 23);
		panelStep3.add(lblStep3E26);

		JLabel lblStep3E29 = new JLabel("E29");
		lblStep3E29.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E29.setBounds(712, 165, 29, 23);
		panelStep3.add(lblStep3E29);

		JLabel lblStep3E30 = new JLabel("E30");
		lblStep3E30.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E30.setBounds(712, 188, 29, 23);
		panelStep3.add(lblStep3E30);

		JLabel lblStep3E31 = new JLabel("E31");
		lblStep3E31.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E31.setBounds(712, 213, 29, 23);
		panelStep3.add(lblStep3E31);

		JLabel lblStep3E49 = new JLabel("E49");
		lblStep3E49.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E49.setBounds(154, 164, 29, 23);
		panelStep3.add(lblStep3E49);

		JLabel lblStep3E50 = new JLabel("E50");
		lblStep3E50.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E50.setBounds(154, 187, 29, 23);
		panelStep3.add(lblStep3E50);

		JLabel lblStep3E51 = new JLabel("E51");
		lblStep3E51.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E51.setBounds(154, 212, 29, 23);
		panelStep3.add(lblStep3E51);

		JLabel lblStep3E54 = new JLabel("E54");
		lblStep3E54.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E54.setBounds(432, 165, 29, 23);
		panelStep3.add(lblStep3E54);

		JLabel lblStep3E55 = new JLabel("E55");
		lblStep3E55.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E55.setBounds(432, 188, 29, 23);
		panelStep3.add(lblStep3E55);

		JLabel lblStep3E56 = new JLabel("E56");
		lblStep3E56.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E56.setBounds(432, 213, 29, 23);
		panelStep3.add(lblStep3E56);

		JLabel lblStep3P1PartsToBeProduced = new JLabel("250");
		lblStep3P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P1PartsToBeProduced.setBounds(115, 142, 29, 23);
		panelStep3.add(lblStep3P1PartsToBeProduced);

		JLabel lblStep3P2PartsToBeProduced = new JLabel("250");
		lblStep3P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P2PartsToBeProduced.setBounds(393, 142, 29, 23);
		panelStep3.add(lblStep3P2PartsToBeProduced);

		JLabel lblStep3P3PartsToBeProduced = new JLabel("250");
		lblStep3P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P3PartsToBeProduced.setBounds(673, 142, 29, 23);
		panelStep3.add(lblStep3P3PartsToBeProduced);

		JLabel lblStep3E4PartsToBeProduced = new JLabel("250");
		lblStep3E4PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E4PartsToBeProduced.setBounds(115, 164, 29, 23);
		panelStep3.add(lblStep3E4PartsToBeProduced);

		JLabel lblStep3E5PartsToBeProduced = new JLabel("250");
		lblStep3E5PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E5PartsToBeProduced.setBounds(393, 164, 29, 23);
		panelStep3.add(lblStep3E5PartsToBeProduced);

		JLabel lblStep3E6PartsToBeProduced = new JLabel("250");
		lblStep3E6PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E6PartsToBeProduced.setBounds(673, 164, 29, 23);
		panelStep3.add(lblStep3E6PartsToBeProduced);

		JLabel lblStep3E7PartsToBeProduced = new JLabel("250");
		lblStep3E7PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E7PartsToBeProduced.setBounds(115, 187, 29, 23);
		panelStep3.add(lblStep3E7PartsToBeProduced);

		JLabel lblStep3E8PartsToBeProduced = new JLabel("250");
		lblStep3E8PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E8PartsToBeProduced.setBounds(393, 187, 29, 23);
		panelStep3.add(lblStep3E8PartsToBeProduced);

		JLabel lblStep3E9PartsToBeProduced = new JLabel("250");
		lblStep3E9PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E9PartsToBeProduced.setBounds(673, 187, 29, 23);
		panelStep3.add(lblStep3E9PartsToBeProduced);

		JLabel lblStep3E10PartsToBeProduced = new JLabel("250");
		lblStep3E10PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E10PartsToBeProduced.setBounds(115, 209, 29, 23);
		panelStep3.add(lblStep3E10PartsToBeProduced);

		JLabel lblStep3E11PartsToBeProduced = new JLabel("250");
		lblStep3E11PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E11PartsToBeProduced.setBounds(393, 209, 29, 23);
		panelStep3.add(lblStep3E11PartsToBeProduced);

		JLabel lblStep3E12PartsToBeProduced = new JLabel("250");
		lblStep3E12PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E12PartsToBeProduced.setBounds(673, 209, 29, 23);
		panelStep3.add(lblStep3E12PartsToBeProduced);

		JLabel lblStep3E13PartsToBeProduced = new JLabel("250");
		lblStep3E13PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E13PartsToBeProduced.setBounds(115, 232, 29, 23);
		panelStep3.add(lblStep3E13PartsToBeProduced);

		JLabel lblStep3E14PartsToBeProduced = new JLabel("250");
		lblStep3E14PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E14PartsToBeProduced.setBounds(393, 232, 29, 23);
		panelStep3.add(lblStep3E14PartsToBeProduced);

		JLabel lblStep3E15PartsToBeProduced = new JLabel("250");
		lblStep3E15PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E15PartsToBeProduced.setBounds(673, 232, 29, 23);
		panelStep3.add(lblStep3E15PartsToBeProduced);

		JLabel lblStep3E16PartsToBeProduced = new JLabel("250");
		lblStep3E16PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E16PartsToBeProduced.setBounds(368, 60, 29, 23);
		panelStep3.add(lblStep3E16PartsToBeProduced);

		JLabel lblStep3E17PartsToBeProduced = new JLabel("250");
		lblStep3E17PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E17PartsToBeProduced.setBounds(500, 60, 29, 23);
		panelStep3.add(lblStep3E17PartsToBeProduced);

		JLabel lblStep3E18PartsToBeProduced = new JLabel("250");
		lblStep3E18PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E18PartsToBeProduced.setBounds(249, 142, 29, 23);
		panelStep3.add(lblStep3E18PartsToBeProduced);

		JLabel lblStep3E19PartsToBeProduced = new JLabel("250");
		lblStep3E19PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E19PartsToBeProduced.setBounds(527, 142, 29, 23);
		panelStep3.add(lblStep3E19PartsToBeProduced);

		JLabel lblStep3E20PartsToBeProduced = new JLabel("250");
		lblStep3E20PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E20PartsToBeProduced.setBounds(807, 142, 29, 23);
		panelStep3.add(lblStep3E20PartsToBeProduced);

		JLabel lblStep3E26PartsToBeProduced = new JLabel("250");
		lblStep3E26PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E26PartsToBeProduced.setBounds(639, 60, 29, 23);
		panelStep3.add(lblStep3E26PartsToBeProduced);

		JLabel lblStep3E29PartsToBeProduced = new JLabel("250");
		lblStep3E29PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E29PartsToBeProduced.setBounds(807, 165, 29, 23);
		panelStep3.add(lblStep3E29PartsToBeProduced);

		JLabel lblStep3E30PartsToBeProduced = new JLabel("250");
		lblStep3E30PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E30PartsToBeProduced.setBounds(807, 188, 29, 23);
		panelStep3.add(lblStep3E30PartsToBeProduced);

		JLabel lblStep3E31PartsToBeProduced = new JLabel("250");
		lblStep3E31PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E31PartsToBeProduced.setBounds(807, 211, 29, 23);
		panelStep3.add(lblStep3E31PartsToBeProduced);

		JLabel lblStep3E49PartsToBeProduced = new JLabel("250");
		lblStep3E49PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E49PartsToBeProduced.setBounds(249, 164, 29, 23);
		panelStep3.add(lblStep3E49PartsToBeProduced);

		JLabel lblStep3E50PartsToBeProduced = new JLabel("250");
		lblStep3E50PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E50PartsToBeProduced.setBounds(249, 187, 29, 23);
		panelStep3.add(lblStep3E50PartsToBeProduced);

		JLabel lblStep3E51PartsToBeProduced = new JLabel("250");
		lblStep3E51PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E51PartsToBeProduced.setBounds(249, 210, 29, 23);
		panelStep3.add(lblStep3E51PartsToBeProduced);

		JLabel lblStep3E54PartsToBeProduced = new JLabel("250");
		lblStep3E54PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E54PartsToBeProduced.setBounds(527, 165, 29, 23);
		panelStep3.add(lblStep3E54PartsToBeProduced);

		JLabel lblStep3E55PartsToBeProduced = new JLabel("250");
		lblStep3E55PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E55PartsToBeProduced.setBounds(527, 188, 29, 23);
		panelStep3.add(lblStep3E55PartsToBeProduced);

		JLabel lblStep3E56PartsToBeProduced = new JLabel("250");
		lblStep3E56PartsToBeProduced
				.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56PartsToBeProduced
				.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E56PartsToBeProduced.setBounds(527, 211, 29, 23);
		panelStep3.add(lblStep3E56PartsToBeProduced);

		JSpinner spinnerStep3P1 = new JSpinner();
		lblStep3P1.setLabelFor(spinnerStep3P1);
		spinnerStep3P1.setBounds(50, 145, 60, 20);
		panelStep3.add(spinnerStep3P1);

		JSpinner spinnerStep3P2 = new JSpinner();
		spinnerStep3P2.setBounds(328, 145, 60, 20);
		panelStep3.add(spinnerStep3P2);

		JSpinner spinnerStep3P3 = new JSpinner();
		spinnerStep3P3.setBounds(608, 145, 60, 20);
		panelStep3.add(spinnerStep3P3);

		JSpinner spinnerStep3E4 = new JSpinner();
		spinnerStep3E4.setBounds(50, 167, 60, 20);
		panelStep3.add(spinnerStep3E4);

		JSpinner spinnerStep3E5 = new JSpinner();
		spinnerStep3E5.setBounds(328, 167, 60, 20);
		panelStep3.add(spinnerStep3E5);

		JSpinner spinnerStep3E6 = new JSpinner();
		spinnerStep3E6.setBounds(608, 167, 60, 20);
		panelStep3.add(spinnerStep3E6);

		JSpinner spinnerStep3E7 = new JSpinner();
		spinnerStep3E7.setBounds(50, 190, 60, 20);
		panelStep3.add(spinnerStep3E7);

		JSpinner spinnerStep3E8 = new JSpinner();
		spinnerStep3E8.setBounds(328, 190, 60, 20);
		panelStep3.add(spinnerStep3E8);

		JSpinner spinnerStep3E9 = new JSpinner();
		spinnerStep3E9.setBounds(608, 190, 60, 20);
		panelStep3.add(spinnerStep3E9);

		JSpinner spinnerStep3E10 = new JSpinner();
		spinnerStep3E10.setBounds(50, 212, 60, 20);
		panelStep3.add(spinnerStep3E10);

		JSpinner spinnerStep3E11 = new JSpinner();
		spinnerStep3E11.setBounds(328, 212, 60, 20);
		panelStep3.add(spinnerStep3E11);

		JSpinner spinnerStep3E12 = new JSpinner();
		spinnerStep3E12.setBounds(608, 212, 60, 20);
		panelStep3.add(spinnerStep3E12);

		JSpinner spinnerStep3E13 = new JSpinner();
		spinnerStep3E13.setBounds(50, 235, 60, 20);
		panelStep3.add(spinnerStep3E13);

		JSpinner spinnerStep3E14 = new JSpinner();
		spinnerStep3E14.setBounds(328, 235, 60, 20);
		panelStep3.add(spinnerStep3E14);

		JSpinner spinnerStep3E15 = new JSpinner();
		spinnerStep3E15.setBounds(608, 235, 60, 20);
		panelStep3.add(spinnerStep3E15);

		JSpinner spinnerStep3E16 = new JSpinner();
		spinnerStep3E16.setBounds(303, 63, 60, 20);
		panelStep3.add(spinnerStep3E16);

		JSpinner spinnerStep3E17 = new JSpinner();
		spinnerStep3E17.setBounds(433, 63, 60, 20);
		panelStep3.add(spinnerStep3E17);

		JSpinner spinnerStep3E18 = new JSpinner();
		spinnerStep3E18.setBounds(184, 145, 60, 20);
		panelStep3.add(spinnerStep3E18);

		JSpinner spinnerStep3E19 = new JSpinner();
		spinnerStep3E19.setBounds(462, 145, 60, 20);
		panelStep3.add(spinnerStep3E19);

		JSpinner spinnerStep3E20 = new JSpinner();
		spinnerStep3E20.setBounds(742, 145, 60, 20);
		panelStep3.add(spinnerStep3E20);

		JSpinner spinnerStep3E26 = new JSpinner();
		spinnerStep3E26.setBounds(569, 63, 60, 20);
		panelStep3.add(spinnerStep3E26);

		JSpinner spinnerStep3E29 = new JSpinner();
		spinnerStep3E29.setBounds(742, 168, 60, 20);
		panelStep3.add(spinnerStep3E29);

		JSpinner spinnerStep3E30 = new JSpinner();
		spinnerStep3E30.setBounds(742, 191, 60, 20);
		panelStep3.add(spinnerStep3E30);

		JSpinner spinnerStep3E31 = new JSpinner();
		spinnerStep3E31.setBounds(742, 213, 60, 20);
		panelStep3.add(spinnerStep3E31);

		JSpinner spinnerStep3E49 = new JSpinner();
		spinnerStep3E49.setBounds(184, 167, 60, 20);
		panelStep3.add(spinnerStep3E49);

		JSpinner spinnerStep3E50 = new JSpinner();
		spinnerStep3E50.setBounds(184, 190, 60, 20);
		panelStep3.add(spinnerStep3E50);

		JSpinner spinnerStep3E51 = new JSpinner();
		spinnerStep3E51.setBounds(184, 212, 60, 20);
		panelStep3.add(spinnerStep3E51);

		JSpinner spinnerStep3E54 = new JSpinner();
		spinnerStep3E54.setBounds(462, 168, 60, 20);
		panelStep3.add(spinnerStep3E54);

		JSpinner spinnerStep3E55 = new JSpinner();
		spinnerStep3E55.setBounds(462, 191, 60, 20);
		panelStep3.add(spinnerStep3E55);

		JSpinner spinnerStep3E56 = new JSpinner();
		spinnerStep3E56.setBounds(462, 213, 60, 20);
		panelStep3.add(spinnerStep3E56);

		JButton btnStep3NextStep = new JButton(
				"Schritt 4: Auftragsreihenfolge >>");
		btnStep3NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep3NextStep.setBounds(616, 266, 220, 35);
		panelStep3.add(btnStep3NextStep);

		JButton btnStep3PrevStep = new JButton("<< Schritt 2: Vertriebswunsch");
		btnStep3PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep3PrevStep.setBounds(10, 266, 220, 35);
		panelStep3.add(btnStep3PrevStep);

		JButton btnStep3Recalculate = new JButton("Neu berechnen");
		btnStep3Recalculate.setBounds(328, 266, 182, 35);
		panelStep3.add(btnStep3Recalculate);

		JPanel panelStep4 = new JPanel();
		tabbedPanePlanning.addTab("Schritt 4", null, panelStep4, null);
		panelStep4.setLayout(null);

		JLabel lblSchrittAuftragreihenfolge = new JLabel(
				"Schritt 4: Auftragsreihenfolge");
		lblSchrittAuftragreihenfolge
				.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittAuftragreihenfolge.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittAuftragreihenfolge.setBounds(10, 11, 826, 23);
		panelStep4.add(lblSchrittAuftragreihenfolge);

		JButton btnStep4PrevStep = new JButton("<< Schritt 3: Planbestand");
		btnStep4PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep4PrevStep.setBounds(10, 268, 220, 35);
		panelStep4.add(btnStep4PrevStep);

		JButton btnStep4NextStep = new JButton("Schritt 5: Kapazitäten >>");
		btnStep4NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep4NextStep.setBounds(616, 268, 220, 35);
		panelStep4.add(btnStep4NextStep);

		JList list = new JList();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "E4 -- 50x", "E5 -- 100x" };

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
		btnNewButton.setIcon(new ImageIcon(PlanungstoolGUI.class
				.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnNewButton.setBounds(417, 82, 124, 35);
		panelStep4.add(btnNewButton);

		JButton btnSplitten = new JButton("Splitten");
		btnSplitten.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSplitten.setBounds(417, 128, 124, 35);
		panelStep4.add(btnSplitten);

		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(PlanungstoolGUI.class
				.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		button_2.setBounds(417, 174, 124, 35);
		panelStep4.add(button_2);

		JPanel panelStep5 = new JPanel();
		tabbedPanePlanning.addTab("Schritt 5", null, panelStep5, null);
		panelStep5.setLayout(null);

		JLabel lblSchrittKapazittsplanung = new JLabel(
				"Schritt 5: Kapazitätsplanung");
		lblSchrittKapazittsplanung
				.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittKapazittsplanung.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittKapazittsplanung.setBounds(10, 11, 826, 23);
		panelStep5.add(lblSchrittKapazittsplanung);

		JLabel lblStep5TitleWorkplace = new JLabel("Arbeitsplatz");
		lblStep5TitleWorkplace.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleWorkplace.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleWorkplace.setBounds(10, 45, 108, 23);
		panelStep5.add(lblStep5TitleWorkplace);

		JLabel lblStep5TitleShift = new JLabel("Schichten");
		lblStep5TitleShift.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleShift.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleShift.setBounds(128, 45, 89, 23);
		panelStep5.add(lblStep5TitleShift);

		JLabel lblStep5TitleOvertime = new JLabel("Überstunden");
		lblStep5TitleOvertime.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleOvertime.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleOvertime.setBounds(213, 45, 108, 23);
		panelStep5.add(lblStep5TitleOvertime);

		JLabel lblStep5Workplace1 = new JLabel("1");
		lblStep5Workplace1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace1.setBounds(20, 76, 98, 23);
		panelStep5.add(lblStep5Workplace1);

		JSpinner spinnerStep5ShiftWorkplace1 = new JSpinner();
		spinnerStep5ShiftWorkplace1.setBounds(148, 79, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace1);

		JSpinner spinnerStep5OvertimeWorkplace1 = new JSpinner();
		spinnerStep5OvertimeWorkplace1.setBounds(213, 79, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace1);

		JLabel lblStep5Workplace2 = new JLabel("2");
		lblStep5Workplace2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace2.setBounds(20, 98, 98, 23);
		panelStep5.add(lblStep5Workplace2);

		JSpinner spinnerStep5ShiftWorkplace2 = new JSpinner();
		spinnerStep5ShiftWorkplace2.setBounds(148, 101, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace2);

		JSpinner spinnerStep5OvertimeWorkplace2 = new JSpinner();
		spinnerStep5OvertimeWorkplace2.setBounds(213, 101, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace2);

		JLabel lblStep5Workplace3 = new JLabel("3");
		lblStep5Workplace3.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace3.setBounds(20, 121, 98, 23);
		panelStep5.add(lblStep5Workplace3);

		JSpinner spinnerStep5ShiftWorkplace3 = new JSpinner();
		spinnerStep5ShiftWorkplace3.setBounds(148, 124, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace3);

		JSpinner spinnerStep5OvertimeWorkplace3 = new JSpinner();
		spinnerStep5OvertimeWorkplace3.setBounds(213, 124, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace3);

		JLabel lblStep5Workplace4 = new JLabel("4");
		lblStep5Workplace4.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace4.setBounds(20, 144, 98, 23);
		panelStep5.add(lblStep5Workplace4);

		JSpinner spinnerStep5ShiftWorkplace4 = new JSpinner();
		spinnerStep5ShiftWorkplace4.setBounds(148, 147, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace4);

		JSpinner spinnerStep5OvertimeWorkplace4 = new JSpinner();
		spinnerStep5OvertimeWorkplace4.setBounds(213, 147, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace4);

		JLabel lblStep5Workplace6 = new JLabel("6");
		lblStep5Workplace6.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace6.setBounds(20, 167, 98, 23);
		panelStep5.add(lblStep5Workplace6);

		JSpinner spinnerStep5ShiftWorkplace6 = new JSpinner();
		spinnerStep5ShiftWorkplace6.setBounds(148, 170, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace6);

		JSpinner spinnerStep5OvertimeWorkplace6 = new JSpinner();
		spinnerStep5OvertimeWorkplace6.setBounds(213, 170, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace6);

		JLabel lblStep5Workplace7 = new JLabel("7");
		lblStep5Workplace7.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace7.setBounds(20, 191, 98, 23);
		panelStep5.add(lblStep5Workplace7);

		JSpinner spinnerStep5ShiftWorkplace7 = new JSpinner();
		spinnerStep5ShiftWorkplace7.setBounds(148, 194, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace7);

		JSpinner spinnerStep5OvertimeWorkplace7 = new JSpinner();
		spinnerStep5OvertimeWorkplace7.setBounds(213, 194, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace7);

		JLabel lblStep5Workplace8 = new JLabel("8");
		lblStep5Workplace8.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace8.setBounds(20, 214, 98, 23);
		panelStep5.add(lblStep5Workplace8);

		JSpinner spinnerStep5ShiftWorkplace8 = new JSpinner();
		spinnerStep5ShiftWorkplace8.setBounds(148, 217, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace8);

		JSpinner spinnerStep5OvertimeWorkplace8 = new JSpinner();
		spinnerStep5OvertimeWorkplace8.setBounds(213, 217, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace8);

		JButton btnStep5PrevStep = new JButton(
				"<< Schritt 4: Auftragsreihenfolge");
		btnStep5PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep5PrevStep.setBounds(10, 268, 220, 35);
		panelStep5.add(btnStep5PrevStep);

		JButton btnStep5NextStep = new JButton("Schritt 6: Bestellungen >>");
		btnStep5NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep5NextStep.setBounds(616, 268, 220, 35);
		panelStep5.add(btnStep5NextStep);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(545, 217, 108, 20);
		panelStep5.add(spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(480, 217, 52, 20);
		panelStep5.add(spinner_1);

		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(480, 194, 52, 20);
		panelStep5.add(spinner_2);

		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(480, 170, 52, 20);
		panelStep5.add(spinner_3);

		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(480, 147, 52, 20);
		panelStep5.add(spinner_4);

		JSpinner spinner_5 = new JSpinner();
		spinner_5.setBounds(480, 124, 52, 20);
		panelStep5.add(spinner_5);

		JSpinner spinner_6 = new JSpinner();
		spinner_6.setBounds(480, 101, 52, 20);
		panelStep5.add(spinner_6);

		JSpinner spinner_7 = new JSpinner();
		spinner_7.setBounds(480, 79, 52, 20);
		panelStep5.add(spinner_7);

		JLabel label = new JLabel("15");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(352, 214, 98, 23);
		panelStep5.add(label);

		JLabel label_1 = new JLabel("14");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(352, 191, 98, 23);
		panelStep5.add(label_1);

		JLabel label_2 = new JLabel("13");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_2.setBounds(352, 167, 98, 23);
		panelStep5.add(label_2);

		JLabel label_3 = new JLabel("12");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_3.setBounds(352, 144, 98, 23);
		panelStep5.add(label_3);

		JLabel label_4 = new JLabel("11");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_4.setBounds(352, 121, 98, 23);
		panelStep5.add(label_4);

		JLabel label_5 = new JLabel("10");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_5.setBounds(352, 98, 98, 23);
		panelStep5.add(label_5);

		JLabel label_6 = new JLabel("9");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_6.setBounds(352, 76, 98, 23);
		panelStep5.add(label_6);

		JLabel lblStep5TitleWorkplace2 = new JLabel("Arbeitsplatz");
		lblStep5TitleWorkplace2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleWorkplace2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleWorkplace2.setBounds(342, 45, 108, 23);
		panelStep5.add(lblStep5TitleWorkplace2);

		JLabel lblStep5TitleShift2 = new JLabel("Schichten");
		lblStep5TitleShift2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleShift2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleShift2.setBounds(460, 45, 89, 23);
		panelStep5.add(lblStep5TitleShift2);

		JLabel label_9 = new JLabel("Überstunden");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_9.setBounds(545, 45, 108, 23);
		panelStep5.add(label_9);

		JSpinner spinner_8 = new JSpinner();
		spinner_8.setBounds(545, 194, 108, 20);
		panelStep5.add(spinner_8);

		JSpinner spinner_9 = new JSpinner();
		spinner_9.setBounds(545, 170, 108, 20);
		panelStep5.add(spinner_9);

		JSpinner spinner_10 = new JSpinner();
		spinner_10.setBounds(545, 147, 108, 20);
		panelStep5.add(spinner_10);

		JSpinner spinner_11 = new JSpinner();
		spinner_11.setBounds(545, 124, 108, 20);
		panelStep5.add(spinner_11);

		JSpinner spinner_12 = new JSpinner();
		spinner_12.setBounds(545, 101, 108, 20);
		panelStep5.add(spinner_12);

		JSpinner spinner_13 = new JSpinner();
		spinner_13.setBounds(545, 79, 108, 20);
		panelStep5.add(spinner_13);

		JPanel panelStep6 = new JPanel();
		tabbedPanePlanning.addTab("Schritt 6", null, panelStep6, null);
		panelStep6.setLayout(null);

		JLabel lblSchrittBestellungen = new JLabel("Schritt 6: Bestellungen");
		lblSchrittBestellungen.setHorizontalAlignment(SwingConstants.CENTER);
		lblSchrittBestellungen.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSchrittBestellungen.setBounds(10, 11, 826, 23);
		panelStep6.add(lblSchrittBestellungen);

		textField = new JTextField();
		textField.setBounds(10, 45, 56, 20);
		panelStep6.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(70, 45, 56, 20);
		panelStep6.add(textField_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox.setBounds(136, 45, 73, 20);
		panelStep6.add(comboBox);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(10, 76, 56, 20);
		panelStep6.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(70, 76, 56, 20);
		panelStep6.add(textField_3);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_1.setBounds(136, 76, 73, 20);
		panelStep6.add(comboBox_1);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(10, 107, 56, 20);
		panelStep6.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(70, 107, 56, 20);
		panelStep6.add(textField_5);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_2.setBounds(136, 107, 73, 20);
		panelStep6.add(comboBox_2);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(10, 138, 56, 20);
		panelStep6.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(70, 138, 56, 20);
		panelStep6.add(textField_7);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_3.setBounds(136, 138, 73, 20);
		panelStep6.add(comboBox_3);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(10, 169, 56, 20);
		panelStep6.add(textField_8);

		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(70, 169, 56, 20);
		panelStep6.add(textField_9);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_4.setBounds(136, 169, 73, 20);
		panelStep6.add(comboBox_4);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(10, 200, 56, 20);
		panelStep6.add(textField_10);

		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(70, 200, 56, 20);
		panelStep6.add(textField_11);

		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_5.setBounds(136, 200, 73, 20);
		panelStep6.add(comboBox_5);

		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(10, 231, 56, 20);
		panelStep6.add(textField_12);

		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(70, 231, 56, 20);
		panelStep6.add(textField_13);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_6.setBounds(136, 231, 73, 20);
		panelStep6.add(comboBox_6);

		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(219, 231, 56, 20);
		panelStep6.add(textField_14);

		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(219, 200, 56, 20);
		panelStep6.add(textField_15);

		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(219, 169, 56, 20);
		panelStep6.add(textField_16);

		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(219, 138, 56, 20);
		panelStep6.add(textField_17);

		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(219, 107, 56, 20);
		panelStep6.add(textField_18);

		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(219, 76, 56, 20);
		panelStep6.add(textField_19);

		textField_20 = new JTextField();
		textField_20.setColumns(10);
		textField_20.setBounds(219, 45, 56, 20);
		panelStep6.add(textField_20);

		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBounds(279, 45, 56, 20);
		panelStep6.add(textField_21);

		textField_22 = new JTextField();
		textField_22.setColumns(10);
		textField_22.setBounds(279, 76, 56, 20);
		panelStep6.add(textField_22);

		textField_23 = new JTextField();
		textField_23.setColumns(10);
		textField_23.setBounds(279, 107, 56, 20);
		panelStep6.add(textField_23);

		textField_24 = new JTextField();
		textField_24.setColumns(10);
		textField_24.setBounds(279, 138, 56, 20);
		panelStep6.add(textField_24);

		textField_25 = new JTextField();
		textField_25.setColumns(10);
		textField_25.setBounds(279, 169, 56, 20);
		panelStep6.add(textField_25);

		textField_26 = new JTextField();
		textField_26.setColumns(10);
		textField_26.setBounds(279, 200, 56, 20);
		panelStep6.add(textField_26);

		textField_27 = new JTextField();
		textField_27.setColumns(10);
		textField_27.setBounds(279, 231, 56, 20);
		panelStep6.add(textField_27);

		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_7.setBounds(345, 231, 73, 20);
		panelStep6.add(comboBox_7);

		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_8.setBounds(345, 200, 73, 20);
		panelStep6.add(comboBox_8);

		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_9.setBounds(345, 169, 73, 20);
		panelStep6.add(comboBox_9);

		JComboBox comboBox_10 = new JComboBox();
		comboBox_10.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_10.setBounds(345, 138, 73, 20);
		panelStep6.add(comboBox_10);

		JComboBox comboBox_11 = new JComboBox();
		comboBox_11.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_11.setBounds(345, 107, 73, 20);
		panelStep6.add(comboBox_11);

		JComboBox comboBox_12 = new JComboBox();
		comboBox_12.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_12.setBounds(345, 76, 73, 20);
		panelStep6.add(comboBox_12);

		JComboBox comboBox_13 = new JComboBox();
		comboBox_13.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_13.setBounds(345, 45, 73, 20);
		panelStep6.add(comboBox_13);

		textField_28 = new JTextField();
		textField_28.setColumns(10);
		textField_28.setBounds(434, 231, 56, 20);
		panelStep6.add(textField_28);

		textField_29 = new JTextField();
		textField_29.setColumns(10);
		textField_29.setBounds(434, 200, 56, 20);
		panelStep6.add(textField_29);

		textField_30 = new JTextField();
		textField_30.setColumns(10);
		textField_30.setBounds(434, 169, 56, 20);
		panelStep6.add(textField_30);

		textField_31 = new JTextField();
		textField_31.setColumns(10);
		textField_31.setBounds(434, 138, 56, 20);
		panelStep6.add(textField_31);

		textField_32 = new JTextField();
		textField_32.setColumns(10);
		textField_32.setBounds(434, 107, 56, 20);
		panelStep6.add(textField_32);

		textField_33 = new JTextField();
		textField_33.setColumns(10);
		textField_33.setBounds(434, 76, 56, 20);
		panelStep6.add(textField_33);

		textField_34 = new JTextField();
		textField_34.setColumns(10);
		textField_34.setBounds(434, 45, 56, 20);
		panelStep6.add(textField_34);

		textField_35 = new JTextField();
		textField_35.setColumns(10);
		textField_35.setBounds(494, 45, 56, 20);
		panelStep6.add(textField_35);

		textField_36 = new JTextField();
		textField_36.setColumns(10);
		textField_36.setBounds(494, 76, 56, 20);
		panelStep6.add(textField_36);

		textField_37 = new JTextField();
		textField_37.setColumns(10);
		textField_37.setBounds(494, 107, 56, 20);
		panelStep6.add(textField_37);

		textField_38 = new JTextField();
		textField_38.setColumns(10);
		textField_38.setBounds(494, 138, 56, 20);
		panelStep6.add(textField_38);

		textField_39 = new JTextField();
		textField_39.setColumns(10);
		textField_39.setBounds(494, 169, 56, 20);
		panelStep6.add(textField_39);

		textField_40 = new JTextField();
		textField_40.setColumns(10);
		textField_40.setBounds(494, 200, 56, 20);
		panelStep6.add(textField_40);

		textField_41 = new JTextField();
		textField_41.setColumns(10);
		textField_41.setBounds(494, 231, 56, 20);
		panelStep6.add(textField_41);

		JComboBox comboBox_14 = new JComboBox();
		comboBox_14.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_14.setBounds(560, 231, 73, 20);
		panelStep6.add(comboBox_14);

		JComboBox comboBox_15 = new JComboBox();
		comboBox_15.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_15.setBounds(560, 200, 73, 20);
		panelStep6.add(comboBox_15);

		JComboBox comboBox_16 = new JComboBox();
		comboBox_16.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_16.setBounds(560, 169, 73, 20);
		panelStep6.add(comboBox_16);

		JComboBox comboBox_17 = new JComboBox();
		comboBox_17.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_17.setBounds(560, 138, 73, 20);
		panelStep6.add(comboBox_17);

		JComboBox comboBox_18 = new JComboBox();
		comboBox_18.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_18.setBounds(560, 107, 73, 20);
		panelStep6.add(comboBox_18);

		JComboBox comboBox_19 = new JComboBox();
		comboBox_19.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_19.setBounds(560, 76, 73, 20);
		panelStep6.add(comboBox_19);

		JComboBox comboBox_20 = new JComboBox();
		comboBox_20.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBox_20.setBounds(560, 45, 73, 20);
		panelStep6.add(comboBox_20);

		textField_42 = new JTextField();
		textField_42.setColumns(10);
		textField_42.setBounds(637, 231, 56, 20);
		panelStep6.add(textField_42);

		textField_43 = new JTextField();
		textField_43.setColumns(10);
		textField_43.setBounds(637, 200, 56, 20);
		panelStep6.add(textField_43);

		textField_44 = new JTextField();
		textField_44.setColumns(10);
		textField_44.setBounds(637, 169, 56, 20);
		panelStep6.add(textField_44);

		textField_45 = new JTextField();
		textField_45.setColumns(10);
		textField_45.setBounds(637, 138, 56, 20);
		panelStep6.add(textField_45);

		textField_46 = new JTextField();
		textField_46.setColumns(10);
		textField_46.setBounds(637, 107, 56, 20);
		panelStep6.add(textField_46);

		textField_47 = new JTextField();
		textField_47.setColumns(10);
		textField_47.setBounds(637, 76, 56, 20);
		panelStep6.add(textField_47);

		textField_48 = new JTextField();
		textField_48.setColumns(10);
		textField_48.setBounds(637, 45, 56, 20);
		panelStep6.add(textField_48);

		textField_49 = new JTextField();
		textField_49.setColumns(10);
		textField_49.setBounds(697, 45, 56, 20);
		panelStep6.add(textField_49);

		textField_50 = new JTextField();
		textField_50.setColumns(10);
		textField_50.setBounds(697, 76, 56, 20);
		panelStep6.add(textField_50);

		textField_51 = new JTextField();
		textField_51.setColumns(10);
		textField_51.setBounds(697, 107, 56, 20);
		panelStep6.add(textField_51);

		textField_52 = new JTextField();
		textField_52.setColumns(10);
		textField_52.setBounds(697, 138, 56, 20);
		panelStep6.add(textField_52);

		textField_53 = new JTextField();
		textField_53.setColumns(10);
		textField_53.setBounds(697, 169, 56, 20);
		panelStep6.add(textField_53);

		textField_54 = new JTextField();
		textField_54.setColumns(10);
		textField_54.setBounds(697, 200, 56, 20);
		panelStep6.add(textField_54);

		textField_55 = new JTextField();
		textField_55.setColumns(10);
		textField_55.setBounds(697, 231, 56, 20);
		panelStep6.add(textField_55);

		JComboBox comboBox_21 = new JComboBox();
		comboBox_21.setBounds(763, 231, 73, 20);
		panelStep6.add(comboBox_21);

		JComboBox comboBox_22 = new JComboBox();
		comboBox_22.setBounds(763, 200, 73, 20);
		panelStep6.add(comboBox_22);

		JComboBox comboBox_23 = new JComboBox();
		comboBox_23.setBounds(763, 169, 73, 20);
		panelStep6.add(comboBox_23);

		JComboBox comboBox_24 = new JComboBox();
		comboBox_24.setBounds(763, 138, 73, 20);
		panelStep6.add(comboBox_24);

		JComboBox comboBox_25 = new JComboBox();
		comboBox_25.setBounds(763, 107, 73, 20);
		panelStep6.add(comboBox_25);

		JComboBox comboBox_26 = new JComboBox();
		comboBox_26.setBounds(763, 76, 73, 20);
		panelStep6.add(comboBox_26);

		JComboBox comboBox_27 = new JComboBox();
		comboBox_27.setBounds(763, 45, 73, 20);
		panelStep6.add(comboBox_27);

		JButton btnStep6PrevStep = new JButton(
				"<< Schritt 5: Kapazitätsplanung");
		btnStep6PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep6PrevStep.setBounds(10, 268, 220, 35);
		panelStep6.add(btnStep6PrevStep);
		internalFrame_1.setVisible(true);

		JLabel lblPP = new JLabel("P1 - P3");
		lblPP.setHorizontalAlignment(SwingConstants.CENTER);
		lblPP.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPP.setBounds(173, 60, 90, 23);
		panelStep3.add(lblPP);

		// ENDE GUI BUILDER

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// MANUELLE KONFIGURATION DER UI ITEMS

		// PREPARE GUI
		tabbedPanePlanning.removeAll();

		// BUILD STEPS MAP FOR ACCESSING STEP PANELS BY INDEX
		stepsMap.put(1, panelStep1);
		stepsMap.put(2, panelStep2);
		stepsMap.put(3, panelStep3);
		stepsMap.put(4, panelStep4);
		stepsMap.put(5, panelStep5);
		stepsMap.put(6, panelStep6);

		safetyStockFormular.put("P1", new SafetyStockEntity(lblStep3P1,
				spinnerStep3P1, lblStep3P1PartsToBeProduced));
		safetyStockFormular.put("P2", new SafetyStockEntity(lblStep3P2,
				spinnerStep3P2, lblStep3P2PartsToBeProduced));
		safetyStockFormular.put("P3", new SafetyStockEntity(lblStep3P3,
				spinnerStep3P3, lblStep3P3PartsToBeProduced));
		safetyStockFormular.put("E4", new SafetyStockEntity(lblStep3E4,
				spinnerStep3E4, lblStep3E4PartsToBeProduced));
		safetyStockFormular.put("E5", new SafetyStockEntity(lblStep3E5,
				spinnerStep3E5, lblStep3E5PartsToBeProduced));
		safetyStockFormular.put("E6", new SafetyStockEntity(lblStep3E6,
				spinnerStep3E6, lblStep3E6PartsToBeProduced));
		safetyStockFormular.put("E7", new SafetyStockEntity(lblStep3E7,
				spinnerStep3E7, lblStep3E7PartsToBeProduced));
		safetyStockFormular.put("E8", new SafetyStockEntity(lblStep3E8,
				spinnerStep3E8, lblStep3E8PartsToBeProduced));
		safetyStockFormular.put("E9", new SafetyStockEntity(lblStep3E9,
				spinnerStep3E9, lblStep3E9PartsToBeProduced));
		safetyStockFormular.put("E10", new SafetyStockEntity(lblStep3E10,
				spinnerStep3E10, lblStep3E10PartsToBeProduced));
		safetyStockFormular.put("E11", new SafetyStockEntity(lblStep3E11,
				spinnerStep3E11, lblStep3E11PartsToBeProduced));
		safetyStockFormular.put("E12", new SafetyStockEntity(lblStep3E12,
				spinnerStep3E12, lblStep3E12PartsToBeProduced));
		safetyStockFormular.put("E13", new SafetyStockEntity(lblStep3E13,
				spinnerStep3E13, lblStep3E13PartsToBeProduced));
		safetyStockFormular.put("E14", new SafetyStockEntity(lblStep3E14,
				spinnerStep3E14, lblStep3E14PartsToBeProduced));
		safetyStockFormular.put("E15", new SafetyStockEntity(lblStep3E15,
				spinnerStep3E15, lblStep3E15PartsToBeProduced));
		safetyStockFormular.put("E16", new SafetyStockEntity(lblStep3E16,
				spinnerStep3E16, lblStep3E16PartsToBeProduced));
		safetyStockFormular.put("E17", new SafetyStockEntity(lblStep3E17,
				spinnerStep3E17, lblStep3E17PartsToBeProduced));
		safetyStockFormular.put("E18", new SafetyStockEntity(lblStep3E18,
				spinnerStep3E18, lblStep3E18PartsToBeProduced));
		safetyStockFormular.put("E19", new SafetyStockEntity(lblStep3E19,
				spinnerStep3E19, lblStep3E19PartsToBeProduced));
		safetyStockFormular.put("E20", new SafetyStockEntity(lblStep3E20,
				spinnerStep3E20, lblStep3E20PartsToBeProduced));
		safetyStockFormular.put("E26", new SafetyStockEntity(lblStep3E26,
				spinnerStep3E26, lblStep3E26PartsToBeProduced));
		safetyStockFormular.put("E29", new SafetyStockEntity(lblStep3E29,
				spinnerStep3E29, lblStep3E29PartsToBeProduced));
		safetyStockFormular.put("E30", new SafetyStockEntity(lblStep3E30,
				spinnerStep3E30, lblStep3E30PartsToBeProduced));
		safetyStockFormular.put("E31", new SafetyStockEntity(lblStep3E31,
				spinnerStep3E31, lblStep3E31PartsToBeProduced));
		safetyStockFormular.put("E49", new SafetyStockEntity(lblStep3E49,
				spinnerStep3E49, lblStep3E49PartsToBeProduced));
		safetyStockFormular.put("E50", new SafetyStockEntity(lblStep3E50,
				spinnerStep3E50, lblStep3E50PartsToBeProduced));
		safetyStockFormular.put("E51", new SafetyStockEntity(lblStep3E51,
				spinnerStep3E51, lblStep3E51PartsToBeProduced));
		safetyStockFormular.put("E54", new SafetyStockEntity(lblStep3E54,
				spinnerStep3E54, lblStep3E54PartsToBeProduced));
		safetyStockFormular.put("E55", new SafetyStockEntity(lblStep3E55,
				spinnerStep3E55, lblStep3E55PartsToBeProduced));
		safetyStockFormular.put("E56", new SafetyStockEntity(lblStep3E56,
				spinnerStep3E56, lblStep3E56PartsToBeProduced));

		// INITIALIZE ACTION LISTENERS
		ActionListener switchStepsButtonActionListener = new StepButtonsActionDialog(
				this);

		// SET ACTION LISTENERS
		btnStep1NextStep.addActionListener(switchStepsButtonActionListener);
		btnStep2PrevStep.addActionListener(switchStepsButtonActionListener);
		btnStep2NextStep.addActionListener(switchStepsButtonActionListener);
		btnStep3PrevStep.addActionListener(switchStepsButtonActionListener);
		btnStep3NextStep.addActionListener(switchStepsButtonActionListener);
		btnStep4PrevStep.addActionListener(switchStepsButtonActionListener);
		btnStep4NextStep.addActionListener(switchStepsButtonActionListener);
		btnStep5PrevStep.addActionListener(switchStepsButtonActionListener);
		btnStep5NextStep.addActionListener(switchStepsButtonActionListener);
		btnStep6PrevStep.addActionListener(switchStepsButtonActionListener);

		btnStep1NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInForecasts();
			}
		});

		btnStep2NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSales();
				calculateSafetyStock();
			}
		});

		btnStep3Recalculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSales();
				readInSafetyStock();
				calculateSafetyStock();

			}
		});
		
		displaySafetyStock();

	}

	protected void calculateSafetyStock() {

		ProductionService productionService = null;

		try {
			productionService = new ProductionService(results);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		productionService.setForecast(userInput.getForecast());
		productionService.setSales(userInput.getSales(),
				userInput.getDirectSales());
		Map<String, Integer> safetyStockGui = productionService
				.calculateSafetyStock(userInput.getSafetyStock());

		userInput.getSafetyStock().setSafetyStocks(safetyStockGui);

		this.displaySafetyStock();

	}

	protected void setResults(Results results2) {
		this.results = results2;
	}

	protected void readInForecasts() {

		Sales salesPeriode1 = new Sales(
				(int) spinnerStep1P1Periode1.getValue(),
				(int) spinnerStep1P2Periode1.getValue(),
				(int) spinnerStep1P3Periode1.getValue());

		Sales salesPeriode2 = new Sales(
				(int) spinnerStep1P1Periode2.getValue(),
				(int) spinnerStep1P2Periode2.getValue(),
				(int) spinnerStep1P3Periode2.getValue());
		Sales salesPeriode3 = new Sales(
				(int) spinnerStep1P1Periode3.getValue(),
				(int) spinnerStep1P2Periode3.getValue(),
				(int) spinnerStep1P3Periode3.getValue());
		Sales salesPeriode4 = new Sales(
				(int) spinnerStep1P1Periode4.getValue(),
				(int) spinnerStep1P2Periode4.getValue(),
				(int) spinnerStep1P3Periode4.getValue());

		Forecast forecast = new Forecast(salesPeriode1, salesPeriode2,
				salesPeriode3, salesPeriode4);

		userInput.setForecast(forecast);

	}

	protected void readInSales() {
		Sales sales = new Sales((int) spinnerStep2P1Sales.getValue(),
				(int) spinnerStep2P2Sales.getValue(),
				(int) spinnerStep2P3Sales.getValue());

		Sales directSales = new Sales(
				(int) spinnerStep2P1DirectSales.getValue(),
				(int) spinnerStep2P2DirectSales.getValue(),
				(int) spinnerStep2P3DirectSales.getValue());

		userInput.setSales(sales);
		userInput.setDirectSales(directSales);
	}

	protected void readInSafetyStock() {

		for (String id : safetyStockFormular.keySet()) {

			SafetyStockEntity safetyStockEntity = safetyStockFormular.get(id);
			userInput.getSafetyStock().getSafetyStocks()
					.put(id, (Integer) safetyStockEntity.getWish().getValue());

			System.out.println(id + ": "
					+ userInput.getSafetyStock().getStock(id));
		}
	}

	protected void displaySafetyStock() {

		for (String id : userInput.getSafetyStock().getSafetyStocks().keySet()) {

			safetyStockFormular
					.get(id)
					.getCalculated()
					.setText(
							userInput.getSafetyStock().getSafetyStocks()
									.get(id).toString());
			;
		}
	}

	protected void displayPeriod(int period) {

		lblStep1Periode1Title.setText("Periode " + (period + 1));
		lblStep1Periode2Title.setText("Periode " + (period + 2));
		lblStep1Periode3Title.setText("Periode " + (period + 3));
		lblStep1Periode4Title.setText("Periode " + (period + 4));
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

	private void switchToStep(int goToStep) {
		JPanel stepToShow = stepsMap.get(goToStep);
		tabbedPanePlanning.removeAll();
		tabbedPanePlanning.add(stepToShow);
		tabbedPanePlanning.addTab("Schritt " + goToStep, stepToShow);
	}

	public class StepButtonsActionDialog implements ActionListener {

		PlanungstoolGUI parent;

		public StepButtonsActionDialog(PlanungstoolGUI parent) {
			super();
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Object src = e.getSource();

			if (src instanceof JButton) {
				Integer goToStep = getNumberFromString(((JButton) src)
						.getText());
				if (goToStep != null) {
					switchToStep(goToStep);
				} else {
					return;
				}
			}

			return;
		}

		private Integer getNumberFromString(String s) {

			String pattern = ".*(\\d+).*";
			Matcher m = Pattern.compile(pattern).matcher(s);

			if (m.find()) {
				return Integer.parseInt(m.group(1));
			}

			return null;
		}
	}
}
