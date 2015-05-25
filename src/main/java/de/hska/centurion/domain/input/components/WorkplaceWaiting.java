package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkplaceWaiting {

	private int id;
	
	private int timeNeed;
	
	private WaitingList waitingList;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name = "timeneed")
	public int getTimeNeed() {
		return timeNeed;
	}

	public void setTimeNeed(int timeNeed) {
		this.timeNeed = timeNeed;
	}

	@XmlElement(name = "waitinglist")
	public WaitingList getWaitingList() {
		return waitingList;
	}

	@Override
	public String toString() {
		return "WorkplaceWaiting [id=" + id + ", timeNeed=" + timeNeed
				+ ", waitingList=" + waitingList + "]";
	}

	public void setWaitingList(WaitingList waitingList) {
		this.waitingList = waitingList;
	}
	
	
	
}
