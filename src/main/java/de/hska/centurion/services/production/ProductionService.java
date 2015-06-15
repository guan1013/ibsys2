package de.hska.centurion.services.production;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hska.centurion.domain.gui.Forecast;
import de.hska.centurion.domain.gui.SafetyStock;
import de.hska.centurion.domain.gui.Sales;
import de.hska.centurion.domain.gui.UserInput;
import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.output.Production;
import de.hska.centurion.domain.output.WorkingTime;
import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.EItem;
import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.item.PItem;
import de.hska.centurion.domain.production.resources.ItemTypeEnum;
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

		// Get an Object of ProductionPlanBuilder
		ProductionPlanBuilder ppb = new ProductionPlanBuilder(results);

		// Create a new ProductionPlan
		this.plans = ppb.createProductionPlans();

		// Build a new UserInput Object
		this.userInput = new UserInput();

		// Define the decimal format for shift values
		this.shiftFormat = new DecimalFormat("##");
		this.shiftFormat.setRoundingMode(RoundingMode.DOWN);
		this.shiftFormat.setMinimumFractionDigits(0);
		this.shiftFormat.setMaximumFractionDigits(0);

		// Define the decimal format for overtime values
		this.overtimeFormat = new DecimalFormat("##");
		this.overtimeFormat.setRoundingMode(RoundingMode.UP);
		this.overtimeFormat.setMinimumFractionDigits(0);
		this.overtimeFormat.setMaximumFractionDigits(0);

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
	 * List of item which should be produced
	 */
	private Map<String, Integer> productionsList;

	/**
	 * Object which holds informations of the workplaces which produce items
	 */
	private Map<String, Workplace> factory;

	/**
	 * Decimal format for shift values
	 */
	private DecimalFormat shiftFormat;

	/**
	 * Decimal format for overtimeFormat;
	 */
	private DecimalFormat overtimeFormat;

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
	public Map<String, Integer> calculateProduction(SafetyStock safetyStockInput) {

		// Place safetystock in global UserInput object for further calculation
		userInput.setSafetyStock(safetyStockInput);

		// Create a new Factory Map
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

		// for each workplace which end-produces an item, produce items till you
		// raised the safety stock;
		for (Map.Entry<String, Workplace> workplace : factory.entrySet()) {
			String itemName = workplace.getValue().getOutput().getType()
					.toString()
					+ workplace.getValue().getOutput().getNumber().toString();

			// get safetyStock amount
			int planedAmount = userInput.getSafetyStock().getStock(itemName);

			// get amount of not used stock items
			int actualAmount = workplace.getValue().getOutput().getStock();

			// Check wether stock is under safetystock
			if (actualAmount < planedAmount) {

				// apply required amount to productions list
				int currentAmount = productions.get(itemName);
				productions.put(itemName, currentAmount + planedAmount
						- actualAmount);
			}
		}

		// Place calculated productions to global productions type
		this.productionsList = productions;

		return this.productionsList;

	}

	/**
	 * Calculate the total work time capacity which is required for the
	 * production
	 * 
	 * @return List of WorkingTime objects, which hold shift and overtime per
	 *         day of each workplace
	 */
	public List<WorkingTime> calculateCapacity() {

		// Create a new Empty Sheet of Workplaces and their roundtriptimes for
		// associated output items
		Map<String, Map<String, Integer>> roundTripTimes = new HashMap<String, Map<String, Integer>>();

		// For each production plan calculate the roundtriptimes
		for (ProductionPlan plan : plans) {
			PItem output = (PItem) plan.getProducer().getOutput();

			// Check if Fix Cost Coverage is over 0
			if (output.getFixCostsCoverage() > 0) {

				// TODO: Order isn't cost efficient exception

			}

			// Get the amount of items to produce
			Integer quantity = this.productionsList.get(plan.getName()
					.toUpperCase());

			// Calculate the RoundTripTimes for each item
			roundTripTimes = calculateRoundTripTime(plan.getProducer(),
					quantity, roundTripTimes);
		}

		// Create a new empty list of WorkingTime objects
		List<WorkingTime> capacities = new ArrayList<WorkingTime>();

		// For each calculated roundTripTime, calculate the needed capacity
		for (Map.Entry<String, Map<String, Integer>> roundTripTime : roundTripTimes
				.entrySet()) {

			// Catch possible parsing errors
			try {
				int workplace = Integer.parseInt(roundTripTime.getKey());

				// Calculate how many shifts will be needed
				Double workingTime = roundTripTime.getValue().get(ACCUMULATED)
						.doubleValue()
						/ SHIFT_TIME_MINS;
				int shift = 1;
				Double overtime = 0.0;

				// Check if there is more than one full shift is needed
				if (workingTime > 1) {

					// Cut the decimals off of the worktime
					shift = Integer.parseInt(shiftFormat.format(workingTime));

					// See how much overtime
					overtime = (workingTime - shift) * SHIFT_TIME_MINS;
					if (overtime > (SHIFT_TIME_MINS * 0.5)) {
						overtime = 0.0;
						shift += 1;
					}

					// Check wether if more than 3 shifts would be needed for
					// the production
					if (shift > 3) {
						// TODO: production is not possible
					}

				}

				// Calculate the overtime per day
				Double overtimePerDay = overtime / 5;

				// Create a new WorkingTime object with all calculated working
				// time informations and add it to the capacities list
				WorkingTime capacity = new WorkingTime(workplace, shift,
						Integer.parseInt(overtimeFormat.format(overtimePerDay)));

				capacities.add(capacity);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return capacities;
	}

	/**
	 * Creates a List of Productions
	 * 
	 * @return
	 */
	public List<Production> getProductionOrder() {

		List<Production> suggestedProduction = new ArrayList<Production>();

		for (Map.Entry<String, Integer> production : this.productionsList
				.entrySet()) {

			try {
				int item = Integer.parseInt(production.getKey().substring(1));
				int quantity = production.getValue();
				suggestedProduction.add(new Production(item, quantity));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return suggestedProduction;
	}

	/*
	 * ======================== PRIVATS ========================
	 */

	private Map<String, Integer> putWorkplaceToProductions(Workplace plan,
			Map<String, Integer> productions, Integer quantity) {

		// get name of current workplaces output item
		String itemName = plan.getOutput().getType().toString()
				+ plan.getOutput().getNumber().toString();

		int required = 0;
		if (plan.getOutput().getProducer().equalsIgnoreCase(plan.getNumber())) {
			Workplace workplace;

			String factoryKey = plan.getNumber() + itemName;
			if (factory.get(factoryKey) == null) {

				Item output;
				if (plan.getOutput().getType().toString()
						.equalsIgnoreCase(ItemTypeEnum.E.toString())) {
					EItem eOutput = (EItem) plan.getOutput();
					output = new EItem(eOutput.getNumber(), eOutput.getName(),
							eOutput.getValue(), eOutput.getStock(),
							eOutput.getProducer());
				} else {

					PItem pOutput = (PItem) plan.getOutput();
					output = new PItem(pOutput.getNumber(), pOutput.getName(),
							pOutput.getValue(), pOutput.getStock(),
							pOutput.getRevenue(), pOutput.getProducer());
				}
				workplace = new Workplace(plan.getNumber(), output, null,
						plan.getOpenOrders(), plan.getProductionTime(),
						plan.getSetupTime());
				factory.put(factoryKey, workplace);
			}
			workplace = factory.get(factoryKey);

			// get current Stock of workplaces output item
			int currentStock = workplace.getOutput().getStock();

			// get current unfinished items on this workplace
			int waitingList = getWaitingList(plan, itemName, 0);

			required = quantity;

			// apply waiting list items to total required amount
			if ((required - waitingList) < 0) {
				factory.get(factoryKey).setOpenOrders(waitingList - required);
				required = 0;
			} else {
				required -= waitingList;
				factory.get(factoryKey).setOpenOrders(0);
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
		for (ProductionInput input : plan.getInputs()) {
			if (input.getProducer() != null) {
				productions = putWorkplaceToProductions(input.getProducer(),
						productions, input.getQuantity() * quantity);

			}

		}

		return productions;
	}

	private int getWaitingList(Workplace workplace, String item, int waitingList) {
		waitingList += workplace.getOpenOrders();

		for (ProductionInput input : workplace.getInputs()) {
			if (input.getItem().equalsIgnoreCase(item)) {
				waitingList += getWaitingList(input.getProducer(), item,
						waitingList);
			}

		}

		return waitingList;
	}

	private Map<String, Map<String, Integer>> calculateRoundTripTime(
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

		int productionTime = workplace.getProductionTime();

		int setupTime = workplace.getSetupTime();
		if (roundTripTimes.get(workplaceNumber) == null) {

			// get current unfinished items on this workplace
			int waitingList = workplace.getOpenOrders();

			// calculate the actual quantity which should be produced
			value = this.productionsList.get(itemName) + waitingList;

			// Calculate the roundtrip time on this workplace for all produced
			// items
			Integer roundTripTime = (value * productionTime) + setupTime;

			// Add item to rrt list
			outputRRT.put(itemName, roundTripTime);

		} else {

			// Check wether current workplaces output is already in the list
			outputRRT = roundTripTimes.get(workplaceNumber);

			Integer roundTripTime = 0;

			if (outputRRT.get(itemName) == null) {

				// get current unfinished items on this workplace
				int waitingList = workplace.getOpenOrders();

				// calculate the actual quantity which should be produced
				value = this.productionsList.get(itemName) + waitingList;

				// Calculate the roundtrip time on this workplace for all
				// produced items
				roundTripTime = (value * productionTime) + setupTime;

				// Add item to rrt list
				outputRRT.put(itemName, roundTripTime);

			}

		}

		// Calculate the accumulated roundtriptime for each output item of this
		// workplace
		Integer accumulatedRTT = 0;
		for (Map.Entry<String, Integer> set : outputRRT.entrySet()) {
			if (!set.getKey().equalsIgnoreCase(ACCUMULATED)) {
				accumulatedRTT += set.getValue();
			}
		}

		// Add accumulated value to rrt list
		outputRRT.put(ACCUMULATED, accumulatedRTT);

		// add workplace to rrt sheet
		roundTripTimes.put(workplaceNumber, outputRRT);

		// Recursively do this for each input item of this workplace
		for (ProductionInput prodInput : workplace.getInputs()) {

			// Check wether Input is a E or P Item
			if (prodInput.getProducer() != null) {
				roundTripTimes = calculateRoundTripTime(
						prodInput.getProducer(), prodInput.getQuantity(),
						roundTripTimes);
			}

		}

		return roundTripTimes;
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
