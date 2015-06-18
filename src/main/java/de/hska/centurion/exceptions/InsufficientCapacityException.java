package de.hska.centurion.exceptions;

public class InsufficientCapacityException extends Exception {

	String workplace;

	Integer shifts;

	Double overtime;

	public InsufficientCapacityException(String workplace, Integer shifts,
			Double overtime) {

		this.workplace = workplace;
		this.shifts = shifts;
		this.overtime = overtime;

	}

	public String getWorkplace() {
		return workplace;
	}

	public Integer getShifts() {
		return shifts;
	}

	public Double getOvertime() {
		return overtime;
	}

}
