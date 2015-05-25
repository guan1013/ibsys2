package de.hska.centurion.domain.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Results {

	private int game;

	private int group;

	private int period;

	private WarehouseStock warehousestock;

	private InwardStockMovement inwardstockmovement;

	public WarehouseStock getWarehousestock() {
		return warehousestock;
	}

	public void setWarehousestock(WarehouseStock warehousestock) {
		this.warehousestock = warehousestock;
	}

	@XmlElement
	public InwardStockMovement getInwardstockmovement() {
		return inwardstockmovement;
	}

	public void setInwardstockmovement(InwardStockMovement inwardstockmovement) {
		this.inwardstockmovement = inwardstockmovement;
	}

	@XmlElement
	public WarehouseStock getWarehouseStock() {
		return warehousestock;
	}

	public void setWarehouseStock(WarehouseStock warehouseStock) {
		this.warehousestock = warehouseStock;
	}

	@XmlAttribute
	public int getGame() {
		return game;
	}

	public void setGame(int game) {
		this.game = game;
	}

	@XmlAttribute
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Results [game=" + game + ", group=" + group + ", period="
				+ period + ", warehousestock=" + warehousestock
				+ ", inwardstockmovement=" + inwardstockmovement + "]";
	}

	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
