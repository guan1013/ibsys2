package de.hska.centurion.domain.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.categories.FutureInwardStockMovement;
import de.hska.centurion.domain.input.categories.IdleTimeCosts;
import de.hska.centurion.domain.input.categories.InwardStockMovement;
import de.hska.centurion.domain.input.categories.WaitingListWorkstations;
import de.hska.centurion.domain.input.categories.WarehouseStock;

@XmlRootElement
public class Results {

	private int game;

	private int group;

	private int period;

	private WarehouseStock warehousestock;

	private InwardStockMovement inwardStockMovement;

	private FutureInwardStockMovement futureInwardStockMovement;
	
	private IdleTimeCosts idleTimeCosts;
	
	private WaitingListWorkstations waitingListWorkstations;
		
	@XmlElement(name = "waitinglistworkstations")
	public WaitingListWorkstations getWaitingListWorkstations() {
		return waitingListWorkstations;
	}

	public void setWaitingListWorkstations(
			WaitingListWorkstations waitingListWorkstations) {
		this.waitingListWorkstations = waitingListWorkstations;
	}

	@XmlElement(name = "idletimecosts")
	public IdleTimeCosts getIdleTimeCosts() {
		return idleTimeCosts;
	}

	public void setIdleTimeCosts(IdleTimeCosts idleTimeCosts) {
		this.idleTimeCosts = idleTimeCosts;
	}

	public WarehouseStock getWarehousestock() {
		return warehousestock;
	}

	public void setWarehousestock(WarehouseStock warehousestock) {
		this.warehousestock = warehousestock;
	}

	@XmlElement(name = "futureinwardstockmovement")
	public FutureInwardStockMovement getFutureInwardStockMovement() {
		return futureInwardStockMovement;
	}

	public void setFutureInwardStockMovement(
			FutureInwardStockMovement futureInwardStockMovement) {
		this.futureInwardStockMovement = futureInwardStockMovement;
	}

	@XmlElement(name = "inwardstockmovement")
	public InwardStockMovement getInwardStockMovement() {
		return inwardStockMovement;
	}

	public void setInwardStockMovement(InwardStockMovement inwardstockmovement) {
		this.inwardStockMovement = inwardstockmovement;
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
				+ ", inwardstockmovement=" + inwardStockMovement + "]";
	}

	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
