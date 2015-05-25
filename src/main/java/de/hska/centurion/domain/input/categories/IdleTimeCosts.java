package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.IdleTimeCostsSum;
import de.hska.centurion.domain.input.components.WorkplaceCosts;

@XmlRootElement
public class IdleTimeCosts {

	private List<WorkplaceCosts> workplaces;
	
	private IdleTimeCostsSum sum;

	@XmlElement
	public IdleTimeCostsSum getSum() {
		return sum;
	}

	public void setSum(IdleTimeCostsSum sum) {
		this.sum = sum;
	}

	public IdleTimeCosts() {
		this.workplaces = new ArrayList<WorkplaceCosts>();
	}

	@XmlElement(name = "workplace")
	public List<WorkplaceCosts> getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(List<WorkplaceCosts> workplaces) {
		this.workplaces = workplaces;
	}

	@Override
	public String toString() {
		return "IdleTimeCosts [workplaces=" + workplaces + "]";
	}
}
