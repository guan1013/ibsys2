package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Statistic {

	private String current;

	private String all;

	private String averageStr;

	@XmlAttribute
	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	@XmlAttribute
	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	@XmlAttribute(name = "average")
	public String getAverageStr() {
		return averageStr;
	}

	public void setAverageStr(String averageStr) {
		this.averageStr = averageStr;
	}

	@Override
	public String toString() {
		return "Capacity [current=" + current + ", all=" + all
				+ ", averageStr=" + averageStr + "]";
	}

}
