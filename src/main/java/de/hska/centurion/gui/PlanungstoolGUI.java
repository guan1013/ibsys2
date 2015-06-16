package de.hska.centurion.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import de.hska.centurion.App;
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
import de.hska.centurion.domain.output.Input;
import de.hska.centurion.domain.output.Item;
import de.hska.centurion.domain.output.Production;
import de.hska.centurion.domain.output.QualityControl;
import de.hska.centurion.domain.output.WorkingTime;
import de.hska.centurion.gui.actionlistener.StepButtonsActionDialog;
import de.hska.centurion.gui.dialogs.SplittingDialog;
import de.hska.centurion.gui.dialogs.SplittingDialog.DialogResult;
import de.hska.centurion.gui.util.OrderEntity;
import de.hska.centurion.gui.util.OrderType;
import de.hska.centurion.gui.util.SafetyStockEntity;
import de.hska.centurion.gui.util.WorkplaceEntity;
import de.hska.centurion.io.XmlParser;
import de.hska.centurion.services.production.ProductionService;
import de.hska.centurion.services.purchase.OrderService;

/**
 * This is the main graphical user interface of the planning tool. It contains
 * the window which contains the result and the planning frame. The main screen
 * is devided in two parts: Results and planning. The result screen displays the
 * data from the last period. Source of this data is a XML-File which is parsed
 * into an object of {@link Class} {@link Results}. The planning screen contains
 * formulas for the user to enter data (it is stored as {@link UserInput}. Also
 * the result of the calculations is displayed in this area to help the user
 * make decisions.
 * 
 * @see Results
 * @see UserInput
 * 
 * @author Andreas Guentzel
 *
 */
public class PlanungstoolGUI {
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("de.hska.centurion.domain.gui.messages"); //$NON-NLS-1$

	// //////////////////////////////////////////////////////////////////////////////////////////////////////
	// ATTRIBUTES

	// STATIC ATTRIBUTES
	private static final int DEFAULT_SAFETY_STOCK = 100;

	private static final String[] DELIVERY_MODES = new String[] { "?", "special order", "cheap vendor", "JIT", "fast",
			"normal" };

	public static void showErrorDialog(String message, Exception exception, Component parent) {

		String completeMessage = message;

		if (exception.getMessage() != null) {
			completeMessage += "\n"
					+ exception.getMessage().substring(0, Math.min(exception.getMessage().length(), 100));
		}

		JOptionPane.showMessageDialog(parent, completeMessage, "Fehler", JOptionPane.ERROR_MESSAGE);

	}

	// UI COMPONENTS - MAIN SCREEN
	private JFrame frameMain;

	// UI COMPONENTS - PLANNING FRAME
	private JTabbedPane tabbedPanePlanning;
	// UI COMPONENTS - PLANNING STEP 1
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
	// UI COMPONENTS - PLANNING STEP 2
	private JSpinner spinnerStep2P1Sales;
	private JSpinner spinnerStep2P1DirectSales;
	private JSpinner spinnerStep2P2Sales;

	private JSpinner spinnerStep2P3Sales;
	private JSpinner spinnerStep2P2DirectSales;

	private JSpinner spinnerStep2P3DirectSales;
	// TABLES
	private JTable tableWarehouse;
	private JTable tableInwardStockMovement;
	private JTable tableFutureInwardStockMovement;

	private JTable tableIdleTimeCosts;
	private JTable tableWaitingListWorkstations;

	// OTHER FIELDS

	private JTable table;

	/**
	 * A map of all planning steps. The panel which holds the ui components for
	 * a specific step can be accessed by its index (key=1 is the panel of Step
	 * 1).
	 */
	private HashMap<Integer, JPanel> stepsMap;

	/**
	 * A map of all ui components of the planning step 3. All items w
	 */
	private HashMap<String, SafetyStockEntity> safetyStockFormular;

	private HashMap<Integer, WorkplaceEntity> workplaceFormular;

	private List<OrderEntity> orderFormular;

	/**
	 * Currently displayed and used result object (parsed from xml file).
	 */
	private Results results = null;

	/**
	 * Object which stores all data which was entered by the user.
	 */
	private UserInput userInput;

	private Input output;

	private ProductionService productionService;

	// //////////////////////////////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTOR

	private JList<String> listStep4ProductionOrder;

	// //////////////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS

	/**
	 * Create the application.
	 */
	public PlanungstoolGUI(String[] args) {

		String locale = "";
		if (args != null && args.length > 0) {
			locale = args[0];
		}

		BUNDLE = ResourceBundle.getBundle("de.hska.centurion.domain.gui.messages", new Locale(locale)); //$NON-NLS-1$

		// Initialize attributes
		stepsMap = new HashMap<>();
		userInput = new UserInput();
		safetyStockFormular = new HashMap<String, SafetyStockEntity>();
		workplaceFormular = new HashMap<Integer, WorkplaceEntity>();
		orderFormular = new ArrayList<>();
		userInput.setSafetyStock(new SafetyStock(DEFAULT_SAFETY_STOCK));
		output = new Input();

		productionService = null;

		// Initialize UI Components
		initialize();

		try {
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			frameMain.setLocation(new Point(x, y));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculateCapacity() {
		displayCapacity(productionService.calculateCapacity());
	}

	protected void calculateOrders() {

		OrderService orderService = new OrderService(userInput, results, output);

		displayOrders(orderService.calculatePurchaseOrders());

	}

	private void calculateProductionOrder() {

		readInForecasts();
		readInSales();
		readInSafetyStock();
		calculateSafetyStock();

		output.setProductionList(productionService.getProductionOrder());

		displayProductionOrder();
	}

	/**
	 * This method calls the production service to calculate the count of items
	 * which has to be produced to have the entered safety stock at the end of
	 * the planned period.
	 */
	private void calculateSafetyStock() {

		// Permit the user input to the production service.
		productionService.setForecast(userInput.getForecast());
		productionService.setSales(userInput.getSales(), userInput.getDirectSales());

		// Calculate safety stock
		Map<String, Integer> safetyStockGui = productionService.calculateProduction(userInput.getSafetyStock());

		// Display calculation result on gui
		displaySafetyStock(safetyStockGui);

	}

	private void displayCapacity(List<WorkingTime> calculateCapacity) {

		for (WorkingTime workingTime : calculateCapacity) {
			WorkplaceEntity workplace = workplaceFormular.get(workingTime.getStation());
			workplace.getOvertime().setValue(workingTime.getOvertime());
			workplace.getShift().setValue(workingTime.getShift());
		}
	}

	/**
	 * Displays the default value for safety stock in the number spinner (seems
	 * like user entered it).
	 */
	private void displayDefaultSafetyStock() {

		for (String id : userInput.getSafetyStock().getSafetyStocks().keySet()) {

			safetyStockFormular.get(id).getWish().setValue(DEFAULT_SAFETY_STOCK);

			if (id.equalsIgnoreCase("e16") || id.equalsIgnoreCase("e17") || id.equalsIgnoreCase("e26")) {
				safetyStockFormular.get(id).getWish().setValue(DEFAULT_SAFETY_STOCK * 3);
			}
		}
	}

	private void displayOrders(List<de.hska.centurion.domain.output.Order> calculatePurchaseOrders) {

		for (OrderEntity orderEntity : orderFormular) {
			orderEntity.getItemIndex().setText("");
			orderEntity.getQuantity().setText("");
			orderEntity.getOrderType().setSelectedIndex(0);
		}

		for (int i = 0; i < calculatePurchaseOrders.size(); i++) {

			// System.out.println(calculatePurchaseOrders.get(i));

			if (i >= orderFormular.size()) {
				System.out.println("Only 28 Orders can be displayed on GUI (Found: " + calculatePurchaseOrders.size()
						+ ")");
				break;
			}

			orderFormular.get(i).getItemIndex().setText("" + calculatePurchaseOrders.get(i).getArticle());
			orderFormular.get(i).getQuantity().setText("" + calculatePurchaseOrders.get(i).getQuantity());
			orderFormular.get(i).getOrderType().setSelectedIndex(calculatePurchaseOrders.get(i).getModus());

		}

	}

	/**
	 * Change gui labels where current period is used.
	 * 
	 * @param period
	 *            period of the loaded result file
	 */
	private void displayPeriod(int period) {

		lblStep1Periode1Title.setText(BUNDLE.getString("PlanungstoolGUI.common.period") + " " + (period + 1));
		lblStep1Periode2Title.setText(BUNDLE.getString("PlanungstoolGUI.common.period") + " " + (period + 2));
		lblStep1Periode3Title.setText(BUNDLE.getString("PlanungstoolGUI.common.period") + " " + (period + 3));
		lblStep1Periode4Title.setText(BUNDLE.getString("PlanungstoolGUI.common.period") + " " + (period + 4));
	}

	private void displayProductionOrder() {

		// Clean UI component
		listStep4ProductionOrder.removeAll();

		DefaultListModel<String> model = new DefaultListModel<>();

		for (Production production : output.getProductionList()) {
			model.addElement(production.getArticle() + " --- " + production.getQuantity());
		}

		listStep4ProductionOrder.setModel(model);

	}

	/**
	 * Displays the result of the safety calculation as info on the gui. (Not in
	 * the number spinners, in the labels behind the number spinners).
	 * 
	 * @param countOfPartsToProduce
	 *            calculated count of items to produce
	 */
	private void displaySafetyStock(Map<String, Integer> countOfPartsToProduce) {

		for (String id : countOfPartsToProduce.keySet()) {

			safetyStockFormular.get(id).getCalculated().setText(countOfPartsToProduce.get(id).toString());
		}
	}

	public JFrame getFrameMain() {
		return frameMain;
	}

	private String getModeAsString(int modeInt) {

		return DELIVERY_MODES[modeInt] + " (" + modeInt + ")";

	}

	private void initialize() {

		setFrameMain(new JFrame());
		getFrameMain().setIconImage(
				Toolkit.getDefaultToolkit().getImage(
						PlanungstoolGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		getFrameMain().setResizable(false);
		getFrameMain().setTitle(BUNDLE.getString("PlanungstoolGUI.frameMain.title")); //$NON-NLS-1$ //$NON-NLS-1$
		getFrameMain().setBounds(100, 100, 893, 738);
		getFrameMain().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		getFrameMain().setJMenuBar(menuBar);

		JMenu mnFile = new JMenu(BUNDLE.getString("PlanungstoolGUI.mnFile.text")); //$NON-NLS-1$
		menuBar.add(mnFile);

		final JInternalFrame internalFrameResults = new JInternalFrame(
				BUNDLE.getString("PlanungstoolGUI.internalFrameResults.title")); //$NON-NLS-1$
		internalFrameResults.setFrameIcon(new ImageIcon(PlanungstoolGUI.class
				.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif")));
		internalFrameResults.setBounds(10, 11, 867, 286);
		getFrameMain().getContentPane().add(internalFrameResults);
		internalFrameResults.getContentPane().setLayout(null);

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 831, 235);
		internalFrameResults.getContentPane().add(tabbedPane);

		final JPanel panelWarehouse = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.title"), null, panelWarehouse,
				null);
		panelWarehouse.setLayout(null);

		JScrollPane scrollPaneWarehouse = new JScrollPane();
		scrollPaneWarehouse.setBounds(0, 25, 826, 182);
		panelWarehouse.add(scrollPaneWarehouse);

		tableWarehouse = new JTable();
		tableWarehouse.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPaneWarehouse.setViewportView(tableWarehouse);
		tableWarehouse.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.article"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.quantity"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.initialquantity"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.quantitystartquantity"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.price"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.stockvalue") }));

		final JLabel labelTotalStockValue = new JLabel(BUNDLE.getString("PlanungstoolGUI.labelTotalStockValue.text")); //$NON-NLS-1$
		labelTotalStockValue.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalStockValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTotalStockValue.setBounds(20, 0, 806, 23);
		panelWarehouse.add(labelTotalStockValue);

		final JPanel panelInwardStockMovement = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.title"), null,
				panelInwardStockMovement, null);
		panelInwardStockMovement.setLayout(null);

		JScrollPane scrollPaneInwardStockMovement = new JScrollPane();
		scrollPaneInwardStockMovement.setBounds(0, 0, 826, 207);
		panelInwardStockMovement.add(scrollPaneInwardStockMovement);

		tableInwardStockMovement = new JTable();
		scrollPaneInwardStockMovement.setViewportView(tableInwardStockMovement);
		tableInwardStockMovement.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.orderid"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.ordermode"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.article"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.quantity"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.materialcosts"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.ordercosts"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.entirecosts"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.piececosts") }));
		tableInwardStockMovement.getColumnModel().getColumn(0).setResizable(false);
		tableInwardStockMovement.getColumnModel().getColumn(0).setPreferredWidth(103);
		tableInwardStockMovement.getColumnModel().getColumn(1).setResizable(false);
		tableInwardStockMovement.getColumnModel().getColumn(4).setPreferredWidth(106);
		tableInwardStockMovement.getColumnModel().getColumn(5).setPreferredWidth(91);

		final JPanel panelFutureInwardStockMovement = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.futereinwardstockmovement.title"), null,
				panelFutureInwardStockMovement, null);
		panelFutureInwardStockMovement.setLayout(null);

		JScrollPane scrollPaneFutureInwardStockMovement = new JScrollPane();
		scrollPaneFutureInwardStockMovement.setBounds(0, 0, 826, 207);
		panelFutureInwardStockMovement.add(scrollPaneFutureInwardStockMovement);

		tableFutureInwardStockMovement = new JTable();
		scrollPaneFutureInwardStockMovement.setViewportView(tableFutureInwardStockMovement);
		tableFutureInwardStockMovement.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.orderid"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.ordermode"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.article"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.quantity"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.futureinwardstockmovement.inward"),
				BUNDLE.getString("PlanungstoolGUI.results.tab.futureinwardstockmovement.deviation") }) {
			/**
			 * PlanungstoolGUI.results.tab.inwardstockmovement.title
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { true, true, true, true, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		final JPanel panelIdleTimeCosts = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.title"), null,
				panelIdleTimeCosts, null);
		panelIdleTimeCosts.setLayout(null);

		JScrollPane scrollPaneIdleTimeCosts = new JScrollPane();
		scrollPaneIdleTimeCosts.setBounds(0, 0, 826, 207);
		panelIdleTimeCosts.add(scrollPaneIdleTimeCosts);

		tableIdleTimeCosts = new JTable();
		scrollPaneIdleTimeCosts.setViewportView(tableIdleTimeCosts);
		tableIdleTimeCosts.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				BUNDLE.getString("PlanungstoolGUI.lblStep5TitleWorkplace.text"), // ARBEITSPLATZ
				BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.setupevents"), // RÜSTVORGÄNGE
				BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.idletime"), // Leerzeiten
				BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.wageidletimecosts"), // Lohnleerkosten
				BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.wagecosts"), // Lohnkosten
				BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.machine") }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		final JPanel panelWaitingListWorkstations = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.waitinglist.workstations.title"), null,
				panelWaitingListWorkstations, null);
		panelWaitingListWorkstations.setLayout(null);

		JScrollPane scrollPaneWorklingListWorkstations = new JScrollPane();
		scrollPaneWorklingListWorkstations.setBounds(0, 0, 826, 207);
		panelWaitingListWorkstations.add(scrollPaneWorklingListWorkstations);

		tableWaitingListWorkstations = new JTable();
		scrollPaneWorklingListWorkstations.setRowHeaderView(tableWaitingListWorkstations);
		tableWaitingListWorkstations.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Arbeitsplatz",
				"Periode", "Fertigungsauftrag", "Los", "Teil", "Menge", "Zeitbedarf" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5567778224840912024L;
			boolean[] columnEditables = new boolean[] { false, true, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		final JPanel panelWaitingListStock = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.waitinglist.stock.title"), null,
				panelWaitingListStock, null);
		panelWaitingListStock.setLayout(null);

		table = new JTable();
		table.setBounds(0, 0, 826, 196);
		panelWaitingListStock.add(table);

		JPanel panelIntroduction = new JPanel();
		tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.introduction"), null, panelIntroduction, null);
		panelIntroduction.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/centurion.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(747, 0, 69, 207);
		panelIntroduction.add(lblNewLabel);

		JLabel lblIbsysiiplanungstool = new JLabel("IBSYS-II-Planungstool");
		lblIbsysiiplanungstool.setHorizontalAlignment(SwingConstants.CENTER);
		lblIbsysiiplanungstool.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIbsysiiplanungstool.setBounds(20, 44, 806, 32);
		panelIntroduction.add(lblIbsysiiplanungstool);

		JLabel label_7 = new JLabel("");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		img = new ImageIcon(this.getClass().getResource("/gbi.png")).getImage();
		label_7.setIcon(new ImageIcon(img));
		label_7.setBounds(10, 25, 282, 171);
		panelIntroduction.add(label_7);

		JLabel lblAsd = new JLabel(
				"<html><b>Gruppe Centurion</b><ul><li>Alexandra P</li><li>Albian Nitaj</li><li>Simon Findling</li><li>Matthias Schnell</li><li>Andreas Güntzel</li></ul></html>");
		lblAsd.setVerticalAlignment(SwingConstants.TOP);
		lblAsd.setBounds(580, 55, 157, 96);
		panelIntroduction.add(lblAsd);
		tableWaitingListWorkstations.getColumnModel().getColumn(0).setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(1).setPreferredWidth(51);
		tableWaitingListWorkstations.getColumnModel().getColumn(2).setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(2).setPreferredWidth(103);
		tableWaitingListWorkstations.getColumnModel().getColumn(3).setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(3).setPreferredWidth(50);
		tableWaitingListWorkstations.getColumnModel().getColumn(4).setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(4).setPreferredWidth(44);
		tableWaitingListWorkstations.getColumnModel().getColumn(5).setResizable(false);
		tableWaitingListWorkstations.getColumnModel().getColumn(5).setPreferredWidth(54);
		tableWaitingListWorkstations.getColumnModel().getColumn(6).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(0).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(1).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(2).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(3).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(4).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(5).setResizable(false);
		tableIdleTimeCosts.getColumnModel().getColumn(5).setPreferredWidth(128);
		tableFutureInwardStockMovement.getColumnModel().getColumn(0).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(1).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(2).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(3).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(4).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(5).setResizable(false);
		tableFutureInwardStockMovement.getColumnModel().getColumn(5).setMinWidth(26);
		internalFrameResults.setVisible(true);

		final PlanungstoolGUI gui = this;

		JMenuItem mntmLoadResultXml = new JMenuItem(BUNDLE.getString("PlanungstoolGUI.mntmLoadResultXml.text")); //$NON-NLS-1$

		mnFile.add(mntmLoadResultXml);

		JMenu mnSprache = new JMenu(BUNDLE.getString("PlanungstoolGUI.mnSprache.text")); //$NON-NLS-1$ //$NON-NLS-1$
		menuBar.add(mnSprache);

		JMenuItem mntmDeutsch = new JMenuItem(BUNDLE.getString("PlanungstoolGUI.mntmDeutsch.text")); //$NON-NLS-1$
		mntmDeutsch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchLanguage("");
			}
		});
		mnSprache.add(mntmDeutsch);

		JMenuItem mntmEnglisch = new JMenuItem(BUNDLE.getString("PlanungstoolGUI.mntmEnglisch.text")); //$NON-NLS-1$
		mntmEnglisch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchLanguage("en");
			}
		});
		mnSprache.add(mntmEnglisch);

		JMenuItem mntmRussisch = new JMenuItem(BUNDLE.getString("PlanungstoolGUI.mntmRussisch.text")); //$NON-NLS-1$
		mntmRussisch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchLanguage("ru");
			}
		});
		mnSprache.add(mntmRussisch);
		getFrameMain().getContentPane().setLayout(null);

		final JInternalFrame internalFramePlanning = new JInternalFrame(
				BUNDLE.getString("PlanungstoolGUI.internalFramePlanning.title"));
		internalFramePlanning.setBounds(10, 305, 867, 371);
		getFrameMain().getContentPane().add(internalFramePlanning);

		tabbedPanePlanning = new JTabbedPane(JTabbedPane.TOP);
		internalFramePlanning.getContentPane().add(tabbedPanePlanning, BorderLayout.CENTER);

		JPanel panelStep1 = new JPanel();
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 1", null, panelStep1, null);
		panelStep1.setLayout(null);

		JLabel lblStep1Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 1: " + BUNDLE.getString("PlanungstoolGUI.planning.step1")); //$NON-NLS-1$
		lblStep1Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Title.setBounds(10, 11, 826, 23);
		panelStep1.add(lblStep1Title);

		JLabel lblStep1P1Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P1Title.text")); //$NON-NLS-1$
		lblStep1P1Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P1Title.setBounds(129, 99, 141, 23);
		panelStep1.add(lblStep1P1Title);

		JLabel lblStep1P2Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P2Title.text")); //$NON-NLS-1$
		lblStep1P2Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P2Title.setBounds(129, 150, 141, 23);
		panelStep1.add(lblStep1P2Title);

		JLabel lblStep1P3Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P3Title.text")); //$NON-NLS-1$
		lblStep1P3Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep1P3Title.setBounds(129, 201, 141, 23);
		panelStep1.add(lblStep1P3Title);

		lblStep1Periode1Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.common.period") + " n+1");
		lblStep1Periode1Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode1Title.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep1Periode1Title.setBounds(261, 56, 76, 23);
		panelStep1.add(lblStep1Periode1Title);

		lblStep1Periode2Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.common.period") + " n+2");
		lblStep1Periode2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode2Title.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep1Periode2Title.setBounds(344, 56, 76, 23);
		panelStep1.add(lblStep1Periode2Title);

		lblStep1Periode3Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.common.period") + " n+3");
		lblStep1Periode3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode3Title.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep1Periode3Title.setBounds(427, 56, 76, 23);
		panelStep1.add(lblStep1Periode3Title);

		lblStep1Periode4Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.common.period") + " n+4");
		lblStep1Periode4Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep1Periode4Title.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep1Periode4Title.setBounds(510, 56, 76, 23);
		panelStep1.add(lblStep1Periode4Title);

		spinnerStep1P1Periode1 = new JSpinner();
		spinnerStep1P1Periode1.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P1Periode1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P1Periode1.setBounds(272, 93, 56, 35);
		panelStep1.add(spinnerStep1P1Periode1);

		spinnerStep1P2Periode1 = new JSpinner();
		spinnerStep1P2Periode1.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P2Periode1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P2Periode1.setBounds(272, 146, 56, 35);
		panelStep1.add(spinnerStep1P2Periode1);

		spinnerStep1P3Periode1 = new JSpinner();
		spinnerStep1P3Periode1.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P3Periode1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P3Periode1.setBounds(272, 199, 56, 35);
		panelStep1.add(spinnerStep1P3Periode1);

		spinnerStep1P1Periode2 = new JSpinner();
		spinnerStep1P1Periode2.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P1Periode2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P1Periode2.setBounds(354, 93, 56, 35);
		panelStep1.add(spinnerStep1P1Periode2);

		spinnerStep1P2Periode2 = new JSpinner();
		spinnerStep1P2Periode2.setModel(new SpinnerNumberModel(0, 0, 100005, 50));
		spinnerStep1P2Periode2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P2Periode2.setBounds(354, 146, 56, 35);
		panelStep1.add(spinnerStep1P2Periode2);

		spinnerStep1P3Periode2 = new JSpinner();
		spinnerStep1P3Periode2.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P3Periode2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P3Periode2.setBounds(354, 199, 56, 35);
		panelStep1.add(spinnerStep1P3Periode2);

		spinnerStep1P1Periode3 = new JSpinner();
		spinnerStep1P1Periode3.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P1Periode3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P1Periode3.setBounds(436, 93, 56, 35);
		panelStep1.add(spinnerStep1P1Periode3);

		spinnerStep1P2Periode3 = new JSpinner();
		spinnerStep1P2Periode3.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P2Periode3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P2Periode3.setBounds(436, 146, 56, 35);
		panelStep1.add(spinnerStep1P2Periode3);

		spinnerStep1P3Periode3 = new JSpinner();
		spinnerStep1P3Periode3.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P3Periode3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P3Periode3.setBounds(436, 199, 56, 35);
		panelStep1.add(spinnerStep1P3Periode3);

		spinnerStep1P1Periode4 = new JSpinner();
		spinnerStep1P1Periode4.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P1Periode4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P1Periode4.setBounds(518, 93, 56, 35);
		panelStep1.add(spinnerStep1P1Periode4);

		spinnerStep1P2Periode4 = new JSpinner();
		spinnerStep1P2Periode4.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P2Periode4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P2Periode4.setBounds(518, 146, 56, 35);
		panelStep1.add(spinnerStep1P2Periode4);

		spinnerStep1P3Periode4 = new JSpinner();
		spinnerStep1P3Periode4.setModel(new SpinnerNumberModel(0, 0, 10000, 50));
		spinnerStep1P3Periode4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep1P3Periode4.setBounds(518, 199, 56, 35);
		panelStep1.add(spinnerStep1P3Periode4);

		JButton btnStep1NextStep = new JButton(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 2: " + BUNDLE.getString("PlanungstoolGUI.planning.step2") + " >>"); //$NON-NLS-1$
		btnStep1NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep1NextStep.setBounds(616, 268, 220, 35);
		panelStep1.add(btnStep1NextStep);

		JPanel panelStep2 = new JPanel();
		panelStep2.setLayout(null);
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 2", null, panelStep2, null);

		JLabel lblStep2Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 2: " + BUNDLE.getString("PlanungstoolGUI.planning.step2")); //$NON-NLS-1$
		lblStep2Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep2Title.setBounds(10, 11, 826, 23);
		panelStep2.add(lblStep2Title);

		JLabel lblStep2P1Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P1Title.text")); //$NON-NLS-1$
		lblStep2P1Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P1Title.setBounds(129, 99, 141, 23);
		panelStep2.add(lblStep2P1Title);

		JLabel lblStep2P2Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P2Title.text")); //$NON-NLS-1$
		lblStep2P2Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P2Title.setBounds(129, 150, 141, 23);
		panelStep2.add(lblStep2P2Title);

		JLabel lblStep2P3Title = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep1P3Title.text")); //$NON-NLS-1$
		lblStep2P3Title.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep2P3Title.setBounds(129, 201, 141, 23);
		panelStep2.add(lblStep2P3Title);

		JLabel lblStep2SalesTitle = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep2SalesTitle.text")); //$NON-NLS-1$
		lblStep2SalesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2SalesTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep2SalesTitle.setBounds(261, 56, 120, 23);
		panelStep2.add(lblStep2SalesTitle);

		JLabel lblStep2DirectSalesTitle = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep2DirectSalesTitle.text")); //$NON-NLS-1$
		lblStep2DirectSalesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep2DirectSalesTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep2DirectSalesTitle.setBounds(394, 56, 120, 23);
		panelStep2.add(lblStep2DirectSalesTitle);

		spinnerStep2P1Sales = new JSpinner();
		spinnerStep2P1Sales.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P1Sales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P1Sales.setBounds(295, 93, 75, 35);
		panelStep2.add(spinnerStep2P1Sales);

		spinnerStep2P1DirectSales = new JSpinner();
		spinnerStep2P1DirectSales
				.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P1DirectSales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P1DirectSales.setBounds(426, 93, 75, 35);
		panelStep2.add(spinnerStep2P1DirectSales);

		spinnerStep2P2Sales = new JSpinner();
		spinnerStep2P2Sales.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P2Sales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P2Sales.setBounds(295, 146, 75, 35);
		panelStep2.add(spinnerStep2P2Sales);

		spinnerStep2P3Sales = new JSpinner();
		spinnerStep2P3Sales.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P3Sales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P3Sales.setBounds(295, 199, 75, 35);
		panelStep2.add(spinnerStep2P3Sales);

		spinnerStep2P2DirectSales = new JSpinner();
		spinnerStep2P2DirectSales
				.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P2DirectSales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P2DirectSales.setBounds(426, 146, 75, 35);
		panelStep2.add(spinnerStep2P2DirectSales);

		spinnerStep2P3DirectSales = new JSpinner();
		spinnerStep2P3DirectSales
				.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerStep2P3DirectSales.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStep2P3DirectSales.setBounds(426, 199, 75, 35);
		panelStep2.add(spinnerStep2P3DirectSales);

		JButton btnStep2NextStep = new JButton(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 3: " + BUNDLE.getString("PlanungstoolGUI.planning.step3") + " >>"); //$NON-NLS-1$
		btnStep2NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep2NextStep.setBounds(616, 266, 220, 35);
		panelStep2.add(btnStep2NextStep);

		JButton btnStep2PrevStep = new JButton(
				"<< "	+ BUNDLE.getString("PlanungstoolGUI.planning.step") + " 1: " + BUNDLE.getString("PlanungstoolGUI.planning.step1")); //$NON-NLS-1$
		btnStep2PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep2PrevStep.setBounds(10, 266, 220, 35);
		panelStep2.add(btnStep2PrevStep);

		JPanel panelStep3 = new JPanel();
		panelStep3.setLayout(null);
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 3", null, panelStep3, null);

		JLabel lblStep3Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 3: " + BUNDLE.getString("PlanungstoolGUI.planning.step3")); //$NON-NLS-1$
		lblStep3Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep3Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep3Title.setBounds(10, 11, 826, 23);
		panelStep3.add(lblStep3Title);

		JLabel lblStep3P1 = new JLabel("P1");
		lblStep3P1.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P1.setBounds(50, 45, 29, 23);
		panelStep3.add(lblStep3P1);

		JLabel lblStep3P2 = new JLabel("P2");
		lblStep3P2.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P2.setBounds(222, 45, 29, 23);
		panelStep3.add(lblStep3P2);

		JLabel lblStep3P3 = new JLabel("P3");
		lblStep3P3.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3P3.setBounds(398, 45, 29, 23);
		panelStep3.add(lblStep3P3);

		JLabel lblStep3E4 = new JLabel("E4");
		lblStep3E4.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E4.setBounds(50, 67, 29, 23);
		panelStep3.add(lblStep3E4);

		JLabel lblStep3E5 = new JLabel("E5");
		lblStep3E5.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E5.setBounds(222, 67, 29, 23);
		panelStep3.add(lblStep3E5);

		JLabel lblStep3E6 = new JLabel("E6");
		lblStep3E6.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E6.setBounds(398, 67, 29, 23);
		panelStep3.add(lblStep3E6);

		JLabel lblStep3E7 = new JLabel("E7");
		lblStep3E7.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E7.setBounds(50, 90, 29, 23);
		panelStep3.add(lblStep3E7);

		JLabel lblStep3E8 = new JLabel("E8");
		lblStep3E8.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E8.setBounds(222, 90, 29, 23);
		panelStep3.add(lblStep3E8);

		JLabel lblStep3E9 = new JLabel("E9");
		lblStep3E9.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E9.setBounds(398, 90, 29, 23);
		panelStep3.add(lblStep3E9);

		JLabel lblStep3E10 = new JLabel("E10");
		lblStep3E10.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E10.setBounds(50, 112, 29, 23);
		panelStep3.add(lblStep3E10);

		JLabel lblStep3E11 = new JLabel("E11");
		lblStep3E11.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E11.setBounds(222, 112, 29, 23);
		panelStep3.add(lblStep3E11);

		JLabel lblStep3E12 = new JLabel("E12");
		lblStep3E12.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E12.setBounds(398, 112, 29, 23);
		panelStep3.add(lblStep3E12);

		JLabel lblStep3E13 = new JLabel("E13");
		lblStep3E13.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E13.setBounds(50, 135, 29, 23);
		panelStep3.add(lblStep3E13);

		JLabel lblStep3E14 = new JLabel("E14");
		lblStep3E14.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E14.setBounds(222, 135, 29, 23);
		panelStep3.add(lblStep3E14);

		JLabel lblStep3E15 = new JLabel("E15");
		lblStep3E15.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E15.setBounds(398, 135, 29, 23);
		panelStep3.add(lblStep3E15);

		JLabel lblStep3E16 = new JLabel("E16");
		lblStep3E16.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E16.setBounds(569, 45, 29, 23);
		panelStep3.add(lblStep3E16);

		JLabel lblStep3E17 = new JLabel("E17");
		lblStep3E17.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E17.setBounds(569, 67, 29, 23);
		panelStep3.add(lblStep3E17);

		JLabel lblStep3E18 = new JLabel("E18");
		lblStep3E18.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E18.setBounds(50, 163, 29, 23);
		panelStep3.add(lblStep3E18);

		JLabel lblStep3E19 = new JLabel("E19");
		lblStep3E19.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E19.setBounds(222, 164, 29, 23);
		panelStep3.add(lblStep3E19);

		JLabel lblStep3E20 = new JLabel("E20");
		lblStep3E20.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E20.setBounds(398, 162, 29, 23);
		panelStep3.add(lblStep3E20);

		JLabel lblStep3E26 = new JLabel("E26");
		lblStep3E26.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E26.setBounds(569, 90, 29, 23);
		panelStep3.add(lblStep3E26);

		JLabel lblStep3E29 = new JLabel("E29");
		lblStep3E29.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E29.setBounds(398, 185, 29, 23);
		panelStep3.add(lblStep3E29);

		JLabel lblStep3E30 = new JLabel("E30");
		lblStep3E30.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E30.setBounds(398, 208, 29, 23);
		panelStep3.add(lblStep3E30);

		JLabel lblStep3E31 = new JLabel("E31");
		lblStep3E31.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E31.setBounds(398, 233, 29, 23);
		panelStep3.add(lblStep3E31);

		JLabel lblStep3E49 = new JLabel("E49");
		lblStep3E49.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E49.setBounds(50, 185, 29, 23);
		panelStep3.add(lblStep3E49);

		JLabel lblStep3E50 = new JLabel("E50");
		lblStep3E50.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E50.setBounds(50, 208, 29, 23);
		panelStep3.add(lblStep3E50);

		JLabel lblStep3E51 = new JLabel("E51");
		lblStep3E51.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E51.setBounds(50, 233, 29, 23);
		panelStep3.add(lblStep3E51);

		JLabel lblStep3E54 = new JLabel("E54");
		lblStep3E54.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E54.setBounds(222, 187, 29, 23);
		panelStep3.add(lblStep3E54);

		JLabel lblStep3E55 = new JLabel("E55");
		lblStep3E55.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E55.setBounds(222, 210, 29, 23);
		panelStep3.add(lblStep3E55);

		JLabel lblStep3E56 = new JLabel("E56");
		lblStep3E56.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep3E56.setBounds(222, 235, 29, 23);
		panelStep3.add(lblStep3E56);

		JLabel lblStep3P1PartsToBeProduced = new JLabel("250");
		lblStep3P1PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P1PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P1PartsToBeProduced.setBounds(145, 45, 60, 23);
		panelStep3.add(lblStep3P1PartsToBeProduced);

		JLabel lblStep3P2PartsToBeProduced = new JLabel("250");
		lblStep3P2PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P2PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P2PartsToBeProduced.setBounds(317, 45, 60, 23);
		panelStep3.add(lblStep3P2PartsToBeProduced);

		JLabel lblStep3P3PartsToBeProduced = new JLabel("250");
		lblStep3P3PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3P3PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3P3PartsToBeProduced.setBounds(493, 45, 52, 23);
		panelStep3.add(lblStep3P3PartsToBeProduced);

		JLabel lblStep3E4PartsToBeProduced = new JLabel("250");
		lblStep3E4PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E4PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E4PartsToBeProduced.setBounds(145, 67, 60, 23);
		panelStep3.add(lblStep3E4PartsToBeProduced);

		JLabel lblStep3E5PartsToBeProduced = new JLabel("250");
		lblStep3E5PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E5PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E5PartsToBeProduced.setBounds(317, 67, 60, 23);
		panelStep3.add(lblStep3E5PartsToBeProduced);

		JLabel lblStep3E6PartsToBeProduced = new JLabel("250");
		lblStep3E6PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E6PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E6PartsToBeProduced.setBounds(493, 67, 52, 23);
		panelStep3.add(lblStep3E6PartsToBeProduced);

		JLabel lblStep3E7PartsToBeProduced = new JLabel("250");
		lblStep3E7PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E7PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E7PartsToBeProduced.setBounds(145, 90, 60, 23);
		panelStep3.add(lblStep3E7PartsToBeProduced);

		JLabel lblStep3E8PartsToBeProduced = new JLabel("250");
		lblStep3E8PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E8PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E8PartsToBeProduced.setBounds(317, 90, 60, 23);
		panelStep3.add(lblStep3E8PartsToBeProduced);

		JLabel lblStep3E9PartsToBeProduced = new JLabel("250");
		lblStep3E9PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E9PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E9PartsToBeProduced.setBounds(493, 90, 52, 23);
		panelStep3.add(lblStep3E9PartsToBeProduced);

		JLabel lblStep3E10PartsToBeProduced = new JLabel("250");
		lblStep3E10PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E10PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E10PartsToBeProduced.setBounds(145, 112, 60, 23);
		panelStep3.add(lblStep3E10PartsToBeProduced);

		JLabel lblStep3E11PartsToBeProduced = new JLabel("250");
		lblStep3E11PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E11PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E11PartsToBeProduced.setBounds(317, 112, 60, 23);
		panelStep3.add(lblStep3E11PartsToBeProduced);

		JLabel lblStep3E12PartsToBeProduced = new JLabel("250");
		lblStep3E12PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E12PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E12PartsToBeProduced.setBounds(493, 112, 52, 23);
		panelStep3.add(lblStep3E12PartsToBeProduced);

		JLabel lblStep3E13PartsToBeProduced = new JLabel("250");
		lblStep3E13PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E13PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E13PartsToBeProduced.setBounds(145, 135, 60, 23);
		panelStep3.add(lblStep3E13PartsToBeProduced);

		JLabel lblStep3E14PartsToBeProduced = new JLabel("250");
		lblStep3E14PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E14PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E14PartsToBeProduced.setBounds(317, 135, 60, 23);
		panelStep3.add(lblStep3E14PartsToBeProduced);

		JLabel lblStep3E15PartsToBeProduced = new JLabel("250");
		lblStep3E15PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E15PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E15PartsToBeProduced.setBounds(493, 135, 52, 23);
		panelStep3.add(lblStep3E15PartsToBeProduced);

		JLabel lblStep3E16PartsToBeProduced = new JLabel("250");
		lblStep3E16PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E16PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E16PartsToBeProduced.setBounds(668, 45, 52, 23);
		panelStep3.add(lblStep3E16PartsToBeProduced);

		JLabel lblStep3E17PartsToBeProduced = new JLabel("250");
		lblStep3E17PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E17PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E17PartsToBeProduced.setBounds(667, 67, 53, 23);
		panelStep3.add(lblStep3E17PartsToBeProduced);

		JLabel lblStep3E18PartsToBeProduced = new JLabel("250");
		lblStep3E18PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E18PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E18PartsToBeProduced.setBounds(145, 163, 60, 23);
		panelStep3.add(lblStep3E18PartsToBeProduced);

		JLabel lblStep3E19PartsToBeProduced = new JLabel("250");
		lblStep3E19PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E19PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E19PartsToBeProduced.setBounds(317, 164, 60, 23);
		panelStep3.add(lblStep3E19PartsToBeProduced);

		JLabel lblStep3E20PartsToBeProduced = new JLabel("250");
		lblStep3E20PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E20PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E20PartsToBeProduced.setBounds(493, 162, 52, 23);
		panelStep3.add(lblStep3E20PartsToBeProduced);

		JLabel lblStep3E26PartsToBeProduced = new JLabel("250");
		lblStep3E26PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E26PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E26PartsToBeProduced.setBounds(668, 90, 52, 23);
		panelStep3.add(lblStep3E26PartsToBeProduced);

		JLabel lblStep3E29PartsToBeProduced = new JLabel("250");
		lblStep3E29PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E29PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E29PartsToBeProduced.setBounds(493, 185, 52, 23);
		panelStep3.add(lblStep3E29PartsToBeProduced);

		JLabel lblStep3E30PartsToBeProduced = new JLabel("250");
		lblStep3E30PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E30PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E30PartsToBeProduced.setBounds(493, 208, 52, 23);
		panelStep3.add(lblStep3E30PartsToBeProduced);

		JLabel lblStep3E31PartsToBeProduced = new JLabel("250");
		lblStep3E31PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E31PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E31PartsToBeProduced.setBounds(493, 231, 52, 23);
		panelStep3.add(lblStep3E31PartsToBeProduced);

		JLabel lblStep3E49PartsToBeProduced = new JLabel("250");
		lblStep3E49PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E49PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E49PartsToBeProduced.setBounds(145, 185, 60, 23);
		panelStep3.add(lblStep3E49PartsToBeProduced);

		JLabel lblStep3E50PartsToBeProduced = new JLabel("250");
		lblStep3E50PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E50PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E50PartsToBeProduced.setBounds(145, 208, 60, 23);
		panelStep3.add(lblStep3E50PartsToBeProduced);

		JLabel lblStep3E51PartsToBeProduced = new JLabel("250");
		lblStep3E51PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E51PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E51PartsToBeProduced.setBounds(145, 231, 60, 23);
		panelStep3.add(lblStep3E51PartsToBeProduced);

		JLabel lblStep3E54PartsToBeProduced = new JLabel("250");
		lblStep3E54PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E54PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E54PartsToBeProduced.setBounds(317, 187, 60, 23);
		panelStep3.add(lblStep3E54PartsToBeProduced);

		JLabel lblStep3E55PartsToBeProduced = new JLabel("250");
		lblStep3E55PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E55PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E55PartsToBeProduced.setBounds(317, 210, 60, 23);
		panelStep3.add(lblStep3E55PartsToBeProduced);

		JLabel lblStep3E56PartsToBeProduced = new JLabel("250");
		lblStep3E56PartsToBeProduced.setHorizontalAlignment(SwingConstants.LEFT);
		lblStep3E56PartsToBeProduced.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStep3E56PartsToBeProduced.setBounds(317, 233, 60, 23);
		panelStep3.add(lblStep3E56PartsToBeProduced);

		JSpinner spinnerStep3P1 = new JSpinner();
		lblStep3P1.setLabelFor(spinnerStep3P1);
		spinnerStep3P1.setBounds(80, 48, 60, 20);
		panelStep3.add(spinnerStep3P1);

		JSpinner spinnerStep3P2 = new JSpinner();
		spinnerStep3P2.setBounds(252, 48, 60, 20);
		panelStep3.add(spinnerStep3P2);

		JSpinner spinnerStep3P3 = new JSpinner();
		spinnerStep3P3.setBounds(428, 48, 60, 20);
		panelStep3.add(spinnerStep3P3);

		JSpinner spinnerStep3E4 = new JSpinner();
		spinnerStep3E4.setBounds(80, 70, 60, 20);
		panelStep3.add(spinnerStep3E4);

		JSpinner spinnerStep3E5 = new JSpinner();
		spinnerStep3E5.setBounds(252, 70, 60, 20);
		panelStep3.add(spinnerStep3E5);

		JSpinner spinnerStep3E6 = new JSpinner();
		spinnerStep3E6.setBounds(428, 70, 60, 20);
		panelStep3.add(spinnerStep3E6);

		JSpinner spinnerStep3E7 = new JSpinner();
		spinnerStep3E7.setBounds(80, 93, 60, 20);
		panelStep3.add(spinnerStep3E7);

		JSpinner spinnerStep3E8 = new JSpinner();
		spinnerStep3E8.setBounds(252, 93, 60, 20);
		panelStep3.add(spinnerStep3E8);

		JSpinner spinnerStep3E9 = new JSpinner();
		spinnerStep3E9.setBounds(428, 93, 60, 20);
		panelStep3.add(spinnerStep3E9);

		JSpinner spinnerStep3E10 = new JSpinner();
		spinnerStep3E10.setBounds(80, 115, 60, 20);
		panelStep3.add(spinnerStep3E10);

		JSpinner spinnerStep3E11 = new JSpinner();
		spinnerStep3E11.setBounds(252, 115, 60, 20);
		panelStep3.add(spinnerStep3E11);

		JSpinner spinnerStep3E12 = new JSpinner();
		spinnerStep3E12.setBounds(428, 115, 60, 20);
		panelStep3.add(spinnerStep3E12);

		JSpinner spinnerStep3E13 = new JSpinner();
		spinnerStep3E13.setBounds(80, 138, 60, 20);
		panelStep3.add(spinnerStep3E13);

		JSpinner spinnerStep3E14 = new JSpinner();
		spinnerStep3E14.setBounds(252, 138, 60, 20);
		panelStep3.add(spinnerStep3E14);

		JSpinner spinnerStep3E15 = new JSpinner();
		spinnerStep3E15.setBounds(428, 138, 60, 20);
		panelStep3.add(spinnerStep3E15);

		JSpinner spinnerStep3E16 = new JSpinner();
		spinnerStep3E16.setBounds(598, 48, 60, 20);
		panelStep3.add(spinnerStep3E16);

		JSpinner spinnerStep3E17 = new JSpinner();
		spinnerStep3E17.setBounds(600, 70, 60, 20);
		panelStep3.add(spinnerStep3E17);

		JSpinner spinnerStep3E18 = new JSpinner();
		spinnerStep3E18.setBounds(80, 166, 60, 20);
		panelStep3.add(spinnerStep3E18);

		JSpinner spinnerStep3E19 = new JSpinner();
		spinnerStep3E19.setBounds(252, 167, 60, 20);
		panelStep3.add(spinnerStep3E19);

		JSpinner spinnerStep3E20 = new JSpinner();
		spinnerStep3E20.setBounds(428, 165, 60, 20);
		panelStep3.add(spinnerStep3E20);

		JSpinner spinnerStep3E26 = new JSpinner();
		spinnerStep3E26.setBounds(598, 93, 60, 20);
		panelStep3.add(spinnerStep3E26);

		JSpinner spinnerStep3E29 = new JSpinner();
		spinnerStep3E29.setBounds(428, 188, 60, 20);
		panelStep3.add(spinnerStep3E29);

		JSpinner spinnerStep3E30 = new JSpinner();
		spinnerStep3E30.setBounds(428, 211, 60, 20);
		panelStep3.add(spinnerStep3E30);

		JSpinner spinnerStep3E31 = new JSpinner();
		spinnerStep3E31.setBounds(428, 233, 60, 20);
		panelStep3.add(spinnerStep3E31);

		JSpinner spinnerStep3E49 = new JSpinner();
		spinnerStep3E49.setBounds(80, 188, 60, 20);
		panelStep3.add(spinnerStep3E49);

		JSpinner spinnerStep3E50 = new JSpinner();
		spinnerStep3E50.setBounds(80, 211, 60, 20);
		panelStep3.add(spinnerStep3E50);

		JSpinner spinnerStep3E51 = new JSpinner();
		spinnerStep3E51.setBounds(80, 233, 60, 20);
		panelStep3.add(spinnerStep3E51);

		JSpinner spinnerStep3E54 = new JSpinner();
		spinnerStep3E54.setBounds(252, 190, 60, 20);
		panelStep3.add(spinnerStep3E54);

		JSpinner spinnerStep3E55 = new JSpinner();
		spinnerStep3E55.setBounds(252, 213, 60, 20);
		panelStep3.add(spinnerStep3E55);

		JSpinner spinnerStep3E56 = new JSpinner();
		spinnerStep3E56.setBounds(252, 235, 60, 20);
		panelStep3.add(spinnerStep3E56);

		JButton btnStep3NextStep = new JButton(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 4: " + BUNDLE.getString("PlanungstoolGUI.planning.step4") + " >>"); //$NON-NLS-1$
		btnStep3NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep3NextStep.setBounds(616, 266, 220, 35);
		panelStep3.add(btnStep3NextStep);

		JButton btnStep3PrevStep = new JButton(
				"<< "	+ BUNDLE.getString("PlanungstoolGUI.planning.step") + " 2: " + BUNDLE.getString("PlanungstoolGUI.planning.step2")); //$NON-NLS-1$
		btnStep3PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep3PrevStep.setBounds(10, 266, 220, 35);
		panelStep3.add(btnStep3PrevStep);

		JButton btnStep3Recalculate = new JButton(BUNDLE.getString("PlanungstoolGUI.btnStep3Recalculate.text")); //$NON-NLS-1$
		btnStep3Recalculate.setBounds(328, 266, 182, 35);
		panelStep3.add(btnStep3Recalculate);

		JPanel panelStep4 = new JPanel();
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 4", null, panelStep4, null);
		panelStep4.setLayout(null);

		JLabel lblStep4Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 4: " + BUNDLE.getString("PlanungstoolGUI.planning.step4")); //$NON-NLS-1$
		lblStep4Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep4Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep4Title.setBounds(10, 11, 826, 23);
		panelStep4.add(lblStep4Title);

		JButton btnStep4PrevStep = new JButton(
				"<< "	+ BUNDLE.getString("PlanungstoolGUI.planning.step") + " 3: " + BUNDLE.getString("PlanungstoolGUI.planning.step3")); //$NON-NLS-1$
		btnStep4PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep4PrevStep.setBounds(10, 268, 220, 35);
		panelStep4.add(btnStep4PrevStep);

		JButton btnStep4NextStep = new JButton(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 5: " + BUNDLE.getString("PlanungstoolGUI.planning.step5") + " >>"); //$NON-NLS-1$
		btnStep4NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep4NextStep.setBounds(616, 268, 220, 35);
		panelStep4.add(btnStep4NextStep);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(254, 45, 153, 210);
		panelStep4.add(scrollPane);

		listStep4ProductionOrder = new JList();
		scrollPane.setViewportView(listStep4ProductionOrder);
		listStep4ProductionOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStep4ProductionOrder.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveProductionUp();
			}

		});
		btnNewButton.setIcon(new ImageIcon(PlanungstoolGUI.class
				.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnNewButton.setBounds(417, 82, 124, 35);
		panelStep4.add(btnNewButton);

		JButton btnSplitten = new JButton(BUNDLE.getString("PlanungstoolGUI.btnSplitten.text")); //$NON-NLS-1$
		btnSplitten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSplittingDialog();
			}
		});
		btnSplitten.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSplitten.setBounds(417, 128, 124, 35);
		panelStep4.add(btnSplitten);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveProductionDown();
			}
		});
		button_2.setIcon(new ImageIcon(PlanungstoolGUI.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		button_2.setBounds(417, 174, 124, 35);
		panelStep4.add(button_2);

		JPanel panelStep5 = new JPanel();
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 5", null, panelStep5, null);
		panelStep5.setLayout(null);

		JLabel lblStep5Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 5: " + BUNDLE.getString("PlanungstoolGUI.planning.step5")); //$NON-NLS-1$
		lblStep5Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep5Title.setBounds(10, 11, 826, 23);
		panelStep5.add(lblStep5Title);

		JLabel lblStep5TitleWorkplace = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleWorkplace.text")); //$NON-NLS-1$
		lblStep5TitleWorkplace.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleWorkplace.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleWorkplace.setBounds(10, 45, 108, 23);
		panelStep5.add(lblStep5TitleWorkplace);

		JLabel lblStep5TitleShift = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleShift.text")); //$NON-NLS-1$
		lblStep5TitleShift.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleShift.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleShift.setBounds(128, 45, 89, 23);
		panelStep5.add(lblStep5TitleShift);

		JLabel lblStep5TitleOvertime = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleOvertime.text")); //$NON-NLS-1$
		lblStep5TitleOvertime.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleOvertime.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStep5TitleOvertime.setBounds(213, 45, 108, 30);
		panelStep5.add(lblStep5TitleOvertime);

		JLabel lblStep5Workplace1 = new JLabel("1");
		lblStep5Workplace1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5Workplace1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5Workplace1.setBounds(20, 76, 98, 23);
		panelStep5.add(lblStep5Workplace1);

		JSpinner spinnerStep5ShiftWorkplace1 = new JSpinner();
		spinnerStep5ShiftWorkplace1.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace2.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace3.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace4.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace6.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace7.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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
		spinnerStep5ShiftWorkplace8.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace8.setBounds(148, 217, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace8);

		JSpinner spinnerStep5OvertimeWorkplace8 = new JSpinner();
		spinnerStep5OvertimeWorkplace8.setBounds(213, 217, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace8);

		JButton btnStep5PrevStep = new JButton(
				"<< "	+ BUNDLE.getString("PlanungstoolGUI.planning.step") + " 4: " + BUNDLE.getString("PlanungstoolGUI.planning.step4")); //$NON-NLS-1$
		btnStep5PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep5PrevStep.setBounds(10, 268, 220, 35);
		panelStep5.add(btnStep5PrevStep);

		JButton btnStep5NextStep = new JButton(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 6: " + BUNDLE.getString("PlanungstoolGUI.planning.step6") + " >>"); //$NON-NLS-1$
		btnStep5NextStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep5NextStep.setBounds(616, 268, 220, 35);
		panelStep5.add(btnStep5NextStep);

		JSpinner spinnerStep5OvertimeWorkplace15 = new JSpinner();
		spinnerStep5OvertimeWorkplace15.setBounds(545, 217, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace15);

		JSpinner spinnerStep5ShiftWorkplace15 = new JSpinner();
		spinnerStep5ShiftWorkplace15.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace15.setBounds(480, 217, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace15);

		JSpinner spinnerStep5ShiftWorkplace14 = new JSpinner();
		spinnerStep5ShiftWorkplace14.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace14.setBounds(480, 194, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace14);

		JSpinner spinnerStep5ShiftWorkplace13 = new JSpinner();
		spinnerStep5ShiftWorkplace13.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace13.setBounds(480, 170, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace13);

		JSpinner spinnerStep5ShiftWorkplace12 = new JSpinner();
		spinnerStep5ShiftWorkplace12.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace12.setBounds(480, 147, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace12);

		JSpinner spinnerStep5ShiftWorkplace11 = new JSpinner();
		spinnerStep5ShiftWorkplace11.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace11.setBounds(480, 124, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace11);

		JSpinner spinnerStep5ShiftWorkplace10 = new JSpinner();
		spinnerStep5ShiftWorkplace10.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace10.setBounds(480, 101, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace10);

		JSpinner spinnerStep5ShiftWorkplace9 = new JSpinner();
		spinnerStep5ShiftWorkplace9.setModel(new SpinnerNumberModel(0, 0, 3, 1));
		spinnerStep5ShiftWorkplace9.setBounds(480, 79, 52, 20);
		panelStep5.add(spinnerStep5ShiftWorkplace9);

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

		JLabel lblStep5TitleWorkplace2 = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleWorkplace.text")); //$NON-NLS-1$
		lblStep5TitleWorkplace2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleWorkplace2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleWorkplace2.setBounds(342, 45, 108, 23);
		panelStep5.add(lblStep5TitleWorkplace2);

		JLabel lblStep5TitleShift2 = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleShift.text")); //$NON-NLS-1$
		lblStep5TitleShift2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep5TitleShift2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStep5TitleShift2.setBounds(460, 45, 89, 23);
		panelStep5.add(lblStep5TitleShift2);

		JLabel label_9 = new JLabel(BUNDLE.getString("PlanungstoolGUI.lblStep5TitleOvertime.text"));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_9.setBounds(545, 45, 108, 30);
		panelStep5.add(label_9);

		JSpinner spinnerStep5OvertimeWorkplace14 = new JSpinner();
		spinnerStep5OvertimeWorkplace14.setBounds(545, 194, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace14);

		JSpinner spinnerStep5OvertimeWorkplace13 = new JSpinner();
		spinnerStep5OvertimeWorkplace13.setBounds(545, 170, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace13);

		JSpinner spinnerStep5OvertimeWorkplace12 = new JSpinner();
		spinnerStep5OvertimeWorkplace12.setBounds(545, 147, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace12);

		JSpinner spinnerStep5OvertimeWorkplace11 = new JSpinner();
		spinnerStep5OvertimeWorkplace11.setBounds(545, 124, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace11);

		JSpinner spinnerStep5OvertimeWorkplace10 = new JSpinner();
		spinnerStep5OvertimeWorkplace10.setBounds(545, 101, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace10);

		JSpinner spinnerStep5OvertimeWorkplace9 = new JSpinner();
		spinnerStep5OvertimeWorkplace9.setBounds(545, 79, 108, 20);
		panelStep5.add(spinnerStep5OvertimeWorkplace9);

		JPanel panelStep6 = new JPanel();
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " 6", null, panelStep6, null);
		panelStep6.setLayout(null);

		JLabel lblStep6Title = new JLabel(
				BUNDLE.getString("PlanungstoolGUI.planning.step") + " 6: " + BUNDLE.getString("PlanungstoolGUI.planning.step6")); //$NON-NLS-1$
		lblStep6Title.setHorizontalAlignment(SwingConstants.CENTER);
		lblStep6Title.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStep6Title.setBounds(10, 11, 826, 23);
		panelStep6.add(lblStep6Title);

		JTextField textFieldStep6ItemIndex1 = new JTextField();
		textFieldStep6ItemIndex1.setBounds(10, 45, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex1);
		textFieldStep6ItemIndex1.setColumns(10);

		JTextField textFieldStep6Quantity1 = new JTextField();
		textFieldStep6Quantity1.setColumns(10);
		textFieldStep6Quantity1.setBounds(70, 45, 56, 20);
		panelStep6.add(textFieldStep6Quantity1);

		JComboBox comboBoxStep6OrderType1 = new JComboBox();
		comboBoxStep6OrderType1.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType1.setBounds(136, 45, 73, 20);
		panelStep6.add(comboBoxStep6OrderType1);

		JTextField textFieldStep6ItemIndex2 = new JTextField();
		textFieldStep6ItemIndex2.setColumns(10);
		textFieldStep6ItemIndex2.setBounds(10, 76, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex2);

		JTextField textFieldStep6Quantity2 = new JTextField();
		textFieldStep6Quantity2.setColumns(10);
		textFieldStep6Quantity2.setBounds(70, 76, 56, 20);
		panelStep6.add(textFieldStep6Quantity2);

		JComboBox comboBoxStep6OrderType2 = new JComboBox();
		comboBoxStep6OrderType2.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType2.setBounds(136, 76, 73, 20);
		panelStep6.add(comboBoxStep6OrderType2);

		JTextField textFieldStep6ItemIndex3 = new JTextField();
		textFieldStep6ItemIndex3.setColumns(10);
		textFieldStep6ItemIndex3.setBounds(10, 107, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex3);

		JTextField textFieldStep6Quantity3 = new JTextField();
		textFieldStep6Quantity3.setColumns(10);
		textFieldStep6Quantity3.setBounds(70, 107, 56, 20);
		panelStep6.add(textFieldStep6Quantity3);

		JComboBox comboBoxStep6OrderType3 = new JComboBox();
		comboBoxStep6OrderType3.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType3.setBounds(136, 107, 73, 20);
		panelStep6.add(comboBoxStep6OrderType3);

		JTextField textFieldStep6ItemIndex4 = new JTextField();
		textFieldStep6ItemIndex4.setColumns(10);
		textFieldStep6ItemIndex4.setBounds(10, 138, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex4);

		JTextField textFieldStep6Quantity4 = new JTextField();
		textFieldStep6Quantity4.setColumns(10);
		textFieldStep6Quantity4.setBounds(70, 138, 56, 20);
		panelStep6.add(textFieldStep6Quantity4);

		JComboBox comboBoxStep6OrderType4 = new JComboBox();
		comboBoxStep6OrderType4.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType4.setBounds(136, 138, 73, 20);
		panelStep6.add(comboBoxStep6OrderType4);

		JTextField textFieldStep6ItemIndex5 = new JTextField();
		textFieldStep6ItemIndex5.setColumns(10);
		textFieldStep6ItemIndex5.setBounds(10, 169, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex5);

		JTextField textFieldStep6Quantity5 = new JTextField();
		textFieldStep6Quantity5.setColumns(10);
		textFieldStep6Quantity5.setBounds(70, 169, 56, 20);
		panelStep6.add(textFieldStep6Quantity5);

		JComboBox comboBoxStep6OrderType5 = new JComboBox();
		comboBoxStep6OrderType5.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType5.setBounds(136, 169, 73, 20);
		panelStep6.add(comboBoxStep6OrderType5);

		JTextField textFieldStep6ItemIndex6 = new JTextField();
		textFieldStep6ItemIndex6.setColumns(10);
		textFieldStep6ItemIndex6.setBounds(10, 200, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex6);

		JTextField textFieldStep6Quantity6 = new JTextField();
		textFieldStep6Quantity6.setColumns(10);
		textFieldStep6Quantity6.setBounds(70, 200, 56, 20);
		panelStep6.add(textFieldStep6Quantity6);

		JComboBox comboBoxStep6OrderType6 = new JComboBox();
		comboBoxStep6OrderType6.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType6.setBounds(136, 200, 73, 20);
		panelStep6.add(comboBoxStep6OrderType6);

		JTextField textFieldStep6ItemIndex7 = new JTextField();
		textFieldStep6ItemIndex7.setColumns(10);
		textFieldStep6ItemIndex7.setBounds(10, 231, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex7);

		JTextField textFieldStep6Quantity7 = new JTextField();
		textFieldStep6Quantity7.setColumns(10);
		textFieldStep6Quantity7.setBounds(70, 231, 56, 20);
		panelStep6.add(textFieldStep6Quantity7);

		JComboBox comboBoxStep6OrderType7 = new JComboBox();
		comboBoxStep6OrderType7.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType7.setBounds(136, 231, 73, 20);
		panelStep6.add(comboBoxStep6OrderType7);

		JTextField textFieldStep6ItemIndex14 = new JTextField();
		textFieldStep6ItemIndex14.setColumns(10);
		textFieldStep6ItemIndex14.setBounds(219, 231, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex14);

		JTextField textFieldStep6ItemIndex13 = new JTextField();
		textFieldStep6ItemIndex13.setColumns(10);
		textFieldStep6ItemIndex13.setBounds(219, 200, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex13);

		JTextField textFieldStep6ItemIndex12 = new JTextField();
		textFieldStep6ItemIndex12.setColumns(10);
		textFieldStep6ItemIndex12.setBounds(219, 169, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex12);

		JTextField textFieldStep6ItemIndex11 = new JTextField();
		textFieldStep6ItemIndex11.setColumns(10);
		textFieldStep6ItemIndex11.setBounds(219, 138, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex11);

		JTextField textFieldStep6ItemIndex10 = new JTextField();
		textFieldStep6ItemIndex10.setColumns(10);
		textFieldStep6ItemIndex10.setBounds(219, 107, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex10);

		JTextField textFieldStep6ItemIndex9 = new JTextField();
		textFieldStep6ItemIndex9.setColumns(10);
		textFieldStep6ItemIndex9.setBounds(219, 76, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex9);

		JTextField textFieldStep6ItemIndex8 = new JTextField();
		textFieldStep6ItemIndex8.setColumns(10);
		textFieldStep6ItemIndex8.setBounds(219, 45, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex8);

		JTextField textFieldStep6Quantity8 = new JTextField();
		textFieldStep6Quantity8.setColumns(10);
		textFieldStep6Quantity8.setBounds(279, 45, 56, 20);
		panelStep6.add(textFieldStep6Quantity8);

		JTextField textFieldStep6Quantity9 = new JTextField();
		textFieldStep6Quantity9.setColumns(10);
		textFieldStep6Quantity9.setBounds(279, 76, 56, 20);
		panelStep6.add(textFieldStep6Quantity9);

		JTextField textFieldStep6Quantity10 = new JTextField();
		textFieldStep6Quantity10.setColumns(10);
		textFieldStep6Quantity10.setBounds(279, 107, 56, 20);
		panelStep6.add(textFieldStep6Quantity10);

		JTextField textFieldStep6Quantity11 = new JTextField();
		textFieldStep6Quantity11.setColumns(10);
		textFieldStep6Quantity11.setBounds(279, 138, 56, 20);
		panelStep6.add(textFieldStep6Quantity11);

		JTextField textFieldStep6Quantity12 = new JTextField();
		textFieldStep6Quantity12.setColumns(10);
		textFieldStep6Quantity12.setBounds(279, 169, 56, 20);
		panelStep6.add(textFieldStep6Quantity12);

		JTextField textFieldStep6Quantity13 = new JTextField();
		textFieldStep6Quantity13.setColumns(10);
		textFieldStep6Quantity13.setBounds(279, 200, 56, 20);
		panelStep6.add(textFieldStep6Quantity13);

		JTextField textFieldStep6Quantity14 = new JTextField();
		textFieldStep6Quantity14.setColumns(10);
		textFieldStep6Quantity14.setBounds(279, 231, 56, 20);
		panelStep6.add(textFieldStep6Quantity14);

		JComboBox comboBoxStep6OrderType14 = new JComboBox();
		comboBoxStep6OrderType14.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType14.setBounds(345, 231, 73, 20);
		panelStep6.add(comboBoxStep6OrderType14);

		JComboBox comboBoxStep6OrderType13 = new JComboBox();
		comboBoxStep6OrderType13.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType13.setBounds(345, 200, 73, 20);
		panelStep6.add(comboBoxStep6OrderType13);

		JComboBox comboBoxStep6OrderType12 = new JComboBox();
		comboBoxStep6OrderType12.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType12.setBounds(345, 169, 73, 20);
		panelStep6.add(comboBoxStep6OrderType12);

		JComboBox comboBoxStep6OrderType11 = new JComboBox();
		comboBoxStep6OrderType11.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType11.setBounds(345, 138, 73, 20);
		panelStep6.add(comboBoxStep6OrderType11);

		JComboBox comboBoxStep6OrderType10 = new JComboBox();
		comboBoxStep6OrderType10.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType10.setBounds(345, 107, 73, 20);
		panelStep6.add(comboBoxStep6OrderType10);

		JComboBox comboBoxStep6OrderType9 = new JComboBox();
		comboBoxStep6OrderType9.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType9.setBounds(345, 76, 73, 20);
		panelStep6.add(comboBoxStep6OrderType9);

		JComboBox comboBoxStep6OrderType8 = new JComboBox();
		comboBoxStep6OrderType8.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType8.setBounds(345, 45, 73, 20);
		panelStep6.add(comboBoxStep6OrderType8);

		JTextField textFieldStep6ItemIndex21 = new JTextField();
		textFieldStep6ItemIndex21.setColumns(10);
		textFieldStep6ItemIndex21.setBounds(434, 231, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex21);

		JTextField textFieldStep6ItemIndex20 = new JTextField();
		textFieldStep6ItemIndex20.setColumns(10);
		textFieldStep6ItemIndex20.setBounds(434, 200, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex20);

		JTextField textFieldStep6ItemIndex19 = new JTextField();
		textFieldStep6ItemIndex19.setColumns(10);
		textFieldStep6ItemIndex19.setBounds(434, 169, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex19);

		JTextField textFieldStep6ItemIndex18 = new JTextField();
		textFieldStep6ItemIndex18.setColumns(10);
		textFieldStep6ItemIndex18.setBounds(434, 138, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex18);

		JTextField textFieldStep6ItemIndex17 = new JTextField();
		textFieldStep6ItemIndex17.setColumns(10);
		textFieldStep6ItemIndex17.setBounds(434, 107, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex17);

		JTextField textFieldStep6ItemIndex16 = new JTextField();
		textFieldStep6ItemIndex16.setColumns(10);
		textFieldStep6ItemIndex16.setBounds(434, 76, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex16);

		JTextField textFieldStep6ItemIndex15 = new JTextField();
		textFieldStep6ItemIndex15.setColumns(10);
		textFieldStep6ItemIndex15.setBounds(434, 45, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex15);

		JTextField textFieldStep6Quantity15 = new JTextField();
		textFieldStep6Quantity15.setColumns(10);
		textFieldStep6Quantity15.setBounds(494, 45, 56, 20);
		panelStep6.add(textFieldStep6Quantity15);

		JTextField textFieldStep6Quantity16 = new JTextField();
		textFieldStep6Quantity16.setColumns(10);
		textFieldStep6Quantity16.setBounds(494, 76, 56, 20);
		panelStep6.add(textFieldStep6Quantity16);

		JTextField textFieldStep6Quantity17 = new JTextField();
		textFieldStep6Quantity17.setColumns(10);
		textFieldStep6Quantity17.setBounds(494, 107, 56, 20);
		panelStep6.add(textFieldStep6Quantity17);

		JTextField textFieldStep6Quantity18 = new JTextField();
		textFieldStep6Quantity18.setColumns(10);
		textFieldStep6Quantity18.setBounds(494, 138, 56, 20);
		panelStep6.add(textFieldStep6Quantity18);

		JTextField textFieldStep6Quantity19 = new JTextField();
		textFieldStep6Quantity19.setColumns(10);
		textFieldStep6Quantity19.setBounds(494, 169, 56, 20);
		panelStep6.add(textFieldStep6Quantity19);

		JTextField textFieldStep6Quantity20 = new JTextField();
		textFieldStep6Quantity20.setColumns(10);
		textFieldStep6Quantity20.setBounds(494, 200, 56, 20);
		panelStep6.add(textFieldStep6Quantity20);

		JTextField textFieldStep6Quantity21 = new JTextField();
		textFieldStep6Quantity21.setColumns(10);
		textFieldStep6Quantity21.setBounds(494, 231, 56, 20);
		panelStep6.add(textFieldStep6Quantity21);

		JComboBox comboBoxStep6OrderType21 = new JComboBox();
		comboBoxStep6OrderType21.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType21.setBounds(560, 231, 73, 20);
		panelStep6.add(comboBoxStep6OrderType21);

		JComboBox comboBoxStep6OrderType20 = new JComboBox();
		comboBoxStep6OrderType20.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType20.setBounds(560, 200, 73, 20);
		panelStep6.add(comboBoxStep6OrderType20);

		JComboBox comboBoxStep6OrderType19 = new JComboBox();
		comboBoxStep6OrderType19.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType19.setBounds(560, 169, 73, 20);
		panelStep6.add(comboBoxStep6OrderType19);

		JComboBox comboBoxStep6OrderType18 = new JComboBox();
		comboBoxStep6OrderType18.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType18.setBounds(560, 138, 73, 20);
		panelStep6.add(comboBoxStep6OrderType18);

		JComboBox comboBoxStep6OrderType17 = new JComboBox();
		comboBoxStep6OrderType17.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType17.setBounds(560, 107, 73, 20);
		panelStep6.add(comboBoxStep6OrderType17);

		JComboBox comboBoxStep6OrderType16 = new JComboBox();
		comboBoxStep6OrderType16.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType16.setBounds(560, 76, 73, 20);
		panelStep6.add(comboBoxStep6OrderType16);

		JComboBox comboBoxStep6OrderType15 = new JComboBox();
		comboBoxStep6OrderType15.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType15.setBounds(560, 45, 73, 20);
		panelStep6.add(comboBoxStep6OrderType15);

		JTextField textFieldStep6ItemIndex28 = new JTextField();
		textFieldStep6ItemIndex28.setColumns(10);
		textFieldStep6ItemIndex28.setBounds(637, 231, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex28);

		JTextField textFieldStep6ItemIndex27 = new JTextField();
		textFieldStep6ItemIndex27.setColumns(10);
		textFieldStep6ItemIndex27.setBounds(637, 200, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex27);

		JTextField textFieldStep6ItemIndex26 = new JTextField();
		textFieldStep6ItemIndex26.setColumns(10);
		textFieldStep6ItemIndex26.setBounds(637, 169, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex26);

		JTextField textFieldStep6ItemIndex25 = new JTextField();
		textFieldStep6ItemIndex25.setColumns(10);
		textFieldStep6ItemIndex25.setBounds(637, 138, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex25);

		JTextField textFieldStep6ItemIndex24 = new JTextField();
		textFieldStep6ItemIndex24.setColumns(10);
		textFieldStep6ItemIndex24.setBounds(637, 107, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex24);

		JTextField textFieldStep6ItemIndex23 = new JTextField();
		textFieldStep6ItemIndex23.setColumns(10);
		textFieldStep6ItemIndex23.setBounds(637, 76, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex23);

		JTextField textFieldStep6ItemIndex22 = new JTextField();
		textFieldStep6ItemIndex22.setColumns(10);
		textFieldStep6ItemIndex22.setBounds(637, 45, 56, 20);
		panelStep6.add(textFieldStep6ItemIndex22);

		JTextField textFieldStep6Quantity22 = new JTextField();
		textFieldStep6Quantity22.setColumns(10);
		textFieldStep6Quantity22.setBounds(697, 45, 56, 20);
		panelStep6.add(textFieldStep6Quantity22);

		JTextField textFieldStep6Quantity23 = new JTextField();
		textFieldStep6Quantity23.setColumns(10);
		textFieldStep6Quantity23.setBounds(697, 76, 56, 20);
		panelStep6.add(textFieldStep6Quantity23);

		JTextField textFieldStep6Quantity24 = new JTextField();
		textFieldStep6Quantity24.setColumns(10);
		textFieldStep6Quantity24.setBounds(697, 107, 56, 20);
		panelStep6.add(textFieldStep6Quantity24);

		JTextField textFieldStep6Quantity25 = new JTextField();
		textFieldStep6Quantity25.setColumns(10);
		textFieldStep6Quantity25.setBounds(697, 138, 56, 20);
		panelStep6.add(textFieldStep6Quantity25);

		JTextField textFieldStep6Quantity26 = new JTextField();
		textFieldStep6Quantity26.setColumns(10);
		textFieldStep6Quantity26.setBounds(697, 169, 56, 20);
		panelStep6.add(textFieldStep6Quantity26);

		JTextField textFieldStep6Quantity27 = new JTextField();
		textFieldStep6Quantity27.setColumns(10);
		textFieldStep6Quantity27.setBounds(697, 200, 56, 20);
		panelStep6.add(textFieldStep6Quantity27);

		JTextField textFieldStep6Quantity28 = new JTextField();
		textFieldStep6Quantity28.setColumns(10);
		textFieldStep6Quantity28.setBounds(697, 231, 56, 20);
		panelStep6.add(textFieldStep6Quantity28);

		JComboBox comboBoxStep6OrderType28 = new JComboBox();
		comboBoxStep6OrderType28.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType28.setBounds(763, 231, 73, 20);
		panelStep6.add(comboBoxStep6OrderType28);

		JComboBox comboBoxStep6OrderType27 = new JComboBox();
		comboBoxStep6OrderType27.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType27.setBounds(763, 200, 73, 20);
		panelStep6.add(comboBoxStep6OrderType27);

		JComboBox comboBoxStep6OrderType26 = new JComboBox();
		comboBoxStep6OrderType26.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType26.setBounds(763, 169, 73, 20);
		panelStep6.add(comboBoxStep6OrderType26);

		JComboBox comboBoxStep6OrderType25 = new JComboBox();
		comboBoxStep6OrderType25.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType25.setBounds(763, 138, 73, 20);
		panelStep6.add(comboBoxStep6OrderType25);

		JComboBox comboBoxStep6OrderType24 = new JComboBox();
		comboBoxStep6OrderType24.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType24.setBounds(763, 107, 73, 20);
		panelStep6.add(comboBoxStep6OrderType24);

		JComboBox comboBoxStep6OrderType23 = new JComboBox();
		comboBoxStep6OrderType23.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType23.setBounds(763, 76, 73, 20);
		panelStep6.add(comboBoxStep6OrderType23);

		JComboBox comboBoxStep6OrderType22 = new JComboBox();
		comboBoxStep6OrderType22.setModel(new DefaultComboBoxModel(OrderType.values()));
		comboBoxStep6OrderType22.setBounds(763, 45, 73, 20);
		panelStep6.add(comboBoxStep6OrderType22);

		JButton btnStep6PrevStep = new JButton(
				"<< "	+ BUNDLE.getString("PlanungstoolGUI.planning.step") + " 5: " + BUNDLE.getString("PlanungstoolGUI.planning.step5")); //$NON-NLS-1$
		btnStep6PrevStep.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep6PrevStep.setBounds(10, 268, 220, 35);
		panelStep6.add(btnStep6PrevStep);
		internalFramePlanning.setVisible(true);

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

		safetyStockFormular.put("P1", new SafetyStockEntity(lblStep3P1, spinnerStep3P1, lblStep3P1PartsToBeProduced));
		safetyStockFormular.put("P2", new SafetyStockEntity(lblStep3P2, spinnerStep3P2, lblStep3P2PartsToBeProduced));
		safetyStockFormular.put("P3", new SafetyStockEntity(lblStep3P3, spinnerStep3P3, lblStep3P3PartsToBeProduced));
		safetyStockFormular.put("E4", new SafetyStockEntity(lblStep3E4, spinnerStep3E4, lblStep3E4PartsToBeProduced));
		safetyStockFormular.put("E5", new SafetyStockEntity(lblStep3E5, spinnerStep3E5, lblStep3E5PartsToBeProduced));
		safetyStockFormular.put("E6", new SafetyStockEntity(lblStep3E6, spinnerStep3E6, lblStep3E6PartsToBeProduced));
		safetyStockFormular.put("E7", new SafetyStockEntity(lblStep3E7, spinnerStep3E7, lblStep3E7PartsToBeProduced));
		safetyStockFormular.put("E8", new SafetyStockEntity(lblStep3E8, spinnerStep3E8, lblStep3E8PartsToBeProduced));
		safetyStockFormular.put("E9", new SafetyStockEntity(lblStep3E9, spinnerStep3E9, lblStep3E9PartsToBeProduced));
		safetyStockFormular.put("E10",
				new SafetyStockEntity(lblStep3E10, spinnerStep3E10, lblStep3E10PartsToBeProduced));
		safetyStockFormular.put("E11",
				new SafetyStockEntity(lblStep3E11, spinnerStep3E11, lblStep3E11PartsToBeProduced));
		safetyStockFormular.put("E12",
				new SafetyStockEntity(lblStep3E12, spinnerStep3E12, lblStep3E12PartsToBeProduced));
		safetyStockFormular.put("E13",
				new SafetyStockEntity(lblStep3E13, spinnerStep3E13, lblStep3E13PartsToBeProduced));
		safetyStockFormular.put("E14",
				new SafetyStockEntity(lblStep3E14, spinnerStep3E14, lblStep3E14PartsToBeProduced));
		safetyStockFormular.put("E15",
				new SafetyStockEntity(lblStep3E15, spinnerStep3E15, lblStep3E15PartsToBeProduced));
		safetyStockFormular.put("E16",
				new SafetyStockEntity(lblStep3E16, spinnerStep3E16, lblStep3E16PartsToBeProduced));
		safetyStockFormular.put("E17",
				new SafetyStockEntity(lblStep3E17, spinnerStep3E17, lblStep3E17PartsToBeProduced));
		safetyStockFormular.put("E18",
				new SafetyStockEntity(lblStep3E18, spinnerStep3E18, lblStep3E18PartsToBeProduced));
		safetyStockFormular.put("E19",
				new SafetyStockEntity(lblStep3E19, spinnerStep3E19, lblStep3E19PartsToBeProduced));
		safetyStockFormular.put("E20",
				new SafetyStockEntity(lblStep3E20, spinnerStep3E20, lblStep3E20PartsToBeProduced));
		safetyStockFormular.put("E26",
				new SafetyStockEntity(lblStep3E26, spinnerStep3E26, lblStep3E26PartsToBeProduced));
		safetyStockFormular.put("E29",
				new SafetyStockEntity(lblStep3E29, spinnerStep3E29, lblStep3E29PartsToBeProduced));
		safetyStockFormular.put("E30",
				new SafetyStockEntity(lblStep3E30, spinnerStep3E30, lblStep3E30PartsToBeProduced));
		safetyStockFormular.put("E31",
				new SafetyStockEntity(lblStep3E31, spinnerStep3E31, lblStep3E31PartsToBeProduced));
		safetyStockFormular.put("E49",
				new SafetyStockEntity(lblStep3E49, spinnerStep3E49, lblStep3E49PartsToBeProduced));
		safetyStockFormular.put("E50",
				new SafetyStockEntity(lblStep3E50, spinnerStep3E50, lblStep3E50PartsToBeProduced));
		safetyStockFormular.put("E51",
				new SafetyStockEntity(lblStep3E51, spinnerStep3E51, lblStep3E51PartsToBeProduced));
		safetyStockFormular.put("E54",
				new SafetyStockEntity(lblStep3E54, spinnerStep3E54, lblStep3E54PartsToBeProduced));
		safetyStockFormular.put("E55",
				new SafetyStockEntity(lblStep3E55, spinnerStep3E55, lblStep3E55PartsToBeProduced));
		safetyStockFormular.put("E56",
				new SafetyStockEntity(lblStep3E56, spinnerStep3E56, lblStep3E56PartsToBeProduced));

		workplaceFormular.put(1, new WorkplaceEntity(spinnerStep5ShiftWorkplace1, spinnerStep5OvertimeWorkplace1));
		workplaceFormular.put(2, new WorkplaceEntity(spinnerStep5ShiftWorkplace2, spinnerStep5OvertimeWorkplace2));
		workplaceFormular.put(3, new WorkplaceEntity(spinnerStep5ShiftWorkplace3, spinnerStep5OvertimeWorkplace3));
		workplaceFormular.put(4, new WorkplaceEntity(spinnerStep5ShiftWorkplace4, spinnerStep5OvertimeWorkplace4));
		workplaceFormular.put(6, new WorkplaceEntity(spinnerStep5ShiftWorkplace6, spinnerStep5OvertimeWorkplace6));
		workplaceFormular.put(7, new WorkplaceEntity(spinnerStep5ShiftWorkplace7, spinnerStep5OvertimeWorkplace7));
		workplaceFormular.put(8, new WorkplaceEntity(spinnerStep5ShiftWorkplace8, spinnerStep5OvertimeWorkplace8));
		workplaceFormular.put(9, new WorkplaceEntity(spinnerStep5ShiftWorkplace9, spinnerStep5OvertimeWorkplace9));
		workplaceFormular.put(10, new WorkplaceEntity(spinnerStep5ShiftWorkplace10, spinnerStep5OvertimeWorkplace10));
		workplaceFormular.put(11, new WorkplaceEntity(spinnerStep5ShiftWorkplace11, spinnerStep5OvertimeWorkplace11));
		workplaceFormular.put(12, new WorkplaceEntity(spinnerStep5ShiftWorkplace12, spinnerStep5OvertimeWorkplace12));
		workplaceFormular.put(13, new WorkplaceEntity(spinnerStep5ShiftWorkplace13, spinnerStep5OvertimeWorkplace13));
		workplaceFormular.put(14, new WorkplaceEntity(spinnerStep5ShiftWorkplace14, spinnerStep5OvertimeWorkplace14));
		workplaceFormular.put(15, new WorkplaceEntity(spinnerStep5ShiftWorkplace15, spinnerStep5OvertimeWorkplace15));

		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex1, textFieldStep6Quantity1, comboBoxStep6OrderType1));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex2, textFieldStep6Quantity2, comboBoxStep6OrderType2));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex3, textFieldStep6Quantity3, comboBoxStep6OrderType3));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex4, textFieldStep6Quantity4, comboBoxStep6OrderType4));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex5, textFieldStep6Quantity5, comboBoxStep6OrderType5));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex6, textFieldStep6Quantity6, comboBoxStep6OrderType6));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex7, textFieldStep6Quantity7, comboBoxStep6OrderType7));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex8, textFieldStep6Quantity8, comboBoxStep6OrderType8));
		orderFormular.add(new OrderEntity(textFieldStep6ItemIndex9, textFieldStep6Quantity9, comboBoxStep6OrderType9));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex10, textFieldStep6Quantity10, comboBoxStep6OrderType10));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex11, textFieldStep6Quantity11, comboBoxStep6OrderType11));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex12, textFieldStep6Quantity12, comboBoxStep6OrderType12));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex13, textFieldStep6Quantity13, comboBoxStep6OrderType13));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex14, textFieldStep6Quantity14, comboBoxStep6OrderType14));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex15, textFieldStep6Quantity15, comboBoxStep6OrderType15));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex16, textFieldStep6Quantity16, comboBoxStep6OrderType16));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex17, textFieldStep6Quantity17, comboBoxStep6OrderType17));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex18, textFieldStep6Quantity18, comboBoxStep6OrderType18));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex19, textFieldStep6Quantity19, comboBoxStep6OrderType19));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex20, textFieldStep6Quantity20, comboBoxStep6OrderType20));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex21, textFieldStep6Quantity21, comboBoxStep6OrderType21));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex22, textFieldStep6Quantity22, comboBoxStep6OrderType22));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex23, textFieldStep6Quantity23, comboBoxStep6OrderType23));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex24, textFieldStep6Quantity24, comboBoxStep6OrderType24));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex25, textFieldStep6Quantity25, comboBoxStep6OrderType25));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex26, textFieldStep6Quantity26, comboBoxStep6OrderType26));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex27, textFieldStep6Quantity27, comboBoxStep6OrderType27));
		orderFormular
				.add(new OrderEntity(textFieldStep6ItemIndex28, textFieldStep6Quantity28, comboBoxStep6OrderType28));

		JButton btnStep6GenerateXml = new JButton(BUNDLE.getString("PlanungstoolGUI.btnStep6GenerateXml.text")); //$NON-NLS-1$
		btnStep6GenerateXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				readInOrders();

				final JFileChooser fc = new JFileChooser();

				// Zeige Dialog zum Auswählen einer Datei
				int returnVal = fc.showSaveDialog(frameMain);

				// Warten bis der Benutzer eine Datei ausgewählt hat
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					// Ausgewählte Datei abfragen
					File file = fc.getSelectedFile();

					// GENERATE XML OUTPUT (INPUT FOR SCSIM.DE)

					// Step 0: Quality Control
					QualityControl qc = new QualityControl();
					qc.setDelay(0);
					qc.setLoseQuantity(0);
					output.setQualityControl(qc);

					// Step 1: Forecasts
					// -- nothing to do --

					// Step 2: Sales & Directsales
					output.getSellWish().clear();
					output.getSellDirect().clear();

					output.getSellWish().add(new Item(1, userInput.getSales().getChildrenSales()));
					output.getSellWish().add(new Item(2, userInput.getSales().getWomenSales()));
					output.getSellWish().add(new Item(3, userInput.getSales().getMenSales()));

					output.getSellDirect()
							.add(new Item(1, userInput.getDirectSales().getChildrenSales(), "0.0", "0.0"));
					output.getSellDirect().add(new Item(2, userInput.getDirectSales().getWomenSales(), "0.0", "0.0"));
					output.getSellDirect().add(new Item(3, userInput.getDirectSales().getMenSales(), "0.0", "0.0"));

					// Generate XML
					XmlParser.generateOutputXml(output, file.getAbsolutePath());

					JOptionPane.showMessageDialog(frameMain, "XML-Datei wurde erstellt", "Meldung",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		btnStep6GenerateXml.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStep6GenerateXml.setBounds(616, 268, 220, 35);
		panelStep6.add(btnStep6GenerateXml);

		// INITIALIZE ACTION LISTENERS
		ActionListener switchStepsButtonActionListener = new StepButtonsActionDialog(this);

		// SET ACTION LISTENERS
		// btnStep1NextStep.addActionListener(switchStepsButtonActionListener);
		// btnStep2PrevStep.addActionListener(switchStepsButtonActionListener);
		//
		// btnStep3PrevStep.addActionListener(switchStepsButtonActionListener);
		// btnStep3NextStep.addActionListener(switchStepsButtonActionListener);
		// btnStep4PrevStep.addActionListener(switchStepsButtonActionListener);
		// btnStep4NextStep.addActionListener(switchStepsButtonActionListener);
		// btnStep5PrevStep.addActionListener(switchStepsButtonActionListener);
		// btnStep5NextStep.addActionListener(switchStepsButtonActionListener);
		// btnStep6PrevStep.addActionListener(switchStepsButtonActionListener);
		// btnStep2NextStep.addActionListener(switchStepsButtonActionListener);

		btnStep1NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInForecasts();
				switchToStep(2);
			}
		});

		btnStep2PrevStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSales();
				switchToStep(1);

			}
		});

		btnStep2NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSales();
				readInSafetyStock();
				calculateSafetyStock();
				switchToStep(3);
			}
		});

		btnStep3PrevStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToStep(2);

			}
		});

		btnStep3Recalculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSafetyStock();
				calculateSafetyStock();
			}
		});

		btnStep3NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInSafetyStock();
				calculateSafetyStock();
				calculateProductionOrder();
				switchToStep(4);
			}
		});

		btnStep4PrevStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToStep(3);
			}
		});

		btnStep4NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calculateCapacity();
				switchToStep(5);
			}
		});

		btnStep5PrevStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToStep(4);

			}
		});

		btnStep5NextStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readInCapacity();
				calculateOrders();
				switchToStep(6);
			}
		});

		btnStep6PrevStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToStep(5);

			}
		});

		displayDefaultSafetyStock();

		tabbedPane.removeAll();
		tabbedPane.add(BUNDLE.getString("PlanungstoolGUI.results.tab.introduction"), panelIntroduction);

		internalFramePlanning.setVisible(false);

		mntmLoadResultXml.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML-Dateien", "xml", "xml");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);

				// Zeige Dialog zum Auswählen einer Datei
				int returnVal = fc.showOpenDialog(getFrameMain());

				// Warten bis der Benutzer eine Datei ausgewählt hat
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					// Ausgewählte Datei abfragen
					File file = fc.getSelectedFile();

					Results results = null;

					try {

						// Datei als Result-Objekt parsen
						results = XmlParser.parseXmlFile(file.getPath());
					} catch (Exception ex) {
						results = null;

						showErrorDialog("XML-Datei konnte nicht geladen werden!", ex, frameMain);
						return;
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AUSGABE / ANZEIGE ERGEBNISSE

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// ALLGEMEINES

					// Titel des Ergebnis-Frames setzen
					internalFrameResults.setTitle(BUNDLE.getString("PlanungstoolGUI.internalFrameResults.results")
							+ " - " + BUNDLE.getString("PlanungstoolGUI.common.period") + " " + results.getPeriod()
							+ " | " + BUNDLE.getString("PlanungstoolGUI.internalFrameResults.group") + " "
							+ results.getGroup());

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// LAGERHAUS

					// Gesamtwert in Label anzeigen
					labelTotalStockValue.setText("Gesamtwert: "
							+ results.getWarehouseStock().getTotalStockValueString());

					// Bisherige angezeigte Daten entfernen
					DefaultTableModel model = (DefaultTableModel) tableWarehouse.getModel();
					removeAllRowsFromTable(model);

					// Alle Artikel im Lagerhaus auflisten
					List<Article> articles = results.getWarehouseStock().getArticles();

					for (Article article : articles) {
						model.addRow(new Object[] { article.getId(), article.getAmount(), article.getStartAmount(),
								article.getPctString(), article.getPriceString(), article.getStockValueString() });
					}

					// Clean up
					articles = null;
					model = null;

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AKTUELLER LAGERZUGANG BESTELLUNGEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableInwardStockMovement.getModel();
					removeAllRowsFromTable(model);

					// Alle angekommenen Bestellungen auflisten
					List<Order> orders = results.getInwardStockMovement().getOrders();

					for (Order order : orders) {
						model.addRow(new Object[] { new String(order.getOrderPeriod() + "-" + order.getId()),
								getModeAsString(order.getMode()), order.getArticle(), order.getAmount(),
								order.getMaterialCostsStr(), order.getOrderCostsStr(), order.getEntireCostsStr(),
								order.getPieceCostsStr() });
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// AUSSTEHENDE BESTELLUNGEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableFutureInwardStockMovement.getModel();
					removeAllRowsFromTable(model);

					// Alle ausstehenden Bestellungen auflisten
					orders = results.getFutureInwardStockMovement().getOrders();

					if (orders != null) {
						for (Order order : orders) {
							model.addRow(new Object[] { new String(order.getOrderPeriod() + "-" + order.getId()),
									order.getMode(), order.getArticle(), order.getAmount(),
									order.getInwardStockMovementAvg(), order.getInwardStockMovementMax() });
						}
					}

					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// LEERZEITEN KOSTEN

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableIdleTimeCosts.getModel();
					removeAllRowsFromTable(model);

					// Alle Leerzeiten Kosten auflisten
					List<WorkplaceCosts> workplaceCosts = results.getIdleTimeCosts().getWorkplaces();

					for (WorkplaceCosts wC : workplaceCosts) {
						model.addRow(new Object[] { wC.getId(), wC.getSetupEvents(), wC.getIdleTime(),
								wC.getWageIdleTimeCostsStr(), wC.getWageCostsStr(), wC.getMachineIdleTimeCostsStr() });
					}

					// Summe ausgeben
					if (results.getIdleTimeCosts().getSum() != null) {
						IdleTimeCostsSum sum = results.getIdleTimeCosts().getSum();
						model.addRow(new Object[] { "SUMME", sum.getSetupEvents(), sum.getIdleTime(),
								sum.getWageIdleTimeCostsStr(), sum.getWageCostsStr(), sum.getMachineIdleTimeCostsStr() });
					}
					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					// WARTELISTE ARBEITSPLATZ

					// Bisherige angezeigte Daten entfernen
					model = (DefaultTableModel) tableWaitingListWorkstations.getModel();
					removeAllRowsFromTable(model);

					// Alle Wartelisten anzeigen
					List<WorkplaceWaiting> workplacesWaiting = results.getWaitingListWorkstations().getWorkplaces();
					for (WorkplaceWaiting wW : workplacesWaiting) {

						if (wW.getWaitingList() != null) {

							model.addRow(new Object[] { wW.getId(), wW.getWaitingList().getPeriod(),
									wW.getWaitingList().getOrder(),
									wW.getWaitingList().getFirstBatch() + "-" + wW.getWaitingList().getLastBatch(),
									wW.getWaitingList().getItem(), wW.getWaitingList().getAmount(), wW.getTimeNeed() });
						} else {

							model.addRow(new Object[] { wW.getId() });
						}
					}

					try {
						productionService = new ProductionService(results);
					} catch (IOException e1) {
						e1.printStackTrace();
						return;
					}

					gui.switchToStep(1);
					gui.displayPeriod(results.getPeriod());
					gui.setResults(results);

					tabbedPane.removeAll();
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.warehousestock.title"), null,
							panelWarehouse, null);
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.inwardstockmovement.title"), null,
							panelInwardStockMovement, null);
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.futereinwardstockmovement.title"),
							null, panelFutureInwardStockMovement, null);
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.idletimecosts.title"), null,
							panelIdleTimeCosts, null);
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.waitinglist.workstations.title"),
							null, panelWaitingListWorkstations, null);
					tabbedPane.addTab(BUNDLE.getString("PlanungstoolGUI.results.tab.waitinglist.stock.title"), null,
							panelWaitingListStock, null);

					internalFramePlanning.setVisible(true);
					frameMain.setSize(893, 738);
				}
			}
		});

		frameMain.setSize(893, 730);
		System.out.println(frameMain.getSize());
	}

	protected void moveProductionDown() {

		// Get the selected list item
		int selectedIndex = listStep4ProductionOrder.getSelectedIndex();

		// If no item is selected (index = -1), nothing to do => return
		if (selectedIndex < 0)
			return;

		// Get current displayed list
		List<Production> currentList = output.getProductionList();

		// If item is last item of the list, nothing to do
		if (selectedIndex + 1 >= currentList.size()) {
			return;
		}

		// Move the item down (swap with next item)
		Collections.swap(currentList, selectedIndex, selectedIndex + 1);

		// Display changed list on gui
		displayProductionOrder();

		// Set the moved item as selected again
		listStep4ProductionOrder.setSelectedIndex(selectedIndex + 1);

	}

	protected void moveProductionUp() {

		// Get the selected list item
		int selectedIndex = listStep4ProductionOrder.getSelectedIndex();

		// If no item is selected (index = -1), nothing to do => return
		if (selectedIndex < 0)
			return;

		// Get current displayed list
		List<Production> currentList = output.getProductionList();

		// If item is first item of the list, nothing to do
		if (selectedIndex - 1 < 0) {
			return;
		}

		// Move the item up (swap with prev item)
		Collections.swap(currentList, selectedIndex, selectedIndex - 1);

		// Display changed list on gui
		displayProductionOrder();

		// Set the moved item as selected again
		listStep4ProductionOrder.setSelectedIndex(selectedIndex - 1);

	}

	private void readInCapacity() {

		List<WorkingTime> workingTimes = new ArrayList<>();
		for (int workplaceId : workplaceFormular.keySet()) {
			workingTimes.add(new WorkingTime(workplaceId, (int) workplaceFormular.get(workplaceId).getShift()
					.getValue(), (int) workplaceFormular.get(workplaceId).getOvertime().getValue()));
		}
		output.setWorkingTimeList(workingTimes);
	}

	private void readInForecasts() {

		Sales salesPeriode1 = new Sales((int) spinnerStep1P1Periode1.getValue(),
				(int) spinnerStep1P2Periode1.getValue(), (int) spinnerStep1P3Periode1.getValue());

		Sales salesPeriode2 = new Sales((int) spinnerStep1P1Periode2.getValue(),
				(int) spinnerStep1P2Periode2.getValue(), (int) spinnerStep1P3Periode2.getValue());
		Sales salesPeriode3 = new Sales((int) spinnerStep1P1Periode3.getValue(),
				(int) spinnerStep1P2Periode3.getValue(), (int) spinnerStep1P3Periode3.getValue());
		Sales salesPeriode4 = new Sales((int) spinnerStep1P1Periode4.getValue(),
				(int) spinnerStep1P2Periode4.getValue(), (int) spinnerStep1P3Periode4.getValue());

		Forecast forecast = new Forecast(salesPeriode1, salesPeriode2, salesPeriode3, salesPeriode4);

		userInput.setForecast(forecast);

	}

	private void readInOrders() {

		List<de.hska.centurion.domain.output.Order> userOrders = new ArrayList<>();

		for (OrderEntity orderEntity : orderFormular) {

			if (orderEntity.getOrderType().getSelectedIndex() > 0) {
				userOrders.add(new de.hska.centurion.domain.output.Order(Integer.parseInt(orderEntity.getItemIndex()
						.getText()), Integer.parseInt(orderEntity.getQuantity().getText()), orderEntity.getOrderType()
						.getSelectedIndex()));
			}
		}

		output.setOrderList(userOrders);
	}

	private void readInSafetyStock() {

		for (String id : safetyStockFormular.keySet()) {

			SafetyStockEntity safetyStockEntity = safetyStockFormular.get(id);
			userInput.getSafetyStock().getSafetyStocks().put(id, (Integer) safetyStockEntity.getWish().getValue());

		}

	}

	private void readInSales() {
		Sales sales = new Sales((int) spinnerStep2P1Sales.getValue(), (int) spinnerStep2P2Sales.getValue(),
				(int) spinnerStep2P3Sales.getValue());

		Sales directSales = new Sales((int) spinnerStep2P1DirectSales.getValue(),
				(int) spinnerStep2P2DirectSales.getValue(), (int) spinnerStep2P3DirectSales.getValue());

		userInput.setSales(sales);
		userInput.setDirectSales(directSales);

	}

	private void removeAllRowsFromTable(DefaultTableModel model) {
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	public void setFrameMain(JFrame frameMain) {
		this.frameMain = frameMain;
	}

	private void setResults(Results results2) {
		this.results = results2;
	}

	protected void showSplittingDialog() {

		// Get the selected list item
		int selectedIndex = listStep4ProductionOrder.getSelectedIndex();

		// If no item is selected (index = -1), nothing to do => return
		if (selectedIndex < 0)
			return;

		Production productionToSplit = output.getProductionList().get(selectedIndex);
		SplittingDialog splitDialog = new SplittingDialog(productionToSplit);
		splitDialog.setLocationRelativeTo(frameMain);
		splitDialog.setVisible(true);

		if (splitDialog.getResult() == DialogResult.OK) {
			output.splitProduction(selectedIndex, splitDialog.getQuantityA(), splitDialog.getQuantityB());
		}

		displayProductionOrder();
		listStep4ProductionOrder.setSelectedIndex(selectedIndex);
	}

	protected void switchLanguage(String string) {

		Point location = frameMain.getLocation();
		Locale.setDefault(new Locale(string));
		frameMain.setVisible(false);
		frameMain.dispose();
		App.main(new String[] { string, location.getX() + "", "" + location.getY() });
	}

	public void switchToStep(int goToStep) {

		// String validationResult = UserInputValidator.validate(userInput);
		//
		// if (validationResult != null) {
		// showErrorDialog(validationResult);
		// }

		JPanel stepToShow = stepsMap.get(goToStep);
		tabbedPanePlanning.removeAll();
		tabbedPanePlanning.add(stepToShow);
		tabbedPanePlanning.addTab(BUNDLE.getString("PlanungstoolGUI.planning.step") + " " + goToStep, stepToShow);
	}
}
