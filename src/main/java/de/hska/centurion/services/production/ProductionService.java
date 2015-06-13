package de.hska.centurion.services.production;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hska.centurion.domain.gui.Forecast;
import de.hska.centurion.domain.gui.SafetyStock;
import de.hska.centurion.domain.gui.Sales;
import de.hska.centurion.domain.gui.UserInput;
import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.output.Input;
import de.hska.centurion.domain.output.Production;
import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.PItem;
import de.hska.centurion.domain.production.workplace.ProductionInput;
import de.hska.centurion.domain.production.workplace.Workplace;
import de.hska.centurion.util.ProductionPlanBuilder;

/**
 * Service Object which provides business logic functionality
 * 
 * @author Matthias Schnell
 *
 */
public class ProductionService {

	/*
	 * ======================== CONSTANTS ========================
	 */

	private String ACCUMULATED = "accumulated";

	private Integer SHIFT_TIME_MINS = 2400;

	private Integer MAX_CAPACITY = (3 * SHIFT_TIME_MINS)
			+ (3 * SHIFT_TIME_MINS / 2);

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param xmlPath
	 *            path to output.xml
	 * 
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 */
	public ProductionService(Results results) throws IOException {

		ProductionPlanBuilder ppb = new ProductionPlanBuilder(results);

		this.plans = ppb.createProductionPlans();

		this.userInput = new UserInput();
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * List of all production plans
	 */
	private List<ProductionPlan> plans;

	/**
	 * Object which holds user input information
	 */
	private UserInput userInput;

	/**
	 * list of item which should be produced
	 */
	private Map<String, Integer> productions;

	/**
	 * Object which holds informations for the application output "input.xml"
	 */
	private Input suggestedOutput;

	/**
	 * 
	 */
	private Map<String, Workplace> factory;

	/*
	 * ======================== METHODS ========================
	 */

	/**
	 * Calculate produced items with given safetystock
	 * 
	 * @param safetyStockInput
	 *            given safetystock for calculation
	 * @return items which will be produced under consideration of safetystock,
	 *         sellwish, current stock and unfinished productions
	 */
	public Map<String, Integer> calculateSafetyStock(
			SafetyStock safetyStockInput) {

		// Place safetystock in global UserInput object for further calculation
		userInput.setSafetyStock(safetyStockInput);

		factory = new HashMap<String, Workplace>();

		// Create a List of all planed safes
		Map<String, Integer> salesMap = new HashMap<String, Integer>();
		salesMap.put("P1", userInput.getSales().getChildrenSales()
				+ userInput.getDirectSales().getChildrenSales());
		salesMap.put("P2", userInput.getSales().getWomenSales()
				+ userInput.getDirectSales().getWomenSales());
		salesMap.put("P3", userInput.getSales().getMenSales()
				+ userInput.getDirectSales().getMenSales());

		// Create a default empty Map of possible items to produce
		Map<String, Integer> productions = new SafetyStock(0).getSafetyStocks();

		// Calculate the produced items
		for (ProductionPlan plan : plans) {
			String planName = plan.getName().toUpperCase();

			productions = putWorkplaceToProductions(plan.getProducer(),
					productions, salesMap.get(planName));
		}

		for (Map.Entry<String, Integer> p : productions.entrySet()) {

			System.out.println("#### " + p.getKey() + " = " + p.getValue());
		}

		this.productions = productions;

		return productions;

	}

	/**
	 * Calculate the optimal order in which items should be produced
	 * 
	 * @return
	 */
	public List<Production> calculateProductionOrder() {

		// Create a new Empty Sheet of Workplaces and their roundtriptimes for
		// associated output items
		Map<String, Map<String, Integer>> roundTripTimes = new HashMap<String, Map<String, Integer>>();

		// For each production plan calculate the roundtriptiems
		for (ProductionPlan plan : plans) {
			PItem output = (PItem) plan.getProducer().getOutput();

			// Check if Fix Cost Coverage is over 0
			if (output.getFixCostsCoverage() > 0) {

				// TODO: Order isn't cost efficient exception

			}

			// Get the amount of items to produce
			Integer quantity = productions.get(plan.getName().toUpperCase());

			// Calculate the RoundTripTimes for each item
			roundTripTimes = calcRoundTripTime(plan.getProducer(), quantity,
					roundTripTimes);
		}

		// Figure uut the bottle neck workplace
		String bottleNeck = calcBottleNeck(roundTripTimes);

		// For each production plan
		for (ProductionPlan plan : plans) {

			PItem output = (PItem) plan.getProducer().getOutput();

			Double productionOrder = output.getFixCostsCoverage()
					/ roundTripTimes.get(bottleNeck).get(ACCUMULATED)
							.doubleValue();

		}

		return null;
	}

	/*
	 * ======================== PRIVATS ========================
	 */

	private Map<String, Integer> putWorkplaceToProductions(Workplace workplace,
			Map<String, Integer> productions, Integer quantity) {
		// get name of current workplaces output item
		String itemName = workplace.getOutput().getType().toString()
				+ workplace.getOutput().getNumber().toString();

		String factoryKey = workplace.getNumber() + itemName;

		int required = 0;
		if (factory.get(factoryKey) == null) {
			factory.put(factoryKey, workplace);
			required = quantity + userInput.getSafetyStock().getStock(itemName);

		} else {
			required = quantity;
		}

		workplace = factory.get(factoryKey);

		if (workplace.getOutput().getProducer()
				.equalsIgnoreCase(workplace.getNumber())) {
			// get current Stock of workplaces output item
			int currentStock = workplace.getOutput().getStock();

			// get current unfinished items on this workplace
			int waitingList = workplace.getWaitingList();

			// apply waiting list items to total required amount
			if ((required - waitingList) < 0) {
				factory.get(factoryKey).setWaitingList(waitingList - required);
				required = 0;
			} else {
				required -= waitingList;
				factory.get(factoryKey).setWaitingList(0);
			}

			// apply stock items to total required amount
			if ((required - currentStock) < 0) {
				factory.get(factoryKey).getOutput()
						.setStock(currentStock - required);
				required = 0;
			} else {
				required -= currentStock;
				factory.get(factoryKey).getOutput().setStock(0);
			}

			// apply required amount to productions list
			int currentAmount = productions.get(itemName);
			productions.put(itemName, currentAmount + required);

		}

		// Recursively do this for each input item of this workplace
		for (ProductionInput prodInput : workplace.getInputs()) {
			if (prodInput.getProducer() != null) {
				productions = putWorkplaceToProductions(
						prodInput.getProducer(), productions,
						prodInput.getQuantity() * required);

			}

		}

		return productions;
	}

	private Map<String, Map<String, Integer>> calcRoundTripTime(
			Workplace workplace, Integer quantity,
			Map<String, Map<String, Integer>> roundTripTimes) {

		// get name of current workplaces output item
		String itemName = workplace.getOutput().getType().toString()
				+ workplace.getOutput().getNumber().toString();

		// get current Workplaces number
		String workplaceNumber = workplace.getNumber();

		// Create new Empty list of possible round trip times of this workplace
		Map<String, Integer> outputRRT = new HashMap<String, Integer>();

		// Check wether this workplace already has a List of rrts
		int value = 0;
		if (roundTripTimes.get(workplaceNumber) == null) {

			// get current Stock of workplaces output item
			int currentStock = workplace.getOutput().getStock();

			// get current unfinished items on this workplace
			int waitingList = workplace.getWaitingList();

			// calculate the actual quantity which should be produced
			value = quantity + userInput.getSafetyStock().getStock(itemName)
					- currentStock - waitingList;

			// Calculate the roundtrip time on this workplace for all produced
			// items
			Integer roundTripTime = (value * workplace.getProductionTime())
					+ workplace.getSetupTime();

			// Add item to rrt list
			outputRRT.put(itemName, roundTripTime);

		} else {

			// Check wether current workplaces output is already in the list
			outputRRT = roundTripTimes.get(workplaceNumber);

			Integer roundTripTime = 0;

			if (outputRRT.get(itemName) == null) {

				// get current Stock of workplaces output item
				int currentStock = workplace.getOutput().getStock();

				// get current unfinished items on this workplace
				int waitingList = workplace.getWaitingList();

				// calculate the actual quantity which should be produced
				value = quantity
						+ userInput.getSafetyStock().getStock(itemName)
						- currentStock - waitingList;

				// Calculate the roundtrip time on this workplace for all
				// produced items
				roundTripTime = (value * workplace.getProductionTime())
						+ workplace.getSetupTime();

			} else {

				// Get already added value of current workplaces output
				Integer currentValue = outputRRT.get(itemName);

				// Calculate the roundtrip time on this workplace for all
				// produced items
				roundTripTime = (quantity * workplace.getProductionTime())
						+ currentValue + workplace.getSetupTime();
			}

			// Add item to rrt list
			outputRRT.put(itemName, roundTripTime);

		}

		// Calculate the accumulated roundtriptime for each output item of this
		// workplace
		Integer accumulatedRTT = 0;
		for (Map.Entry<String, Integer> set : outputRRT.entrySet()) {
			accumulatedRTT += set.getValue();
		}

		// Add accumulated value to rrt list
		outputRRT.put(ACCUMULATED, accumulatedRTT);

		// add workplace to rrt sheet
		roundTripTimes.put(workplaceNumber, outputRRT);

		// Recursively do this for each input item of this workplace
		for (ProductionInput prodInput : workplace.getInputs()) {

			// Check wether Input is a E or P Item
			if (prodInput.getProducer() != null) {
				roundTripTimes = calcRoundTripTime(prodInput.getProducer(),
						prodInput.getQuantity(), roundTripTimes);
			}

		}

		return roundTripTimes;
	}

	private String calcBottleNeck(
			Map<String, Map<String, Integer>> roundTripTimes) {

		String bottleNeckWorkplace = null;

		Integer bottleNeckRTT = 0;

		// Determine the Workplace with the lowest remaining capacity
		for (Map.Entry<String, Map<String, Integer>> rtt : roundTripTimes
				.entrySet()) {

			// Dertermine remaining capacity
			Integer capacity = MAX_CAPACITY - rtt.getValue().get(ACCUMULATED);

			// Check wether there is no remaining capacity
			if (capacity < 0) {
				// TODO: Order isn't possible within periode
			}

			// Check wether current workplace has the overall lowest remaining
			// capacity
			if (capacity < bottleNeckRTT) {
				bottleNeckRTT = capacity;

				// Replace latest workplace with current one
				bottleNeckWorkplace = rtt.getKey();
			}

		}
		return bottleNeckWorkplace;

	}

	/*
	 * ======================== GETS & SETS ========================
	 */

	public void setForecast(Forecast forecast) {
		userInput.setForecast(forecast);
	}

	public void setSales(Sales sales, Sales directSales) {
		userInput.setSales(sales);
		userInput.setDirectSales(directSales);
	}

}
