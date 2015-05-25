package de.hska.centurion.domain.production.order;

import de.hska.centurion.domain.production.item.Item;
import de.hska.centurion.domain.production.workplace.Workplace;

/**
 * Describes and order for a workplace to produce a specific item
 * 
 * @author Matthias Schnell
 *
 */
public class ProductionOrder {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param item
	 *            which is requested to be produced
	 * @param quantity
	 *            in which the item is requested
	 * @param priority
	 *            describes the order of production
	 * @param receiver
	 *            workspace which requested this item
	 */
	public ProductionOrder(Item item, Integer quantity, Integer priority,
			Workplace receiver) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.priority = priority;
		this.receiver = receiver;
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * Object identification
	 */
	private int id;

	/**
	 * item which is requested to be produced
	 */
	private Item item;

	/**
	 * quantity in which the item is requested
	 */
	private Integer quantity;

	/**
	 * priority describes the order of production
	 */
	private Integer priority;

	/**
	 * Receiver workspace which requested this item
	 */
	private Workplace receiver;

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "ProductionOrder [id=" + id + ", item=" + item + ", quantity="
				+ quantity + ", priority=" + priority + ", receiver="
				+ receiver + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((receiver == null) ? 0 : receiver.hashCode());
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
		ProductionOrder other = (ProductionOrder) obj;
		if (id != other.id)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
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

	public Workplace getReciver() {
		return receiver;
	}

	public void setReciver(Workplace reciver) {
		this.receiver = reciver;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
