package de.hska.centurion.gui.util;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class OrderEntity {

	private JTextField itemIndex;

	private JTextField quantity;

	private JComboBox<OrderType> orderType;

	public JTextField getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(JTextField itemIndex) {
		this.itemIndex = itemIndex;
	}

	public JTextField getQuantity() {
		return quantity;
	}

	public void setQuantity(JTextField quantity) {
		this.quantity = quantity;
	}

	public JComboBox<OrderType> getOrderType() {
		return orderType;
	}

	public void setOrderType(JComboBox<OrderType> orderType) {
		this.orderType = orderType;
	}

	public OrderEntity(JTextField itemIndex, JTextField quantity,
			JComboBox<OrderType> orderType) {
		super();
		this.itemIndex = itemIndex;
		this.quantity = quantity;
		this.orderType = orderType;
	}

}
