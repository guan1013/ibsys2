package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WaitingList {

	private int period;
	
	private int order;
	
	private int firstBatch;
	
	private int lastBatch;
	
	private int item;
	
	private int amount;
	
	private int timeNeed;

	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@XmlAttribute
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@XmlAttribute(name = "firstbatch")
	public int getFirstBatch() {
		return firstBatch;
	}

	public void setFirstBatch(int firstBatch) {
		this.firstBatch = firstBatch;
	}

	@XmlAttribute(name = "lastbatch")
	public int getLastBatch() {
		return lastBatch;
	}

	public void setLastBatch(int lastBatch) {
		this.lastBatch = lastBatch;
	}
	
	@XmlAttribute
	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	@XmlAttribute
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@XmlAttribute(name = "timeneed")
	public int getTimeNeed() {
		return timeNeed;
	}

	public void setTimeNeed(int timeNeed) {
		this.timeNeed = timeNeed;
	}

	@Override
	public String toString() {
		return "WaitingList [period=" + period + ", order=" + order
				+ ", firstBatch=" + firstBatch + ", lastBatch=" + lastBatch
				+ ", item=" + item + ", amount=" + amount + ", timeNeed="
				+ timeNeed + "]";
	}
	
	
}
