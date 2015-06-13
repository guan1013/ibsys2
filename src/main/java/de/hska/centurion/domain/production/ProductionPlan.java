package de.hska.centurion.domain.production;

import de.hska.centurion.domain.production.workplace.Workplace;

/**
 * Main Production Object which hold all information about a specific production
 * plan
 * 
 * @author Matthias Schnell
 *
 */
public class ProductionPlan {
	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param name
	 *            which describe a specific production plan
	 * @param producer
	 *            Workplace which produces the final product of this production plan
	 */
	public ProductionPlan(String name, Workplace producer) {
		super();
		this.name = name;
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
	 * name which describe a specific production plan
	 */
	private String name;

	/**
	 * Workplace which produces the final product of this production plan
	 */
	private Workplace producer;

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "ProductionPlan [id=" + id + ", name=" + name + ", producer="
				+ producer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((producer == null) ? 0 : producer.hashCode());
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
		ProductionPlan other = (ProductionPlan) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Workplace getProducer() {
		return producer;
	}

	public void setProducer(Workplace producer) {
		this.producer = producer;
	}

}
