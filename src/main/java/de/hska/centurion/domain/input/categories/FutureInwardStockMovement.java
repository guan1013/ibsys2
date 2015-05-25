package de.hska.centurion.domain.input.categories;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.Order;

@XmlRootElement
public class FutureInwardStockMovement {

	private List<Order> orders;

	@XmlElement(name = "order")
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "FutureInwardStockMovement [orders=" + orders + "]";
	}
	
}
