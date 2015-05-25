package de.hska.centurion.domain.production.item;

import de.hska.centurion.domain.production.resources.ItemTypeEnum;

/**
 * Abstract parent class for all production items
 * 
 * @author Matthias Schnell
 *
 */
public abstract class Item {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Default Constructor
	 */
	public Item() {
		type = null;
		number = null;
		name = null;
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * Object identification
	 */
	protected int id;

	/**
	 * Specific Type of the item in production plan
	 */
	protected ItemTypeEnum type;

	/**
	 * Identification number of the item in production plan
	 */
	protected Integer number;

	/**
	 * Actual identifier of the item
	 */
	protected String name;

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "Item [id=" + id + ", type=" + type + ", number=" + number
				+ ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	/*
	 * ======================== GETS & SETS ========================
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemTypeEnum getType() {
		return type;
	}

	public void setType(ItemTypeEnum type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
