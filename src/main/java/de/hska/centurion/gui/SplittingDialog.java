package de.hska.centurion.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import de.hska.centurion.domain.output.Production;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SplittingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private DialogResult result;

	private Production production;

	private int quantityA;

	private int quantityB;

	public DialogResult getResult() {
		return result;
	}

	public void setResult(DialogResult result) {
		this.result = result;
	}

	public int getQuantityA() {
		return quantityA;
	}

	public void setQuantityA(int quantityA) {
		this.quantityA = quantityA;
	}

	public int getQuantityB() {
		return quantityB;
	}

	public void setQuantityB(int quantityB) {
		this.quantityB = quantityB;
	}

	/**
	 * Create the dialog.
	 */
	public SplittingDialog(final Production production) {
		this.production = production;
		setModal(true);
		setTitle("Auftrag (" + production.getArticle() + " --- "
				+ production.getQuantity() + ") splitten");
		setBounds(100, 100, 239, 132);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		final JSpinner spinnerProductionA = new JSpinner();

		spinnerProductionA.setBounds(10, 11, 98, 35);
		contentPanel.add(spinnerProductionA);

		final JSpinner spinnerProductionB = new JSpinner();
		spinnerProductionB.setBounds(118, 11, 98, 35);
		contentPanel.add(spinnerProductionB);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Integer sum = (Integer) spinnerProductionA.getValue()
								+ (Integer) spinnerProductionB.getValue();

						if (sum != production.getQuantity()) {
							JOptionPane.showMessageDialog(null,
									"Die Summe der beiden Aufträge muss "
											+ production.getQuantity()
											+ " sein!", "Fehler",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						quantityA = (Integer) spinnerProductionA.getValue();
						quantityB = (Integer) spinnerProductionB.getValue();
						result = DialogResult.OK;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						result = DialogResult.CANCEL;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		Integer amountA = production.getQuantity() / 2
				+ production.getQuantity() % 2;
		Integer amountB = production.getQuantity() / 2;
		spinnerProductionA.setValue(amountA);
		spinnerProductionB.setValue(amountB);

		spinnerProductionA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerProductionB.setValue(production.getQuantity()
						- (Integer) spinnerProductionA.getValue());
			}
		});

		spinnerProductionB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerProductionA.setValue(production.getQuantity()
						- (Integer) spinnerProductionB.getValue());
			}
		});
	}

	public enum DialogResult {
		CANCEL, OK
	}
}
