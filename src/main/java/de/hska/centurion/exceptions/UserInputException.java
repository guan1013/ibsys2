package de.hska.centurion.exceptions;

public class UserInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4356435036394928571L;

	private int actualValue;

	private int allowedValue;

	// tooBig = true => actualValue > allowedValue
	// tooBig = false => actualValue < allowedValue;
	private boolean tooBig = false;

	private String item;

	@Override
	public String getMessage() {

		if (tooBig) {
			return "Der Wert für " + item + " darf den Wert " + allowedValue
					+ " nicht überschreiten (Aktuell: " + actualValue + ").";
		}

		return "Der Wert für " + item + " darf den Wert " + allowedValue
				+ " nicht unterschreiten (Aktuell: " + actualValue + ").";
	}

	public int getActualValue() {
		return actualValue;
	}

	public void setActualValue(int actualValue) {
		this.actualValue = actualValue;
	}

	public int getAllowedValue() {
		return allowedValue;
	}

	public void setAllowedValue(int allowedValue) {
		this.allowedValue = allowedValue;
	}

	public boolean isTooBig() {
		return tooBig;
	}

	public void setTooBig(boolean tooBig) {
		this.tooBig = tooBig;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public UserInputException(int actualValue, int allowedValue,
			boolean tooBig, String item) {
		super();
		this.actualValue = actualValue;
		this.allowedValue = allowedValue;
		this.tooBig = tooBig;
		this.item = item;
	}

}
