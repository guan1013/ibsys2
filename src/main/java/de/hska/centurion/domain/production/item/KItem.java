package de.hska.centurion.domain.production.item;

import de.hska.centurion.domain.production.resources.ItemTypeEnum;
import de.hska.centurion.domain.purchase.Usage;

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
	 *            Identification number of the item in production plan
	 * @param name
	 *            Actual identifier of the item
	 * @param value
	 *            Monetary value of this item
	 * @param stock
	 *            Current amount in stock
	 * @param stack
	 *            quantity per stack
	 * @param orderCosts
	 *            costs per order
	 * @param deliveryTime
	 *            average time (in Periods) for a delivery to get shipped
	 * @param diviation
	 *            to deliveryTime
	 * @param producer
	 *            Final Workplace which produce this item
	 */
	public KItem(Integer number, String name, Double value, Integer stock,
			Integer stack, Double orderCosts, Double deliveryTime,
			Double diviation, String producer) {
		super(ItemTypeEnum.K, number, name, value, stock, producer);
		this.stack = stack;
		this.orderCosts = orderCosts;
		this.deliveryTime = deliveryTime;
		this.diviation = diviation;
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * quantity per stack
	 */
	private Integer stack;

	/**
	 * costs per order
	 */
	private Double orderCosts;

	/**
	 * average time (in Periods) for a delivery to get shipped
	 */
	private Double deliveryTime;

	/**
	 * to deliveryTime
	 */
	private Double diviation;

	/**
	 * how often is the item used in P1, P2, P3
	 */
	private Usage usage;
	
	@Override
	public String toString() {
		return "KItem [stack=" + stack + ", orderCosts=" + orderCosts
				+ ", deliveryTime=" + deliveryTime + ", diviation=" + diviation
				+ ", usage=" + usage + ", id=" + id + ", type=" + type
				+ ", number=" + number + ", name=" + name + ", value=" + value
				+ ", stock=" + stock + ", producer=" + producer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result
				+ ((diviation == null) ? 0 : diviation.hashCode());
		result = prime * result
				+ ((orderCosts == null) ? 0 : orderCosts.hashCode());
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KItem other = (KItem) obj;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (diviation == null) {
			if (other.diviation != null)
				return false;
		} else if (!diviation.equals(other.diviation))
			return false;
		if (orderCosts == null) {
			if (other.orderCosts != null)
				return false;
		} else if (!orderCosts.equals(other.orderCosts))
			return false;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		return true;
	}

	/*
	 * ======================== GETS & SETS ========================
	 */

	public Integer getstack() {
		return stack;
	}

	public void setstack(Integer stack) {
		this.stack = stack;
	}

	public Double getOrderCosts() {
		return orderCosts;
	}

	public void setOrderCosts(Double orderCosts) {
		this.orderCosts = orderCosts;
	}

	public Double getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Double deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Double getDiviation() {
		return diviation;
	}

	public void setDiviation(Double diviation) {
		this.diviation = diviation;
	}
	
	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

}
