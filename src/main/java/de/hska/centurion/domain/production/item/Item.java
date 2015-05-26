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
	 * Standard Constructor
	 * 
	 * @param type
	 *            of the item in production plan
	 * @param number
	 *            Identification number of the item in production plan
	 * @param name
	 *            Actual identifier of the item
	 * @param value
	 *            Monetary value of this item
	 * @param stock
	 *            Current amount in stock
	 */
	public Item(ItemTypeEnum type, Integer number, String name, Double value,
			Integer stock) {
		super();
		this.type = type;
		this.number = number;
		this.name = name;
		this.value = value;
		this.stock = stock;
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

	/**
	 * Monetary value of this item
	 */
	protected Double value;

	/**
	 * Current amount in stock
	 */
	protected Integer stock;

	/*
	 * ======================== OVERRIDES ========================
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", type=" + type + ", number=" + number
				+ ", name=" + name + ", value=" + value + ", stock=" + stock
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
