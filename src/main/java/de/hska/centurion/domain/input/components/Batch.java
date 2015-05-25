package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Batch {

	private int id;
	
	private int amount;
	
	private int cycleTime;
	
	private String costStr;

	@Override
	public String toString() {
		return "Batch [id=" + id + ", amount=" + amount + ", cycleTime="
				+ cycleTime + ", costStr=" + costStr + "]";
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@XmlAttribute
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@XmlAttribute(name="cycletime")
	public int getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(int cycleTime) {
		this.cycleTime = cycleTime;
	}

	@XmlAttribute(name="cost")
	public String getCostStr() {
		return costStr;
	}

	public void setCostStr(String costStr) {
		this.costStr = costStr;
	}
	
	
}
