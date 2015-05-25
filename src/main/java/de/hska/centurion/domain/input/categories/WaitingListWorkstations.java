package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.WorkplaceWaiting;

@XmlRootElement(name = "waitinglistworkstations")
public class WaitingListWorkstations {

	private List<WorkplaceWaiting> workplaces;

	public WaitingListWorkstations() {
		this.workplaces = new ArrayList<WorkplaceWaiting>();
	}

	@XmlElement(name = "workplace")
	public List<WorkplaceWaiting> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(List<WorkplaceWaiting> workplaces) {
		this.workplaces = workplaces;
	}
}
