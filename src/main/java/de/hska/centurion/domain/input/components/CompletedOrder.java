package de.hska.centurion.domain.input.components;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompletedOrder {

	private int period;

	private int id;

	private int item;

	private int quantity;

	private String costStr;

	private String averageUnitCostStr;

	private List<Batch> batches;

	public CompletedOrder() {
		this.batches = new ArrayList<Batch>();
	}

	@XmlElement(name = "batch")
	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	@XmlAttribute
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@XmlAttribute(name = "cost")
	public String getCostStr() {
		return costStr;
	}

	public void setCostStr(String costStr) {
		this.costStr = costStr;
	}

	@XmlAttribute(name = "averageunitcost")
	public String getAverageUnitCostStr() {
		return averageUnitCostStr;
	}

	@Override
	public String toString() {
		return "CompletedOrder [period=" + period + ", id=" + id + ", item="
				+ item + ", quantity=" + quantity + ", costStr=" + costStr
				+ ", averageUnitCostStr=" + averageUnitCostStr + ", batches="
				+ batches + "]";
	}

	public void setAverageUnitCostStr(String averageUnitCostStr) {
		this.averageUnitCostStr = averageUnitCostStr;
	}

}
