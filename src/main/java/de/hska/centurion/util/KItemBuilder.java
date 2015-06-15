package de.hska.centurion.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.hska.centurion.domain.input.Results;
import de.hska.centurion.domain.production.item.KItem;
import de.hska.centurion.domain.purchase.Usage;

public class KItemBuilder {

	/*
	 * ======================== ATTRIBUTES ========================
	 */
	private InputStream is;

	private Properties props;

	private Results results;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */

	public KItemBuilder(Results results) throws IOException {
		is = getClass().getResourceAsStream(
				Constants.getProductionPlanProperties());
		this.results = results;
		props = new Properties();
		props.load(is);
	}

	/*
	 * ======================== METHODS ========================
	 */

	public List<KItem> createKItemList() {
		List<KItem> items = new ArrayList<KItem>();

		for (int i = 21; i < 60; i++) {
			String key = Constants.getItemInitKey() + ".K" + i;
			String type = props.getProperty(key + Constants.getTypeKey());

			if (type != null) {
				int number = Integer.parseInt(props.getProperty(key
						+ Constants.getNumberKey()));
				String name = props.getProperty(key + Constants.getNameKey());
				String valueStr = results.getWarehousestock()
						.getArticles().get(i - 1).getStockValueString();
				valueStr = valueStr.replace(',', '.');
				double value = Double.parseDouble(valueStr);
				int stock = results.getWarehousestock().getArticles()
						.get(i - 1).getAmount();
				int stack = Integer.parseInt(props.getProperty(key
						+ Constants.getStackKey()));
				double orderCosts = Double.parseDouble(props.getProperty(key
						+ Constants.getOrderKey()));
				double deliveryTime = Double.parseDouble(props.getProperty(key
						+ Constants.getTimeKey()));
				double diviation = Double.parseDouble(props.getProperty(key
						+ Constants.getDivKey()));
				String producer = props.getProperty(key
						+ Constants.getProducerKey());

				key = key + Constants.getUsageKey();
				int usageP1 = Integer.parseInt(props.getProperty(key
						+ Constants.getP1Key()));
				int usageP2 = Integer.parseInt(props.getProperty(key
						+ Constants.getP2Key()));
				int usageP3 = Integer.parseInt(props.getProperty(key
						+ Constants.getP3Key()));

				Usage usage = new Usage(usageP1, usageP2, usageP3);
				KItem item = new KItem(number, name, value, stock, stack,
						orderCosts, deliveryTime, diviation, producer);
				item.setUsage(usage);
				items.add(item);
			}
		}

		return items;
	}
}
