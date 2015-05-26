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

public class ProductionPlanBuilder {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */
	public ProductionPlanBuilder() throws IOException {
		is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());
		prop = new Properties();
		prop.load(is);
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	InputStream is;
	Properties prop;

	/*
	 * ======================== METHODS ========================
	 */
	public List<ProductionPlan> createProductionPlans() {

		List<ProductionPlan> productionPlans = new ArrayList<ProductionPlan>();

		final int plansCount = Integer.parseInt(prop.getProperty(Constants
				.getPlanInitKey() + Constants.getCountKey()));
		int i = 0;
		while (i < plansCount) {
			i++;

			String prodPlan = prop.getProperty(Constants.getPlanInitKey() + i);

			final int workplacesCount = Integer.parseInt(prop
					.getProperty(prodPlan + Constants.getWorkplaceKey()
							+ Constants.getCountKey()));
			int workplaceNumber = 0;

			List<Workplace> workplaces = new ArrayList<Workplace>();
			while (workplaceNumber < workplacesCount) {
				workplaceNumber++;
				workplaces.add(getWorkplace(prodPlan, workplaceNumber));
			}

			productionPlans.add(new ProductionPlan(prodPlan, workplaces));

		}

		return productionPlans;
	}

	private Workplace getWorkplace(String prodPlan, int workplaceNumber) {

		String propertyString = prodPlan + Constants.getWorkplaceKey()
				+ workplaceNumber;

		Integer number = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getNumberKey()));

		String item = prop.getProperty(propertyString
				+ Constants.getOutputKey());
		Item output = getItem(item);

		List<Input> inputs = new ArrayList<Input>();
		final int inputCount = Integer.parseInt(propertyString
				+ Constants.getInputKey() + Constants.getCountKey());
		int inputNumber = 0;

		while (inputNumber < inputCount) {
			inputNumber++;
			inputs.add(getInput(prodPlan, workplaceNumber, inputNumber));
		}

		List<ProductionOrder> orders = new ArrayList<ProductionOrder>();

		Integer productionTime = Integer.parseInt(prop
				.getProperty(propertyString + Constants.getPtimeKey()));

		Integer setupTime = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getStimeKey()));

		Workplace workplace = new Workplace(number, output, inputs, orders,
				productionTime, setupTime);

		return workplace;
	}

	private Input getInput(String prodPlan, int workplaceNumber, int inputNumber) {

		String propertyString = prodPlan + Constants.getWorkplaceKey()
				+ workplaceNumber + Constants.getInputKey() + inputNumber;

		String itemString = prop.getProperty(propertyString
				+ Constants.getItemKey());
		Item item = getItem(itemString);

		Integer quantity = Integer.parseInt(propertyString
				+ Constants.getQuantityKey());
		Integer producer = Integer.parseInt(propertyString
				+ Constants.getProducerKey());

		Input input = new Input(item, quantity, producer);
		return input;
	}

	private Item getItem(String itemString) {

		String propertyString = Constants.getItemInitKey() + itemString;

		Item item;

		String type = prop.getProperty(propertyString + Constants.getTypeKey());
		Integer number = Integer.parseInt(prop.getProperty(propertyString
				+ Constants.getNumberKey()));
		String name = prop.getProperty(propertyString + Constants.getNameKey());
		Double value = Double.parseDouble(prop.getProperty(propertyString
				+ Constants.getValueKey()));

		// @TODO Get stock form XML
		Integer stock = 0;

		if (type == ItemTypeEnum.E.toString()) {
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
