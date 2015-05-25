package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.CycleOrder;

@XmlRootElement
public class CycleTimes {

	private int startedOrders;

	private int waitingOrders;

	private List<CycleOrder> orders;

	public CycleTimes() {
		orders = new ArrayList<CycleOrder>();
	}

	@XmlAttribute(name="startedorders")
	public int getStartedOrders() {
		return startedOrders;
	}

	public void setStartedOrders(int startedOrders) {
		this.startedOrders = startedOrders;
	}
	
	@XmlAttribute(name="waitingorders")
	public int getWaitingOrders() {
		return waitingOrders;
	}

	public void setWaitingOrders(int waitingOrders) {
		this.waitingOrders = waitingOrders;
	}

	@XmlElement(name="order")
	public List<CycleOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<CycleOrder> orders) {
		this.orders = orders;
	}
}
