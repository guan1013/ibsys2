package de.hska.centurion.domain.output;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents the static qualityControl of a simulation-input.
 * 
 * @author Simon
 *
 */
@XmlType(propOrder={"type", "loseQuantity", "delay"})
public class QualityControl {

	/*
	 * ======================== ATTRIBUTES ========================
	 */

	private String type = "no";
	private int loseQuantity = 0;
	private int delay = 0;

	/*
	 * ======================== METHODS ========================
	 */
	@XmlAttribute()
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute(name = "losequantity")
	public int getLoseQuantity() {
		return loseQuantity;
	}

	public void setLoseQuantity(int loseQuantity) {
		this.loseQuantity = loseQuantity;
	}

	@XmlAttribute
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + delay;
		result = prime * result + loseQuantity;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		QualityControl other = (QualityControl) obj;
		if (delay != other.delay)
			return false;
		if (loseQuantity != other.loseQuantity)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QualityControl [type=" + type + ", loseQuantity="
				+ loseQuantity + ", delay=" + delay + "]";
	}

}
