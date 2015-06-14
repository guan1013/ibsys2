package de.hska.centurion.domain.gui;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * This class contains the salees of children bikes, women bikes and men bikes.
 * 
 * @author Simon
 *
 */
public class Sales {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The planned number of children bike sales.
	 */
	@Min(0)
	@Max(500)
	private int childrenSales;

	/**
	 * The planned number of women bike sales.
	 */
	@Min(0)
	@Max(500)
	private int womenSales;

	/**
	 * The planned number of men bike sales.
	 */
	@Min(0)
	@Max(500)
	private int menSales;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * This Constructors creates an Sales-object through the number of sales for
	 * each bike.
	 * 
	 * @param childrenSales
	 *            The number of children bike sales.
	 * @param womenSales
	 *            The number of women bike sales.
	 * @param menSales
	 *            The number of men bike sales.
	 */
	public Sales(int childrenSales, int womenSales, int menSales) {
		super();
		this.childrenSales = childrenSales;
		this.womenSales = womenSales;
		this.menSales = menSales;
	}

	/*
	 * ======================== METHODS ========================
	 */
	public int getChildrenSales() {
		return childrenSales;
	}

	public void setChildrenSales(int childrenSales) {
		this.childrenSales = childrenSales;
	}

	public int getWomenSales() {
		return womenSales;
	}

	public void setWomenSales(int womenSales) {
		this.womenSales = womenSales;
	}

	public int getMenSales() {
		return menSales;
	}

	public void setMenSales(int menSales) {
		this.menSales = menSales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + childrenSales;
		result = prime * result + menSales;
		result = prime * result + womenSales;
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
		Sales other = (Sales) obj;
		if (childrenSales != other.childrenSales)
			return false;
		if (menSales != other.menSales)
			return false;
		if (womenSales != other.womenSales)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sales [childrenSales=" + childrenSales + ", womenSales="
				+ womenSales + ", menSales=" + menSales + "]";
	}
}
