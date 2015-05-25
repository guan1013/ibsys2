package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MarketplaceSale {

	private Statistic profit;

	@XmlElement
	public Statistic getProfit() {
		return profit;
	}

	public void setProfit(Statistic profit) {
		this.profit = profit;
	}
}
