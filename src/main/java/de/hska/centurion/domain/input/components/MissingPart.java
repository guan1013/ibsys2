package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MissingPart {

	private int id;
	
	private WaitingList waitingList;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name="waitinglist")
	public WaitingList getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(WaitingList waitingList) {
		this.waitingList = waitingList;
	}

	@Override
	public String toString() {
		return "MissingPart [id=" + id + ", waitingList=" + waitingList + "]";
	}
	
	
}
