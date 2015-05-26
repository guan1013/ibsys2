package de.hska.centurion.domain.production.item;

import de.hska.centurion.domain.production.resources.ItemTypeEnum;

/**
 * Item Class for finished items (P for Produkt)
 * 
 * @author Matthias Schnell
 *
 */
public class PItem extends Item {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param number
	 *            Identification number of the item in production plan
	 * @param name
	 *            Actual identifier of the item
	 * @param value
	 *            Monetary value of this item
	 * @param stock
	 *            Current amount in stock
	 */
	public PItem(Integer number, String name, Double value, Integer stock) {
		super(ItemTypeEnum.P, number, name, value, stock);
	}

}
