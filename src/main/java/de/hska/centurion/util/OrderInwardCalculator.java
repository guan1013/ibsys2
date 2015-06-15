package de.hska.centurion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.input.components.Order;

public class OrderInwardCalculator {
	private InputStream is;
	private Properties props;

	public OrderInwardCalculator() throws IOException {
		is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());
		props = new Properties();
		props.load(is);
	}

	public Results calculateOutstandingOrders(Results results) {
		if (results.getPeriod() == 1) {
			return null;
		}
		for (Order order : results.getFutureInwardStockMovement().getOrders()) {
			String key = Constants.getItemInitKey() + ".K" + order.getArticle();
			float time = Float.parseFloat(props.getProperty(key
					+ Constants.getTimeKey()));
			float dev = Float.parseFloat(props.getProperty(key
					+ Constants.getDivKey()));

			float inwardDate = Math
					.round((order.getOrderPeriod() + time + 0.3) * 100) / 100.0f;
			float inwardDateMax = Math.round((order.getOrderPeriod() + time
					+ 0.3 + dev) * 100) / 100.0f;

			int inwardPeriod = (int) inwardDate;
			if (inwardDate - inwardPeriod == 0) {
				inwardPeriod--;
			}
			int inwardPeriodMax = (int) inwardDateMax;
			if (inwardDateMax - inwardPeriodMax == 0) {
				inwardPeriodMax--;
			}

			int inwardDay = (int) Math.ceil((inwardDate - inwardPeriod) * 5.0f);
			int inwardDayMax = (int) Math
					.ceil((inwardDateMax - inwardPeriodMax) * 5.0f);

			order.setInwardStockMovementAvg(inwardPeriod + "-" + inwardDay
					+ "-0-0");
			order.setInwardStockMovementMax(inwardPeriodMax + "-" + inwardDayMax + "-0-0");
		}
		return results;
	}
}
