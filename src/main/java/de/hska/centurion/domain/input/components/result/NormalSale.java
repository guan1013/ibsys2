package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NormalSale {

	private Statistic salesprice;

	private Statistic profit;

	private Statistic profitPerUnit;

	@XmlElement(name = "salesprice")
	public Statistic getSalesprice() {
		return salesprice;
	}

	public void setSalesprice(Statistic salesprice) {
		this.salesprice = salesprice;
	}

	@XmlElement
	public Statistic getProfit() {
		return profit;
	}

	public void setProfit(Statistic profit) {
		this.profit = profit;
	}

	@XmlElement(name = "profitperunit")
	public Statistic getProfitPerUnit() {
		return profitPerUnit;
	}

	public void setProfitPerUnit(Statistic profitPerUnit) {
		this.profitPerUnit = profitPerUnit;
	}

	@Override
	public String toString() {
		return "NormalSale [salesprice=" + salesprice + ", profit=" + profit
				+ ", profitPerUnit=" + profitPerUnit + "]";
	}

}
