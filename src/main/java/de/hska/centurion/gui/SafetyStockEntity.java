package de.hska.centurion.gui;

import javax.swing.JLabel;
import javax.swing.JSpinner;

public class SafetyStockEntity {

	private JLabel label;
	
	private JSpinner wish;
	
	private JLabel calculated;

	public SafetyStockEntity(JLabel label, JSpinner wish, JLabel calculated) {
		super();
		this.label = label;
		this.wish = wish;
		this.calculated = calculated;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JSpinner getWish() {
		return wish;
	}

	public void setWish(JSpinner wish) {
		this.wish = wish;
	}

	public JLabel getCalculated() {
		return calculated;
	}

	public void setCalculated(JLabel calculated) {
		this.calculated = calculated;
	}
}
