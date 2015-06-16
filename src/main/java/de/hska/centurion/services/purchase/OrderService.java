package de.hska.centurion.services.purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import de.hska.centurion.domain.gui.Forecast;
import de.hska.centurion.domain.gui.SafetyStock;
import de.hska.centurion.domain.gui.Sales;
import de.hska.centurion.domain.gui.UserInput;
import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.output.Input;
import de.hska.centurion.domain.output.Item;
import de.hska.centurion.domain.output.Order;
import de.hska.centurion.domain.output.Production;
import de.hska.centurion.domain.output.QualityControl;
import de.hska.centurion.domain.production.item.KItem;
import de.hska.centurion.io.XmlParser;
import de.hska.centurion.util.KItemBuilder;

/**
 * This class calculates the purchase orders for the simulated period.
 * 
 * @author Simon
 *
 */
public class OrderService {

	/*
	 * ======================== CONSTANTS ========================
	 */

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The UserInput-Object received from the GUI.
	 */
	private UserInput userInput;

	/**
	 * The XML-Results-Object received from the GUI.
	 */
	private Results results;

	/**
	 * the Output-Project from the UserInput-Object, where the purchase orders
	 * are stored.
	 */
	private Input suggestedOutput;

	/**
	 * the List of purchaseOrders as attribute for a better access. It's part of
	 * the suggestedOutput-attribute.
	 */
	private List<Order> purchaseOrders;

	/**
	 * A list of the purchase items with some information needed to calculate
	 * the appreciated item consumption
	 */
	private List<KItem> kItems;

	/**
	 * The production program for the next for periods.
	 */
	private Sales[] productionProgram = new Sales[4];

	/**
	 * 
	 */
	private Map<Integer, List<Integer>> consumptions = new HashMap<Integer, List<Integer>>();

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	public OrderService(UserInput userInput, Results results, Input output) {
		this.userInput = userInput;
		this.results = results;
		this.suggestedOutput = output;
		this.purchaseOrders = new ArrayList<Order>();
	}

	/*
	 * ======================== METHODS ========================
	 */

	/**
	 * The Method calculates, which K-items must be ordered.
	 * 
	 * @return a List of purchase orders
	 */
	public List<Order> calculatePurchaseOrders() {
		if (this.purchaseOrders == null) {
			this.purchaseOrders = new ArrayList<Order>();
		}

		loadKItems();
		loadProductionProgram();
		System.out.println();
		calculateAppreciatedKItemConsumption();
		System.out.println();
		examinePurchaseOrders();

		return purchaseOrders;
	}

	/**
	 * Loads the KItem list from the properties
	 */
	private void loadKItems() {
		KItemBuilder builder = null;

		try {
			builder = new KItemBuilder(results);
		} catch (IOException e) {
			e.printStackTrace();
		}

		kItems = builder.createKItemList();
		// System.out.println("K-ItemsSize: " + kItems.size());
	}

	/**
	 * Loads and calculates the production program for the next four periods.
	 */
	private void loadProductionProgram() {
		// get the production list of P1, P2 and P3 for the current period
		List<Production> productions = new ArrayList<Production>();
		for (Production production : suggestedOutput.getProductionList()) {
			if (production.getArticle() == 1 || production.getArticle() == 2
					|| production.getArticle() == 3) {
				productions.add(production);
			}
		}

		// create a sales-object out of the current production programm
		productionProgram[0] = new Sales(productions.get(0).getQuantity(),
				productions.get(1).getQuantity(), productions.get(2)
						.getQuantity());

		// get the forecasts
		Sales p1 = userInput.getForecast().getFirstPeriod();
		Sales p2 = userInput.getForecast().getSecondPeriod();
		Sales p3 = userInput.getForecast().getThirdPeriod();
		Sales p4 = userInput.getForecast().getFourthPeriod();

		// calculate the production programm for the next three periods
		// pxProductionProgram = currentProductionProgram + (pxForecast -
		// currentPeriodForecast)
		productionProgram[1] = new Sales(
				productionProgram[0].getChildrenSales()
						+ (p2.getChildrenSales() - p1.getChildrenSales()),
				productionProgram[0].getWomenSales()
						+ (p2.getWomenSales() - p1.getWomenSales()),
				productionProgram[0].getMenSales()
						+ (p2.getMenSales() - p1.getMenSales()));
		productionProgram[2] = new Sales(
				productionProgram[0].getChildrenSales()
						+ (p3.getChildrenSales() - p1.getChildrenSales()),
				productionProgram[0].getWomenSales()
						+ (p3.getWomenSales() - p1.getWomenSales()),
				productionProgram[0].getMenSales()
						+ (p3.getMenSales() - p1.getMenSales()));
		productionProgram[3] = new Sales(
				productionProgram[0].getChildrenSales()
						+ (p4.getChildrenSales() - p1.getChildrenSales()),
				productionProgram[0].getWomenSales()
						+ (p4.getWomenSales() - p1.getWomenSales()),
				productionProgram[0].getMenSales()
						+ (p4.getMenSales() - p1.getMenSales()));

		int period = 1;
		for (Sales production : productionProgram) {
			System.out.println("productionProgram[p"
					+ period
					+ "]="
					+ production
					+ ", sum="
					+ (production.getChildrenSales()
							+ production.getWomenSales() + production
								.getMenSales()));
			period++;
		}
	}

	/**
	 * Calculate the consumption of the kitems in the next four periods.
	 */
	private void calculateAppreciatedKItemConsumption() {
		// iterate over every KItem
		for (KItem item : kItems) {
			List<Integer> itemConsumption = new ArrayList<Integer>();

			// calculate the consumption for p1-p4
			for (int i = 0; i < 4; i++) {
				int consumption = item.getUsage().getUsageP(1)
						* productionProgram[i].getChildrenSales()
						+ item.getUsage().getUsageP(2)
						* productionProgram[i].getWomenSales()
						+ item.getUsage().getUsageP(3)
						* productionProgram[i].getMenSales();
				itemConsumption.add(consumption);
			}

			System.out.println("Item=" + item.getNumber() + ", Consumption: "
					+ "\tp1=" + itemConsumption.get(0) + "\tp2="
					+ itemConsumption.get(1) + "\tp3=" + itemConsumption.get(2)
					+ "\tp4=" + itemConsumption.get(3));

			// add the consumptions to the consumption-map
			consumptions.put(item.getNumber(), itemConsumption);
		}
	}

	/**
	 * examine for which items a order is necessary
	 */
	private void examinePurchaseOrders() {
		// iterate over all KItems
		for (KItem item : kItems) {
			// get stock of current item
			double stock = item.getStock();
			// get consumptions (next four periods) for the current item
			List<Integer> itemConsumptions = consumptions.get(item.getNumber());

			boolean nextItem = false;
			System.out.println("currentItem=" + item.getNumber()
					+ ", deliveryTime=" + item.getDeliveryTime() * 5
					+ ", deviation=" + item.getDiviation() * 5);
			// iterate over the consumptions. p + 1 = currentPeriod

			// a variable for a matching order
			de.hska.centurion.domain.input.components.Order matchingOrder = null;
			// das ist h�sslich aber hier brauche ich eine andere Order-Klasse
			// iterate over all future incoming orders
			if (results.getFutureInwardStockMovement()!=null) {
				for (de.hska.centurion.domain.input.components.Order order : results
						.getFutureInwardStockMovement().getOrders()) {
					// check if item is in future incoming orders
					if (order.getArticle() == item.getNumber()) {
						matchingOrder = order;
					}
				}
			}

			int orderIncomeP = -1;
			int orderIncomeD = -1;
			if (matchingOrder != null) {
				String incomeStr = matchingOrder.getInwardStockMovementAvg();
				String[] income = incomeStr.split("-", 3);

				orderIncomeP = Integer.parseInt(income[0])
						- results.getPeriod() - 1;
				orderIncomeD = Integer.parseInt(income[1]) - 1;
			}
			System.out.println("orderIncomeP=" + orderIncomeP
					+ ", orderIncomeD=" + orderIncomeD);
			// Is the order already arrived?
			boolean orderIncome = false;

			for (int p = 0; p < itemConsumptions.size(); p++) {
				// iterate over the current day in period. d + 1 = current day
				System.out.println("\t\t" + (p + 1) + ". Period");
				for (int d = 0; d < 5; d++) {
					// stock after current day = current stock -
					// (itemConsumption of the current period / 5)
					// because a period has 5 days.
					stock = stock - itemConsumptions.get(p) / 5;

					if (p == orderIncomeP && d == orderIncomeD) {
						System.out.println("\t\t\t\tOrderIncoming: " + (p + 1)
								+ "/" + (d + 1) + " Amount: "
								+ matchingOrder.getAmount());
						stock = stock + matchingOrder.getAmount();
						orderIncome = true;
					}

					System.out.println("\t\t\t\t" + (d + 1) + ". Day: " + stock
							+ " Stock");

					// when stock is empty ...
					if ((stock <= 0 && matchingOrder == null)
							|| (stock <= 0 && orderIncome)
							|| (stock <= 0 && p != orderIncomeP
									&& p != (orderIncomeP + 1) && (d - orderIncomeD) <= 1)) {
						// check if Order is necessary
						checkIfOrderIsNecessary(item, p, d, orderIncome);
						nextItem = true;
						break;
					}
				}
				if (nextItem == true) {
					break;
				}
			}
		}
	}

	/**
	 * Check if a order is necessary
	 * 
	 * @param item
	 *            the item to be checked
	 * @param p
	 *            the period in which the item will be empty
	 * @param d
	 *            the day on which the item will be empty
	 */
	private void checkIfOrderIsNecessary(KItem item, int p, int d,
			boolean orderInDelivery) {
		// calculate in how many days item stock is empty ...
		int itemIsOut = p * 5 + (d + 1);
		// and calculate the days until the purchase order
		// arrives ...
		float deliveryTime = (float) (item.getDeliveryTime() * 5);
		// and then calculate when a order is necessary (in
		// days)
		double orderNecessary = itemIsOut - deliveryTime;
		System.out.println();
		System.out.println("\t\t\t\t\t\tOrder Necessary <= 5.0?");
		System.out.println("\t\t\t\t\t\titemIsOut=" + itemIsOut
				+ ", maxDeliveryTime=" + deliveryTime + ", orderNecessary="
				+ orderNecessary);
		// compare if a order is necessary in less than 5 days
		// (= one period)
		if (orderNecessary <= 5.0) {
			// calculate average consumptions
			List<Integer> currentConsumptions = consumptions.get(item
					.getNumber());
			float avgConsumption = 0.0f;
			for (Integer consumption : currentConsumptions) {
				avgConsumption += consumption;
			}
			avgConsumption /= currentConsumptions.size();
			System.out.println("\t\t\t\t\t\tavgConsumption=" + avgConsumption);
			float stackMultiplicator = ((avgConsumption * (deliveryTime / 5.0f)) / item
					.getstack());
			System.out.println("\t\t\t\t\t\tstackMultiplicator=" + stackMultiplicator);

			int roundedMultiplicator = (int)(Math.ceil(stackMultiplicator));
			System.out.println("\t\t\t\t\t\troundedMultiplicator=" + roundedMultiplicator);

			// create order object
			Order order = new Order(item.getNumber(), item.getstack()
					* roundedMultiplicator, 5);

			// check if a fast order is necessary
			if (orderNecessary < 0.0) {
				// calculate if a fast order let the item arrive
				// before it is out (just for fun)
				order.setModus(4);

				if (orderInDelivery) {
					// If a order is already in delivery and the need for a fast
					// order is there buy a higher amount. (4 x quantity)
					order.setQuantity(order.getQuantity()*roundedMultiplicator*2); // obsolete
				}
				double makeFastOrder = itemIsOut - (deliveryTime / 2.0);
				// check if a fast order arrives before the item
				// is out
				if (makeFastOrder < 0.0) {
					// if this happens the stock is out before
					// the new order arrives
					// TODO what is to do when the stock is
					// empty before a new fast order arrives
				}
			}
			System.out.println();
			System.out.println("\t\t\t\t\t\tOrder:");
			System.out.println("\t\t\t\t\t\t" + order);
			System.out.println();
			purchaseOrders.add(order);
		}

	}
	// public static void main(String[] args) throws JAXBException {
	// UserInput ui = new UserInput();
	// ui.setForecast(new Forecast(new Sales(200, 150, 100), new Sales(200,
	// 150, 100), new Sales(150, 150, 150), new Sales(100, 100, 150)));
	// ui.setSales(new Sales(200, 150, 100));
	// ui.setSafetyStock(new SafetyStock(100));
	// Results results = XmlInputParser
	// .parseXmlFile("C:\\Users\\Simon\\Desktop\\input.xml");
	// Input output = new Input();
	// output.setQualityControl(new QualityControl());
	// output.addSellWish(new Item(1, 200));
	// output.addSellWish(new Item(2, 150));
	// output.addSellWish(new Item(3, 100));
	// output.addProduction(new Production(1, 200));
	// output.addProduction(new Production(2, 200));
	// output.addProduction(new Production(3, 200));
	// OrderService os = new OrderService(ui, results, output);
	// List<Order> orders = os.calculatePurchaseOrders();
	//
	// System.out.println();
	// System.out.println();
	//
	// for (Order order : orders) {
	// System.out.println("Order: " + order);
	// }
	// }
}
