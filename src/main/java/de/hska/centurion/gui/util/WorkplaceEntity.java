package de.hska.centurion.gui.util;

import javax.swing.JSpinner;

public class WorkplaceEntity {

	private JSpinner shift;
	
	private JSpinner overtime;

	public JSpinner getShift() {
		return shift;
	}

	public void setShift(JSpinner shift) {
		this.shift = shift;
	}

	public JSpinner getOvertime() {
		return overtime;
	}

	public void setOvertime(JSpinner overtime) {
		this.overtime = overtime;
	}

	public WorkplaceEntity(JSpinner shift, JSpinner overtime) {
		super();
		this.shift = shift;
		this.overtime = overtime;
	}
	
	
}
