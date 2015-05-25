package de.hska.centurion.domain.input.categories;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.MissingPart;

@XmlRootElement
public class WaitingListStock {

	private List<MissingPart> missingParts;

	public WaitingListStock() {
		this.missingParts = new ArrayList<MissingPart>();
	}

	@Override
	public String toString() {
		return "WaitingListStock [missingParts=" + missingParts + "]";
	}

	@XmlElement(name="missingpart")
	public List<MissingPart> getMissingParts() {
		return missingParts;
	}

	public void setMissingParts(List<MissingPart> missingParts) {
		this.missingParts = missingParts;
	}
}
