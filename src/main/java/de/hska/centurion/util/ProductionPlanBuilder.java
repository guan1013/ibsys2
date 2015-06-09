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
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 * 
	 */
	public ProductionPlanBuilder() throws IOException {
		// Get plan.properties from classpath
		is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());

		// Create new property object from plan.properties
		ProductionPlanBuilder.prop = new Properties();
		prop.load(is);
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	private InputStream is;

	private static Properties prop;

	/*
	 * ======================== METHODS ========================
	 */
	/**
	 * Creates all production plans listed in plan.properties file
	 * 
	 * 
	 * 
	 * @param xmlResuls
	 *            results from output.xml
	 * 
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 * @return full list of all production plans
	 */
	public static List<ProductionPlan> createProductionPlans(Results xmlResuls) {

		@SuppressWarnings("unused")
		Results xmlResults = xmlResuls;

		// Create a new empty list of ProductionPlans
		List<ProductionPlan> productionPlans = new ArrayList<ProductionPlan>();

		// Get all known productionplans from properties
		final int plansCount = Integer.parseInt(prop.getProperty(Constants
				.getPlanInitKey() + Constants.getCountKey()));
		int i = 0;
		// Foreach Production Plan build the associated objects
		while (i < plansCount) {
			i++;

			// Get productionplan name
			String planName = prop.getProperty(Constants.getPlanInitKey() + i
					+ Constants.getNameKey());

			// Get production plan producer
			String planProducer = prop.getProperty(Constants.getPlanInitKey()
					+ i + Constants.getInitKey());

			Workplace producer = getWorkspace(planProducer, planName, xmlResuls);

			// Add current Production Plan to result list
			productionPlans.add(new ProductionPlan(planName, producer));

		}

		return productionPlans;
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
	private static Workplace getWorkspace(String number, String outputString,
			Results xmlResults) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = Constants.getWpInitKey() + "." + number + "."
				+ outputString;

		Item output = getItem(outputString, xmlResults);

		// Get all known inputs for this workplace from properties
		List<ProductionInput> inputs = new ArrayList<ProductionInput>();
		final int inputCount = Integer.parseInt(propertyString
				+ Constants.getInputKey() + Constants.getCountKey());
		int inputIndex = 0;

		while (inputIndex < inputCount) {
			inputIndex++;

			// Get current interval's input for this workplace and add it to its
			// input list
			inputs.add(getInput(propertyString, inputIndex, xmlResults));
		}

		// Get open orders from output.xml

		List<WorkplaceWaiting> workplaceWaitings = xmlResults
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
		Integer productionTime = Integer.parseInt(prop
				.getProperty(propertyString + Constants.getPtimeKey()));

		// Get workplace.setupTime from properties
		Integer setupTime = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getStimeKey()));

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
	private static ProductionInput getInput(String workplaceProperty,
			int index, Results xmlResults) {
		String propertyString = workplaceProperty + index;

		// Get item identifier from properties
		String item = prop.getProperty(propertyString + Constants.getItemKey());

		// Get input.quantity from properties
		Integer quantity = Integer.parseInt(propertyString
				+ Constants.getQuantityKey());

		// Get input.producer from properties
		String producerNumber = propertyString + Constants.getProducerKey();

		Workplace producer;
		if (Integer.parseInt(producerNumber) > 0) {
			producer = getWorkspace(producerNumber, item, xmlResults);
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
	private static Item getItem(String itemString, Results xmlResults) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = Constants.getItemInitKey() + itemString;

		// Get item.type from properties
		String type = prop.getProperty(propertyString + Constants.getTypeKey());

		// Get item.number from properties
		Integer number = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getNumberKey()));

		// Get item.name from properties
		String name = prop.getProperty(propertyString + Constants.getNameKey());

		// Get item.value from XML

		String priceString = xmlResults.getWarehousestock().getArticles()
				.get(number).getPriceString();

		priceString = priceString.replace(',', '.');
		Double value = Double.parseDouble(priceString);

		// Get item.stock form XML
		Integer stock = xmlResults.getWarehousestock().getArticles()
				.get(number).getAmount();

		// Determine type of item
		Item item;
		if (type.equalsIgnoreCase(ItemTypeEnum.E.toString())) {

			// Create a new EItem
			item = new EItem(number, name, value, stock);
		} else if (type.equalsIgnoreCase(ItemTypeEnum.P.toString())) {
			Double revenue = Double.parseDouble(prop.getProperty(propertyString
					+ Constants.getRevenueKey()));

			item = new PItem(number, name, value, stock, revenue);

		} else if (type.equalsIgnoreCase(ItemTypeEnum.K.toString())) {
			Integer stack = Integer.parseInt(prop.getProperty(propertyString
					+ Constants.getStackKey()));
			Double orderCosts = Double.parseDouble(prop
					.getProperty(propertyString + Constants.getOrderKey()));
			Double deliveryTime = Double.parseDouble(prop
					.getProperty(propertyString + Constants.getTimeKey()));
			Double diviation = Double.parseDouble(prop
					.getProperty(propertyString + Constants.getDivKey()));
			item = new KItem(number, name, value, stock, stack, orderCosts,
					deliveryTime, diviation);
		} else {
			item = null;
		}

		return item;
	}

}
