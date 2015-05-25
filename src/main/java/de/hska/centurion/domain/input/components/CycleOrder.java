package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CycleOrder {

	private int id;
	
	private int period;
	
	private String startTime;
	
	private String finishTime;
	
	private int cycleTimeMin;
	
	private String cycleTimeFactorStr;

	@XmlAttribute(name="cycletimefactor")
	public String getCycleTimeFactorStr() {
		return cycleTimeFactorStr;
	}

	public void setCycleTimeFactorStr(String cycleTimeFactorStr) {
		this.cycleTimeFactorStr = cycleTimeFactorStr;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	@Override
	public String toString() {
		return "CycleOrder [id=" + id + ", period=" + period + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", cycleTimeMin="
				+ cycleTimeMin + ", cycleTimeFactorStr=" + cycleTimeFactorStr
				+ "]";
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	@XmlAttribute(name="starttime")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@XmlAttribute(name="finishtime")
	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@XmlAttribute(name="cycletimemin")
	public int getCycleTimeMin() {
		return cycleTimeMin;
	}

	public void setCycleTimeMin(int cycleTimeMin) {
		this.cycleTimeMin = cycleTimeMin;
	}

	
}
