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
	 * @param revenue
	 *            price of one item
	 */
	public PItem(Integer number, String name, Double value, Integer stock,
			Double revenue) {
		super(ItemTypeEnum.P, number, name, value, stock);
		this.revenue = revenue;
		calcFixCoverage();
		
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * price of one item
	 */
	private Double revenue;

	private Double fixCostsCoverage;

	/*
	 * ======================== METHODS ========================
	 */
	private void calcFixCoverage() {
		this.fixCostsCoverage = this.revenue - this.value;
	}

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "PItem [id=" + id + ", type=" + type + ", number=" + number
				+ ", name=" + name + ", value=" + value + ", stock=" + stock
				+ ", revenue=" + revenue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((revenue == null) ? 0 : revenue.hashCode());
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
		PItem other = (PItem) obj;
		if (revenue == null) {
			if (other.revenue != null)
				return false;
		} else if (!revenue.equals(other.revenue))
			return false;
		return true;
	}

	/*
	 * ======================== GETS & SETS ========================
	 */

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getFixCostsCoverage() {
		return fixCostsCoverage;
	}

	public void setFixCostsCoverage(Double fixCostsCoverage) {
		this.fixCostsCoverage = fixCostsCoverage;
	}

}
