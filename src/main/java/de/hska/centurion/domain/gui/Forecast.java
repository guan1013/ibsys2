package de.hska.centurion.domain.gui;

import java.util.Arrays;

/**
 * This class represents the four period forecast for the simulation.
 * 
 * @author Simon
 *
 */
public class Forecast {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The forecasts are saved in a array of Sales-Objects.
	 * @see Sales
	 */
	private Sales[] forecasts = new Sales[4];
	
	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * This Constructor creates an Forecast-object through a Sales-Array.
	 * The length of the array should be four.
	 * @param forecasts
	 */
	public Forecast(Sales[] forecasts) {
		super();
		this.forecasts = forecasts;
	}
	
	/**
	 * This Constructor creates an Forecast-object trough four Sales-objects.
	 * @param p1
	 * 			the sales object for the first period.
	 * @param p2
	 * 			the sales object for the second period.
	 * @param p3
	 * 			the sales object for the third period.
	 * @param p4
	 * 			the sales object for the fourth period.
	 */
	public Forecast(Sales p1, Sales p2, Sales p3, Sales p4) {
		this.forecasts = new Sales[]{p1, p2, p3, p4};
	}
	
	/*
	 * ======================== METHODS ========================
	 */
	public Sales[] getForecasts() {
		return forecasts;
	}

	public void setForecasts(Sales[] forecasts) {
		this.forecasts = forecasts;
	}
	
	public Sales getFirstPeriod() {
		return forecasts[0];
	}
	
	public void setFirstPeriod(Sales forecast) {
		forecasts[0] = forecast;
	}
	
	public Sales getSecondPeriod() {
		return forecasts[1];
	}
	
	public void setSecondPeriod(Sales forecast) {
		forecasts[1] = forecast;
	}
	
	public Sales getThirdPeriod() {
		return forecasts[2];
	}
	
	public void setThirdPeriod(Sales forecast) {
		forecasts[2] = forecast;
	}
	
	public Sales getFourthPeriod() {
		return forecasts[3];
	}
	
	public void setFourthPeriod(Sales forecast) {
		forecasts[3] = forecast;
	}

	@Override
	public String toString() {
		return "Forecast [forecasts=" + Arrays.toString(forecasts) + "]";
	}
	
}
