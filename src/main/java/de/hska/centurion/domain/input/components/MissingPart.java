package de.hska.centurion.domain.input.components;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MissingPart {

	private int id;

	private List<WaitingList> waitingList;

	public MissingPart() {
		this.waitingList = new ArrayList<WaitingList>();
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name = "waitinglist")
	public List<WaitingList> getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(List<WaitingList> waitingList) {
		this.waitingList = waitingList;
	}

	@Override
	public String toString() {
		return "MissingPart [id=" + id + ", waitingList=" + waitingList + "]";
	}

}
