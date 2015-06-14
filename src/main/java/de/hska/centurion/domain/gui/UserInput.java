package de.hska.centurion.domain.gui;

import javax.validation.Valid;

import de.hska.centurion.domain.output.Input;

/**
 * This class contains the inputs of the user. The user delivers the input
 * through the GUI.
 * 
 * @author Simon
 *
 */
public class UserInput {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	// Notwendige Inputs des Users.
	/**
	 * The Forecast contains the next four periods.
	 */
	@Valid
	private Forecast forecast;

	/**
	 * The Sales-object contains the sales of the three different bicycle-types
	 * for the simulation period.
	 */
	private Sales sales;

	/**
	 * The DirectSales-Object contains the direct sales of the three different
	 * bicycle-types for the simulation period-
	 */
	private Sales directSales;

	/**
	 * The SafetyStock contains the wished number of articles at the end of the
	 * simulation period.
	 */
	private SafetyStock safetyStock;

	// Das Output-Objekt, das w�hrend die GUI aufgebaut wird vom User angepasst
	// werden kann.
	/**
	 * The Input-Object contains the suggested values for the scsim-input. The
	 * inputs will be calculated by the program and can be changed by the user.
	 */
	private Input suggestedOutput;

	/*
	 * ======================== Methods ========================
	 */
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
