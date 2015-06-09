package de.hska.centurion.domain.output;

/**
 * This class represents a time configuration for one workspace.
 * 
 * @author Simon
 *
 */
public class WorkingTime {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The name of the workspace, between 1 and 15 (except 5).
	 */
	private int station;
	/**
	 * The number of shifts for this workspace, between 1 and 3.
	 */
	private int shift;
	/**
	 * The Overtime in Minutes per working day, maximal 720(?).
	 */
	private int overtime;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * The constructor to create a working time configuration.
	 * @param station
	 * 				The name of the workspace, between 1 and 15 (except 5).
	 * @param shift
	 * 				The number of shifts for this workspace, between 1 and 3.
	 * @param overtime
 	 */
	public WorkingTime(int station, int shift, int overtime) {
		super();
		this.station = station;
		this.shift = shift;
		this.overtime = overtime;
	}

	/*
	 * ======================== METHODS ========================
	 */
	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public int getOvertime() {
		return overtime;
	}

	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + overtime;
		result = prime * result + shift;
		result = prime * result + station;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkingTime other = (WorkingTime) obj;
		if (overtime != other.overtime)
			return false;
		if (shift != other.shift)
			return false;
		if (station != other.station)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WorkingTime [station=" + station + ", shift=" + shift
				+ ", overtime=" + overtime + "]";
	}

}
