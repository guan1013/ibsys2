package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.WorkplaceOrder;

@XmlRootElement
public class OrdersInWork {

	private List<WorkplaceOrder> workplaceOrders;

	public OrdersInWork() {
		this.workplaceOrders = new ArrayList<WorkplaceOrder>();
	}

	@Override
	public String toString() {
		return "OrdersInWork [workplaceOrders=" + workplaceOrders + "]";
	}

	@XmlElement(name = "workplace")
	public List<WorkplaceOrder> getWorkplaceOrders() {
		return workplaceOrders;
	}

	public void setWorkplaceOrders(List<WorkplaceOrder> workplaceOrders) {
		this.workplaceOrders = workplaceOrders;
	}
}
