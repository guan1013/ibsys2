package de.hska.centurion.domain.production.workplace;

import de.hska.centurion.domain.production.item.Item;

/**
 * Describes an input type for workspaces
 * 
 * @author Matthias Schnell
 *
 */
public class Input {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param item
	 *            which is required by the workplace
	 * @param quantity
	 *            of required item
	 * @param producer
	 *            workplace for the requested item
	 */
	public Input(Item item, Integer quantity, Workplace producer) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.producer = producer;
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * Object identification
	 */
	private int id;

	/**
	 * item which is required by the workplace
	 */
	private Item item;

	/**
	 * quantity of required item
	 */
	private Integer quantity;

	/**
	 * Producer workplace for the requested item
	 */
	private Workplace producer;

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "Input [id=" + id + ", item=" + item + ", quantity=" + quantity
				+ ", producer=" + producer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((producer == null) ? 0 : producer.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
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
		Input other = (Input) obj;
		if (id != other.id)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Workplace getProducer() {
		return producer;
	}

	public void setProducer(Workplace producer) {
		this.producer = producer;
	}

}
