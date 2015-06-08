package de.hska.centurion.domain.production.workplace;

import java.util.List;

import de.hska.centurion.domain.production.item.Item;

/**
 * Object describes a part of a workspace which produces a certain item
 * (multiple objects can have the same workplace number but produces different
 * items)
 * 
 * @author Matthias Schnell
 *
 */
public class Workplace {

	/*
	 * ======================== CONSTRUCTOR ========================
	 */

	/**
	 * Standard Constructor
	 * 
	 * @param number
	 *            Workspace number in production plan
	 * @param output
	 *            item which is produced
	 * @param inputs
	 *            list of items which are required for the production of the
	 *            output item
	 * @param openOrders
	 *            unfinished items on this workplace
	 * @param productionTime
	 *            which is required to build 1 item
	 * @param setupTime
	 *            which is required to prepare this workspace for the production
	 *            of this objects item
	 */
	public Workplace(String number, Item output, List<Input> inputs,
			Integer openOrders, Integer productionTime, Integer setupTime) {
		super();
		this.number = number;
		this.output = output;
		this.inputs = inputs;
		this.openOrders = openOrders;
		this.productionTime = productionTime;
		this.setupTime = setupTime;
	}

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	/**
	 * Object identification
	 */
	private int id;

	/**
	 * Workspace number in production plan
	 */
	private String number;

	/**
	 * item which is produced
	 */
	private Item output;

	/**
	 * list of items which are required for the production of the output item
	 */
	private List<Input> inputs;

	/**
	 * unfinished items on this workplace
	 */
	private Integer openOrders;

	/**
	 * time which is required to build 1 item
	 */
	private Integer productionTime;

	/**
	 * Time which is required to prepare this workspace for the production of
	 * this objects item
	 */
	private Integer setupTime;

	/*
	 * ======================== METHODS ========================
	 */

	/**
	 * Method to extend required input items
	 * 
	 * @param input
	 *            new required input
	 * @return updated List of input items
	 */
	public List<Input> addInput(Input input) {
		this.inputs.add(input);

		return this.inputs;
	}

	/*
	 * ======================== OVERRIDES ========================
	 */

	@Override
	public String toString() {
		return "Workplace [id=" + id + ", number=" + number + ", output="
				+ output + ", inputs=" + inputs + ", openOrders="
				+ openOrders + ", productionTime=" + productionTime
				+ ", setupTime=" + setupTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		result = prime * result
				+ ((productionTime == null) ? 0 : productionTime.hashCode());
		result = prime * result
				+ ((setupTime == null) ? 0 : setupTime.hashCode());
		result = prime * result
				+ ((openOrders == null) ? 0 : openOrders.hashCode());
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
		Workplace other = (Workplace) obj;
		if (id != other.id)
			return false;
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		if (productionTime == null) {
			if (other.productionTime != null)
				return false;
		} else if (!productionTime.equals(other.productionTime))
			return false;
		if (setupTime == null) {
			if (other.setupTime != null)
				return false;
		} else if (!setupTime.equals(other.setupTime))
			return false;
		if (openOrders == null) {
			if (other.openOrders != null)
				return false;
		} else if (!openOrders.equals(other.openOrders))
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Item getOutput() {
		return output;
	}

	public void setOutput(Item output) {
		this.output = output;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	public Integer getWaitingList() {
		return openOrders;
	}

	public void setWaitingList(Integer waitingList) {
		this.openOrders = waitingList;
	}

	public void setProductionTime(Integer productionTime) {
		this.productionTime = productionTime;
	}

	public void setSetupTime(Integer setupTime) {
		this.setupTime = setupTime;
	}

	public int getProductionTime() {
		return productionTime;
	}

	public void setProductionTime(int productionTime) {
		this.productionTime = productionTime;
	}

	public int getSetupTime() {
		return setupTime;
	}

	public void setSetupTime(int setupTime) {
		this.setupTime = setupTime;
	}

}
