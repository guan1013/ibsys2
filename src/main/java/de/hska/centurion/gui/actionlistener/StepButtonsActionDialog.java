package de.hska.centurion.gui.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

import de.hska.centurion.gui.PlanungstoolGUI;

public class StepButtonsActionDialog implements ActionListener {

	PlanungstoolGUI parent;

	public StepButtonsActionDialog(PlanungstoolGUI parent) {
		super();
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();

		if (src instanceof JButton) {
			Integer goToStep = getNumberFromString(((JButton) src).getText());
			if (goToStep != null) {
				parent.switchToStep(goToStep);
			} else {
				return;
			}
		}

		return;
	}

	private Integer getNumberFromString(String s) {

		String pattern = ".*(\\d+).*";
		Matcher m = Pattern.compile(pattern).matcher(s);

		if (m.find()) {
			return Integer.parseInt(m.group(1));
		}

		return null;
	}
}