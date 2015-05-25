package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.CompletedOrder;

@XmlRootElement
public class CompletedOrders {

	private List<CompletedOrder> completedOrders;

	public CompletedOrders() {
		this.completedOrders = new ArrayList<CompletedOrder>();
	}

	@XmlElement(name="order")
	public List<CompletedOrder> getCompletedOrders() {
		return completedOrders;
	}

	public void setCompletedOrders(List<CompletedOrder> completedOrders) {
		this.completedOrders = completedOrders;
	}
}
