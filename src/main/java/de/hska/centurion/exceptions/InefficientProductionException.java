package de.hska.centurion.exceptions;

public class InefficientProductionException extends Exception {

	String item;

	Double fixCostCoverage;

	public InefficientProductionException(String item, Double fixCostCoverage) {

		this.item = item;
		this.fixCostCoverage = fixCostCoverage;
	}

	public String getItem() {
		return item;
	}

	public Double getFixCostCoverage() {
		return fixCostCoverage;
	}

}
