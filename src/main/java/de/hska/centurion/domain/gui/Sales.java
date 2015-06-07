package de.hska.centurion.domain.gui;

public class Sales {
	private int childrenSales;
	private int womenSales;
	private int menSales;

	public Sales(int childrenSales, int womenSales, int menSales) {
		super();
		this.childrenSales = childrenSales;
		this.womenSales = womenSales;
		this.menSales = menSales;
	}

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
