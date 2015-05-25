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
	 *            Identification number of item in production plan
	 * @param name
	 *            Actual identifier of the item
	 */
	public PItem(Integer number, String name) {
		this.type = ItemTypeEnum.P;
		this.number = number;
		this.name = name;
	}

	// @TODO Implement own functionality

}
