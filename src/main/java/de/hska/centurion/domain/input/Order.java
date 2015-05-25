package de.hska.centurion.domain.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

	private int orderPeriod;

	@XmlAttribute(name = "orderperiod")
	public int getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(int orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	@Override
	public String toString() {
		return "Order [orderPeriod=" + orderPeriod + "]";
	}
}
