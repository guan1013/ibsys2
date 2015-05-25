package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.Order;

@XmlRootElement
public class InwardStockMovement {

	private List<Order> orders;
	
	public InwardStockMovement() {
		this.orders = new ArrayList<Order>();
	}
	
	@XmlElement(name = "order")
	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "InwardStockMovement [orders=" + orders + "]";
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
