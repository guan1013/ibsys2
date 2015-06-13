package de.hska.centurion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.WaitingList;
import de.hska.centurion.domain.input.components.WorkplaceWaiting;
import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.EItem;
import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.item.KItem;
import de.hska.centurion.domain.production.item.PItem;
import de.hska.centurion.domain.production.resources.ItemTypeEnum;
import de.hska.centurion.domain.production.workplace.ProductionInput;
import de.hska.centurion.domain.production.workplace.Workplace;

/**
 * This Class holds the methods to build the ProductionPlan Objects with all
 * associated objects from the resource: plan.properties
 * 
 * @author Matthias Schnell
 */
public class ProductionPlanBuilder {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */
	/**
	 * Standard Constructor
	 * 
	 * @param xmlResults
	 *            results from output.xml
	 * 
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 * 
	 */
	public ProductionPlanBuilder(Results xmlResults) throws IOException {
		// Get plan.properties from classpath
		this.is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());

		// Create new property object from plan.properties
		this.prop = new Properties();
		this.prop.load(this.is);

		this.xmlResults = xmlResults;

	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	private InputStream is;

	private Properties prop;

	private Results xmlResults;

	/*
	 * ======================== METHODS ========================
	 */

	/**
	 * Creates all production plans listed in plan.properties file
	 * 
	 * @return full list of all production plans
	 */
	public List<ProductionPlan> createProductionPlans() {

		// Create a new empty list of ProductionPlans
		List<ProductionPlan> productionPlans = new ArrayList<ProductionPlan>();

		// Get all known productionplans from properties
		final int plansCount = Integer.parseInt(this.prop.getProperty(Constants
				.getPlanInitKey() + Constants.getCountKey()));
		int i = 0;
		// Foreach Production Plan build the associated objects
		while (i < plansCount) {
			i++;

			// Get productionplan name
			String planName = this.prop.getProperty(Constants.getPlanInitKey()
					+ "." + i + Constants.getNameKey());

			ProductionPlan plan = createProductionPlan(planName);
			// Add current Production Plan to result list
			productionPlans.add(plan);

		}

		return productionPlans;
	}

	/**
	 * 
	 * 
	 * @param outputItem
	 *            final output of this production plan
	 * @return
	 */
	public ProductionPlan createProductionPlan(String outputItem) {

		// Get name of final output producer workplace
		String outputProducer = this.prop.getProperty(Constants
				.getItemInitKey()
				+ "."
				+ outputItem
				+ Constants.getProducerKey());

		return new ProductionPlan(outputItem, getWorkspace(outputProducer,
				outputItem));
	}

	/**
	 * Get a workspace for requested production plan
	 * 
	 * @param number
	 *            of workplace in properties
	 * @param outputString
	 *            number of output item in properties
	 * @return workspace for requested production plan
	 */
	private Workplace getWorkspace(String number, String outputString) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = Constants.getWpInitKey() + "." + number + "."
				+ outputString;

		// System.out.println(propertyString);

		Item output = getItem(outputString);

		// Get all known inputs for this workplace from properties
		List<ProductionInput> inputs = new ArrayList<ProductionInput>();
		final int inputCount = Integer.parseInt(this.prop
				.getProperty(propertyString + Constants.getInputKey()
						+ Constants.getCountKey()));
		int inputIndex = 0;

		while (inputIndex < inputCount) {
			inputIndex++;

			// Get current interval's input for this workplace and add it to its
			// input list
			inputs.add(getInput(propertyString, inputIndex));
		}

		// Get open orders from output.xml

		List<WorkplaceWaiting> workplaceWaitings = this.xmlResults
				.getWaitingListWorkstations().getWorkplaces();

		Integer openOrders = 0;

		// Check if unfinished orders are still at this workplace
		for (WorkplaceWaiting workplaceWaiting : workplaceWaitings) {
			if (workplaceWaiting.getId() == Integer.parseInt(number)) {
				WaitingList waitinglist = workplaceWaiting.getWaitingList();
				if (waitinglist != null
						&& waitinglist.getItem() == output.getNumber()) {
					openOrders = waitinglist.getAmount();
				}
			}
		}

		// Get workplace.productionTime from properties
		Integer productionTime = Integer.parseInt(this.prop
				.getProperty(propertyString + Constants.getPtimeKey()));

		// Get workplace.setupTime from properties
		Integer setupTime = Integer.parseInt(this.prop
				.getProperty(propertyString + Constants.getStimeKey()));

		// Create a new workplace with all associated objects
		Workplace workplace = new Workplace(number, output, inputs, openOrders,
				productionTime, setupTime);

		return workplace;
	}

	/**
	 * Get Input for requested workplace
	 * 
	 * @param workplaceProperty
	 *            String of workplace in properties
	 * @param index
	 *            index of current input in properties
	 * @return Input of requested workplace
	 */
	private ProductionInput getInput(String workplaceProperty, int index) {
		String propertyString = workplaceProperty + Constants.getInputKey()
				+ "." + index;

		// System.out.println(propertyString);

		// Get item identifier from properties
		String item = this.prop.getProperty(propertyString
				+ Constants.getItemKey());

		// Get input.quantity from properties
		Integer quantity = Integer.parseInt(this.prop
				.getProperty(propertyString + Constants.getQuantityKey()));

		// Get input.producer from properties
		String producerNumber = this.prop.getProperty(propertyString
				+ Constants.getProducerKey());

		Workplace producer;
		if (Integer.parseInt(producerNumber) > 0) {
			producer = getWorkspace(producerNumber, item);
		} else {
			producer = null;
		}

		// Create a new input with all associated objects
		ProductionInput input = new ProductionInput(item, quantity, producer);

		return input;
	}

	/**
	 * Get an item from item list in plan.properties
	 * 
	 * @param itemString
	 *            identifier of this item
	 * @return an K, P or E item
	 */
	private Item getItem(String itemString) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = Constants.getItemInitKey() + "." + itemString;

		// System.out.println(propertyString);

		// Get item.type from properties
		String type = this.prop.getProperty(propertyString
				+ Constants.getTypeKey());

		// Get item.number from properties
		Integer number = Integer.parseInt(this.prop.getProperty(propertyString
				+ Constants.getNumberKey()));

		// Get item.name from properties
		String name = this.prop.getProperty(propertyString
				+ Constants.getNameKey());

		// Get item.value from XML

		String priceString = this.xmlResults.getWarehousestock().getArticles()
				.get(number - 1).getPriceString();

		priceString = priceString.replace(',', '.');
		Double value = Double.parseDouble(priceString);

		// Get item.stock form XML
		Integer stock = this.xmlResults.getWarehousestock().getArticles()
				.get(number - 1).getAmount();

		// Get item.producer from XML
		String producer = this.prop.getProperty(propertyString
				+ Constants.getProducerKey());

		// Determine type of item
		Item item;
		if (type.equalsIgnoreCase(ItemTypeEnum.E.toString())) {

			// Create a new EItem
			item = new EItem(number, name, value, stock, producer);

		} else if (type.equalsIgnoreCase(ItemTypeEnum.P.toString())) {

			// Create new PItem
			Double revenue = Double.parseDouble(this.prop
					.getProperty(propertyString + Constants.getRevenueKey()));

			item = new PItem(number, name, value, stock, revenue, producer);

		} else if (type.equalsIgnoreCase(ItemTypeEnum.K.toString())) {

			// Create new KItem
			Integer stack = Integer.parseInt(this.prop
					.getProperty(propertyString + Constants.getStackKey()));

			Double orderCosts = Double.parseDouble(this.prop
					.getProperty(propertyString + Constants.getOrderKey()));

			Double deliveryTime = Double.parseDouble(this.prop
					.getProperty(propertyString + Constants.getTimeKey()));

			Double diviation = Double.parseDouble(this.prop
					.getProperty(propertyString + Constants.getDivKey()));

			item = new KItem(number, name, value, stock, stack, orderCosts,
					deliveryTime, diviation, producer);
		} else {
			item = null;
		}

		return item;
	}

}
