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
	 * ======================== CONSTRUCTOR ========================*/
	
	
	/**
	 * Standard Constructor
	 * @param number Identification number of item in production plan
	 * @param name Actual identifier of the item
	 */
	public EItem(Integer number, String name) {
		this.type = ItemTypeEnum.E;
		this.number = number;
		this.name = name;
	}
	
	//@TODO Implement own functionality


}
