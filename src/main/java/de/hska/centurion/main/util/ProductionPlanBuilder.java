package de.hska.centurion.main.util;

import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.EItem;
import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.item.KItem;
import de.hska.centurion.domain.production.item.PItem;
import de.hska.centurion.domain.production.order.ProductionOrder;
import de.hska.centurion.domain.production.resources.ItemTypeEnum;
import de.hska.centurion.domain.production.workplace.Input;
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
	 */
	public ProductionPlanBuilder() throws IOException {
		// Get plan.properties from classpath
		is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());

		// Create new property object from plan.properties
		prop = new Properties();
		prop.load(is);
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * Input stream for plan.properties file
	 */
	private InputStream is;

	/**
	 * property object for plan.properties file
	 */
	private Properties prop;

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

			Workplace producer = getWorkspace(planProducer, planName);

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
	private Workplace getWorkspace(String number, String outputString) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = Constants.getWpInitKey() + "." + number + "."
				+ outputString;

		Item output = getItem(outputString);

		// Get all known inputs for this workplace from properties
		List<Input> inputs = new ArrayList<Input>();
		final int inputCount = Integer.parseInt(propertyString
				+ Constants.getInputKey() + Constants.getCountKey());
		int inputIndex = 0;

		while (inputIndex < inputCount) {
			inputIndex++;

			// Get current interval's input for this workplace and add it to its
			// input list
			inputs.add(getInput(propertyString, inputIndex));
		}

		// Create a new empty list of production orders
		// TODO Get open orders from XML
		List<ProductionOrder> orders = new ArrayList<ProductionOrder>();

		// Get workplace.productionTime from properties
		Integer productionTime = Integer.parseInt(prop
				.getProperty(propertyString + Constants.getPtimeKey()));

		// Get workplace.setupTime from properties
		Integer setupTime = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getStimeKey()));

		// Create a new workplace with all associated objects
		Workplace workplace = new Workplace(number, output, inputs, orders,
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
	private Input getInput(String workplaceProperty, int index) {
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
			producer = getWorkspace(producerNumber, item);
		} else {
			producer = null;
		}

		// Create a new input with all associated objects
		Input input = new Input(item, quantity, producer);

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
		String propertyString = Constants.getItemInitKey() + itemString;

		// Get item.type from properties
		String type = prop.getProperty(propertyString + Constants.getTypeKey());

		// Get item.number from properties
		Integer number = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getNumberKey()));

		// Get item.name from properties
		String name = prop.getProperty(propertyString + Constants.getNameKey());

		// Get item.value from properties
		Double value = Double.parseDouble(prop.getProperty(propertyString
				+ Constants.getValueKey()));

		// TODO Get stock form XML
		Integer stock = 0;

		// Determine type of item
		Item item;
		if (type == ItemTypeEnum.E.toString()) {

			// Create a new EItem
			item = new EItem(number, name, value, stock);
		} else if (type == ItemTypeEnum.P.toString()) {
			Double revenue = Double.parseDouble(prop.getProperty(propertyString
					+ Constants.getRevenueKey()));

			item = new PItem(number, name, value, stock, revenue);

		} else if (type == ItemTypeEnum.K.toString()) {
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
