package de.hska.centurion.services.production;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import de.hska.centurion.domain.gui.SafetyStock;
import de.hska.centurion.domain.gui.Sales;
import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.workplace.Input;
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

	}

	/**
	 * List of all production plans
	 */
	private List<ProductionPlan> plans;

	/**
	 * results from output.xml
	 */
	private Results xmlResults;

	public void calculateProduction() {

	}

	public SafetyStock calculateSaftyStock(Sales sales, Sales directSales,
			SafetyStock safetyStockInput) {

		Map<String, Integer> salesMap = new HashMap<String, Integer>();
		salesMap.put("P1",
				sales.getChildrenSales() + directSales.getChildrenSales());
		salesMap.put("P2", sales.getWomenSales() + directSales.getWomenSales());
		salesMap.put("P3", sales.getMenSales() + directSales.getMenSales());

		SafetyStock safetyStockOutput = new SafetyStock(0);

		for (ProductionPlan plan : plans) {
			String planName = plan.getName().toUpperCase();

			safetyStockOutput = putWorkplaceToStock(plan.getProducer(),
					safetyStockInput, safetyStockOutput, salesMap.get(planName));
		}

		return safetyStockOutput;

	}
	

	private SafetyStock putWorkplaceToStock(Workplace workplace,
			SafetyStock input, SafetyStock output, Integer quantity) {

		String itemName = workplace.getOutput().getType().toString()
				+ workplace.getOutput().getNumber().toString();

		int currentStock = workplace.getOutput().getStock();

		int waitingList = workplace.getWaitingList();

		int value = quantity + input.getStock(itemName) - currentStock
				- waitingList;

		output.getSafetyStocks().put(itemName, value);

		for (Input itemInput : workplace.getInputs()) {

			if (itemInput.getProducer() != null) {
				output = putWorkplaceToStock(itemInput.getProducer(), input,
						output, itemInput.getQuantity() * value);

			}
		}

		return output;
	}
	
	

}
