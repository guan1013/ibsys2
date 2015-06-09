package de.hska.centurion.services.production;

import java.io.IOException;
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
import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.item.PItem;
import de.hska.centurion.domain.production.workplace.ProductionInput;
import de.hska.centurion.domain.production.workplace.Workplace;
import de.hska.centurion.io.XmlInputParser;
import de.hska.centurion.util.ProductionPlanBuilder;

public class ProductionService {

	/**
	 * Standard Constructor
	 * 
	 * @param xmlPath
	 *            path to output.xml
	 * 
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 * @throws JAXBException
	 *             throws exception if output.xml isn't parsable
	 */
	public ProductionService(String xmlPath) throws IOException, JAXBException {

		this.xmlResults = XmlInputParser.parseXmlFile(xmlPath);

		this.plans = ProductionPlanBuilder
				.createProductionPlans(this.xmlResults);

		this.userInput = new UserInput();
	}

	/**
	 * List of all production plans
	 */
	private List<ProductionPlan> plans;

	/**
	 * results from output.xml
	 */
	private Results xmlResults;

	private UserInput userInput;

	private Map<String, Integer> productions;

	private Input suggestedOutput;

	public Input calculateProductionOrder() {

		for (ProductionPlan plan : plans) {

			PItem output = (PItem) plan.getProducer().getOutput();

			if (output.getFixCostsCoverage() > 0) {

				// TODO: Order isn't cost efficient exception

			}

			Map<String, Integer> roundTripTimes = new HashMap<String, Integer>();
			
			
			roundTripTimes = calculateRoundTripTime(plan.getProducer(), 0, roundTripTimes);

		}

		return null;
	}

	private Map<String, Integer> calculateRoundTripTime(Workplace workplace,
			Integer quantity, Map<String, Integer> roundTripTimes) {

		String workplaceNumber = workplace.getNumber();
		if (roundTripTimes.get(workplaceNumber) == null) {

			roundTripTimes.put(
					workplaceNumber,
					(quantity * workplace.getProductionTime())
							+ workplace.getSetupTime());

		} else {
			Integer currentValue = roundTripTimes.get(workplaceNumber);

			roundTripTimes.put(workplaceNumber,
					(quantity * workplace.getProductionTime()) + currentValue
							+ workplace.getSetupTime());
		}
		
		for (ProductionInput prodInput : workplace.getInputs()) {

			if (prodInput.getProducer() != null) {
				roundTripTimes = calculateRoundTripTime(prodInput.getProducer(), prodInput.getQuantity(), roundTripTimes);
			}
				
		}

		return null;
	}

	public Map<String, Integer> calculateSafetyStock(
			SafetyStock safetyStockInput) {

		userInput.setSafetyStock(safetyStockInput);

		Map<String, Integer> salesMap = new HashMap<String, Integer>();
		salesMap.put("P1", userInput.getSales().getChildrenSales()
				+ userInput.getDirectSales().getChildrenSales());
		salesMap.put("P2", userInput.getSales().getWomenSales()
				+ userInput.getDirectSales().getWomenSales());
		salesMap.put("P3", userInput.getSales().getMenSales()
				+ userInput.getDirectSales().getMenSales());

		Map<String, Integer> productions = new SafetyStock(0).getSafetyStocks();

		for (ProductionPlan plan : plans) {
			String planName = plan.getName().toUpperCase();

			productions = putWorkplaceToProductions(plan.getProducer(),
					productions, salesMap.get(planName));
		}

		this.productions = productions;

		return productions;

	}

	private Map<String, Integer> putWorkplaceToProductions(Workplace workplace,
			Map<String, Integer> productions, Integer quantity) {

		String itemName = workplace.getOutput().getType().toString()
				+ workplace.getOutput().getNumber().toString();

		int currentStock = workplace.getOutput().getStock();

		int waitingList = workplace.getWaitingList();

		int value = quantity + userInput.getSafetyStock().getStock(itemName)
				- currentStock - waitingList;

		int currentValue = productions.get(itemName);
		productions.put(itemName, currentValue + value);

		for (ProductionInput prodInput : workplace.getInputs()) {

			if (prodInput.getProducer() != null) {
				productions = putWorkplaceToProductions(
						prodInput.getProducer(), productions,
						prodInput.getQuantity() * value);

			}
		}

		return productions;
	}

	public void setForecast(Forecast forecast) {
		userInput.setForecast(forecast);
	}

	public void setSales(Sales sales, Sales directSales) {
		userInput.setSales(sales);
		userInput.setDirectSales(directSales);
	}

}
