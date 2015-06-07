package de.hska.centurion.domain.gui;

import de.hska.centurion.domain.output.Input;

public class UserInput {
	// Notwendige Inputs des Users.
	private Forecast forecast;
	private Sales sales;
	private Sales directSales;
	private SafetyStock safetyStock;

	// Das Output-Objekt, das während die GUI aufgebaut wird vom User angepasst
	// werden kann.
	private Input suggestedOutput;

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public Sales getDirectSales() {
		return directSales;
	}

	public void setDirectSales(Sales directSales) {
		this.directSales = directSales;
	}

	public SafetyStock getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(SafetyStock safetyStock) {
		this.safetyStock = safetyStock;
	}

	public Input getSuggestedOutput() {
		return suggestedOutput;
	}

	public void setSuggestedOutput(Input suggestedOutput) {
		this.suggestedOutput = suggestedOutput;
	}

}
