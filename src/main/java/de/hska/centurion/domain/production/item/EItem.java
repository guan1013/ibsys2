package de.hska.centurion.domain.production.item;

import de.hska.centurion.domain.production.resources.ItemTypeEnum;

/**
 * Item Class for self produced items (E for Eigenerzeugnisse)
 * 
 * @author Matthias Schnell
 *
 */
public class EItem extends Item {

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
	 * @param producer
	 *            Final Workplace which produce this item
	 */
	public EItem(Integer number, String name, Double value, Integer stock,
			String producer) {
		super(ItemTypeEnum.E, number, name, value, stock, producer);
	}

}
