package de.hska.centurion.domain.production.item;

import de.hska.centurion.domain.production.resources.ItemTypeEnum;

/**
 * Item Class for purchased items (K for Kaufteile)
 * 
 * @author Matthias Schnell
 *
 */
public class KItem extends Item {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param number
	 *            Identification number of item in production plan
	 * @param name
	 *            Actual identifier of the item
	 */
	public KItem(Integer number, String name) {
		this.type = ItemTypeEnum.K;
		this.number = number;
		this.name = name;
	}

	// @TODO Implement own functionality

}
