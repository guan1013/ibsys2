package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sum")
public class IdleTimeCostsSum {

	private int setupEvents;

	private int idleTime;

	private String wageIdleTimeCostsStr;

	private String wageCostsStr;
	
	private String machineIdleTimeCostsStr;
	
	

	@Override
	public String toString() {
		return "IdleTimeCosts [setupEvents=" + setupEvents + ", idleTime="
				+ idleTime + ", wageIdleTimeCostsStr=" + wageIdleTimeCostsStr
				+ ", wageCostsStr=" + wageCostsStr
				+ ", machineIdleTimeCostsStr=" + machineIdleTimeCostsStr + "]";
	}

	@XmlAttribute(name = "machineidletimecosts")
	public String getMachineIdleTimeCostsStr() {
		return machineIdleTimeCostsStr;
	}

	public void setMachineIdleTimeCostsStr(String machineIdleTimeCostsStr) {
		this.machineIdleTimeCostsStr = machineIdleTimeCostsStr;
	}


	@XmlAttribute(name = "setupevents")
	public int getSetupEvents() {
		return setupEvents;
	}

	public void setSetupEvents(int setupEvents) {
		this.setupEvents = setupEvents;
	}
	@XmlAttribute(name = "idletime")
	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
	@XmlAttribute(name = "wageidletimecosts")
	public String getWageIdleTimeCostsStr() {
		return wageIdleTimeCostsStr;
	}

	public void setWageIdleTimeCostsStr(String wageIdleTimeCostsStr) {
		this.wageIdleTimeCostsStr = wageIdleTimeCostsStr;
	}

	@XmlAttribute(name = "wagecosts")
	public String getWageCostsStr() {
		return wageCostsStr;
	}

	public void setWageCostsStr(String wageCostsStr) {
		this.wageCostsStr = wageCostsStr;
	}
}
