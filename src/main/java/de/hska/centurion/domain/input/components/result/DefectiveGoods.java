package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DefectiveGoods {

	private Statistic quantity;
	
	private Statistic costs;

	@XmlElement
	public Statistic getQuantity() {
		return quantity;
	}

	public void setQuantity(Statistic quantity) {
		this.quantity = quantity;
	}

	@XmlElement
	public Statistic getCosts() {
		return costs;
	}

	public void setCosts(Statistic costs) {
		this.costs = costs;
	}

	@Override
	public String toString() {
		return "DefectiveGoods [quantity=" + quantity + ", costs=" + costs
				+ "]";
	}
	
	
}
