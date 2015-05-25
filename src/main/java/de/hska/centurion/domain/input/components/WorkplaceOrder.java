package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkplaceOrder {

	private int id;

	private int period;

	private int order;

	private int batch;

	private int item;

	private int amount;

	private int timeNeed;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@XmlAttribute
	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
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
		return "WorkplaceOrder [id=" + id + ", period=" + period + ", order="
				+ order + ", batch=" + batch + ", item=" + item + ", amount="
				+ amount + ", timeNeed=" + timeNeed + "]";
	}
}
