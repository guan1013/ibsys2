package de.hska.centurion.main.services.production;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hska.centurion.domain.production.ProductionPlan;
import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.resources.ItemTypeEnum;
import de.hska.centurion.domain.production.workplace.Input;
import de.hska.centurion.domain.production.workplace.Workplace;
import de.hska.centurion.main.util.ProductionPlanBuilder;

public class ProductionService {

	/**
	 * Standard Constructor
	 * 
	 * @throws IOException
	 *             throws exception if plan.properties not exists or isn't
	 *             reachable
	 */
	public ProductionService() throws IOException {
		planBuilder = new ProductionPlanBuilder();

		plans = planBuilder.createProductionPlans();

	}

	ProductionPlanBuilder planBuilder;
	List<ProductionPlan> plans;

	public void calculateProduction() {

		Map<String, Map<String, Integer>> rttSheet = new HashMap<String, Map<String, Integer>>();

		for (ProductionPlan plan : plans) {
			
			Map<String, Integer> roundTripTimes = new HashMap<String, Integer>();
			
			roundTripTimes = calcRoundTripTimes(plan);
			
			rttSheet.put(plan.getName(), roundTripTimes);
			
		}

	}
	
	private Map<String, Integer> calcRoundTripTimes(ProductionPlan plan){
		
		
		
		return null;
	}

}
