package de.hska.centurion.domain.production;

import java.util.List;

import de.hska.centurion.domain.production.resources.ProductionPlanEnum;
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
	 * @param workplaces
	 *            list of all workplaces that participate to this production
	 *            plan
	 */
	public ProductionPlan(ProductionPlanEnum name, List<Workplace> workplaces) {
		super();
		this.name = name;
		this.workplaces = workplaces;
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
	private ProductionPlanEnum name;

	/**
	 * workplaces list of all workplaces that participate to this production
	 * plan
	 */
	private List<Workplace> workplaces;

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "ProductionPlan [id=" + id + ", name=" + name + ", workplaces="
				+ workplaces + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((workplaces == null) ? 0 : workplaces.hashCode());
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
		if (name != other.name)
			return false;
		if (workplaces == null) {
			if (other.workplaces != null)
				return false;
		} else if (!workplaces.equals(other.workplaces))
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

	public ProductionPlanEnum getName() {
		return name;
	}

	public void setName(ProductionPlanEnum name) {
		this.name = name;
	}

	public List<Workplace> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(List<Workplace> workplaces) {
		this.workplaces = workplaces;
	}

}
