package de.hska.centurion.domain.output;

/**
 * @author Simon
 *
 */
public class WorkingTime {
	private int station;
	private int shift;
	private int overtime;

	public WorkingTime(int station, int shift, int overtime) {
		super();
		this.station = station;
		this.shift = shift;
		this.overtime = overtime;
	}

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
