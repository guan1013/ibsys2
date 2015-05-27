package de.hska.centurion.main.util;

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
		// Foreach productionplan build the associated objects
		while (i < plansCount) {
			i++;

			// Get productionplan identifier
			String prodPlan = prop.getProperty(Constants.getPlanInitKey() + i);

			// Create a new empty list of workplaces
			List<Workplace> workplaces = new ArrayList<Workplace>();

			// Get all known workplaces in current interval's production plan
			final int workplacesCount = Integer.parseInt(prop
					.getProperty(prodPlan + Constants.getWorkplaceKey()
							+ Constants.getCountKey()));
			int workplaceNumber = 0;

			while (workplaceNumber < workplacesCount) {
				workplaceNumber++;

				// Get current interval's workplace with all associated objects
				// and add it to workplaces list
				workplaces.add(getWorkplace(prodPlan, workplaceNumber));
			}

			// add current interval's production plan to productionplan list

			productionPlans.add(new ProductionPlan(prodPlan, workplaces));

		}

		return productionPlans;
	}

	/**
	 * Get the current index's workplace form the current interval's production
	 * plan
	 * 
	 * @param prodPlan
	 *            current interval's production plan identifier
	 * @param workplaceNumber
	 *            index of the current workplace
	 * @return current index's workplace with all associated objects
	 */
	private Workplace getWorkplace(String prodPlan, int workplaceNumber) {

		// Build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = prodPlan + Constants.getWorkplaceKey()
				+ workplaceNumber;

		// Get workplace.number from properties
		Integer number = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getNumberKey()));

		// Get item identifier form properties
		String item = prop.getProperty(propertyString
				+ Constants.getOutputKey());

		// Get workplace.output from properties by comparing the item identfier
		// to the item list
		Item output = getItem(item);

		// Create a new empty list of inputs
		List<Input> inputs = new ArrayList<Input>();

		// Get all known inputs for this workplace from properties
		final int inputCount = Integer.parseInt(propertyString
				+ Constants.getInputKey() + Constants.getCountKey());
		int inputNumber = 0;

		while (inputNumber < inputCount) {
			inputNumber++;

			// Get current interval's input for this workplace and add it to its
			// input
			// list
			inputs.add(getInput(prodPlan, workplaceNumber, inputNumber));
		}

		// Create a new empty list of production orders
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
	 * Get current index's input form current index's workplace of current
	 * interval's production plan
	 * 
	 * @param prodPlan
	 *            current interval's production plan identifier
	 * @param workplaceNumber
	 *            index of the current workplace
	 * @param inputNumber
	 *            index of the current input
	 * @return current index's input with all associated objects
	 */
	private Input getInput(String prodPlan, int workplaceNumber, int inputNumber) {

		// build the commonly used string to access the key:value pairs in
		// properties
		String propertyString = prodPlan + Constants.getWorkplaceKey()
				+ workplaceNumber + Constants.getInputKey() + inputNumber;

		// Get item identifier form properties
		String itemString = prop.getProperty(propertyString
				+ Constants.getItemKey());

		// Get input.item from properties by comparing the item identfier
		// to the item list
		Item item = getItem(itemString);

		// Get input.quantity from properties
		Integer quantity = Integer.parseInt(propertyString
				+ Constants.getQuantityKey());

		// Get input.producer from properties
		Integer producer = Integer.parseInt(propertyString
				+ Constants.getProducerKey());

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

		// @TODO Get stock form XML
		Integer stock = 0;

		// Determine type of item
		Item item;
		if (type == ItemTypeEnum.E.toString()) {

			// Create a new EItem
			item = new EItem(number, name, value, stock);
		} else if (type == ItemTypeEnum.P.toString()) {
			item = new PItem(number, name, value, stock);

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
