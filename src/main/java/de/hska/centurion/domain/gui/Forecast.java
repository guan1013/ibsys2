package de.hska.centurion.domain.gui;

import java.util.Arrays;

public class Forecast {
	private Sales[] forecasts = new Sales[4];
	
	public Forecast(Sales[] forecasts) {
		super();
		this.forecasts = forecasts;
	}
	
	public Forecast(Sales p1, Sales p2, Sales p3, Sales p4) {
		this.forecasts = new Sales[]{p1, p2, p3, p4};
	}

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
