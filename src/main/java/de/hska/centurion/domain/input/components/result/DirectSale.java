package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DirectSale {

	private Statistic profit;

	private Statistic contractPenalty;

	@XmlElement
	public Statistic getProfit() {
		return profit;
	}

	public void setProfit(Statistic profit) {
		this.profit = profit;
	}

	@XmlElement(name = "contractpenalty")
	public Statistic getContractPenalty() {
		return contractPenalty;
	}

	public void setContractPenalty(Statistic contractPenalty) {
		this.contractPenalty = contractPenalty;
	}

	@Override
	public String toString() {
		return "DirectSale [profit=" + profit + ", contractPenalty="
				+ contractPenalty + "]";
	}
}
