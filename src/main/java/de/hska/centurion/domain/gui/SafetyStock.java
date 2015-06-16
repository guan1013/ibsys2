package de.hska.centurion.domain.gui;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the safety stock of each article at the end of the
 * simulated period.
 * 
 * @author Simon
 *
 */
public class SafetyStock {
	// Schl�ssel Exx oder Px, z. B. E26 setzt dann den Sicherheitsbestand.
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The Map contains String-keys, with the identifiers of the items, and
	 * Integer-values with the wished amount of this item at the end of the
	 * period. Example: The key for the item E26 should be "E26". The key for
	 * the item P1 should be "P1".
	 */
	private Map<String, Integer> safetyStocks;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * This Constructor creates a standard stock for every item.
	 * 
	 * @param standardStock
	 */
	public SafetyStock(int standardStock) {
		safetyStocks = new LinkedHashMap<String, Integer>();
		safetyStocks.put("E49", standardStock);
		safetyStocks.put("E54", standardStock);
		safetyStocks.put("E29", standardStock);
		safetyStocks.put("E50", standardStock);
		safetyStocks.put("E55", standardStock);
		safetyStocks.put("E30", standardStock);
		safetyStocks.put("E51", standardStock);
		safetyStocks.put("E56", standardStock);
		safetyStocks.put("E31", standardStock);
		safetyStocks.put("E17", 3 * standardStock);
		safetyStocks.put("E10", standardStock);
		safetyStocks.put("E11", standardStock);
		safetyStocks.put("E12", standardStock);
		safetyStocks.put("E13", standardStock);
		safetyStocks.put("E14", standardStock);
		safetyStocks.put("E15", standardStock);
		safetyStocks.put("E16", 3 * standardStock);
		safetyStocks.put("E18", standardStock);
		safetyStocks.put("E19", standardStock);
		safetyStocks.put("E20", standardStock);
		safetyStocks.put("E26", 3 * standardStock);
		safetyStocks.put("E7", standardStock);
		safetyStocks.put("E4", standardStock);
		safetyStocks.put("E8", standardStock);
		safetyStocks.put("E5", standardStock);
		safetyStocks.put("E6", standardStock);
		safetyStocks.put("E9", standardStock);
		safetyStocks.put("P1", standardStock);
		safetyStocks.put("P2", standardStock);
		safetyStocks.put("P3", standardStock);

	}

	/*
	 * ======================== METHODS ========================
	 */

	public Map<String, Integer> getSafetyStocks() {
		return safetyStocks;
	}

	public void setSafetyStocks(Map<String, Integer> safetyStocks) {
		this.safetyStocks = safetyStocks;
	}

	public void changeStock(String item, int stock) {
		safetyStocks.put(item, stock);
	}

	public int getStock(String item) {
		return safetyStocks.get(item).intValue();
	}

}
