package de.hska.centurion.domain.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.categories.CompletedOrders;
import de.hska.centurion.domain.input.categories.CycleTimes;
import de.hska.centurion.domain.input.categories.FutureInwardStockMovement;
import de.hska.centurion.domain.input.categories.IdleTimeCosts;
import de.hska.centurion.domain.input.categories.InwardStockMovement;
import de.hska.centurion.domain.input.categories.OrdersInWork;
import de.hska.centurion.domain.input.categories.Result;
import de.hska.centurion.domain.input.categories.WaitingListStock;
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

	private WaitingListStock waitingListStock;

	private OrdersInWork ordersInWork;

	private CompletedOrders completedOrders;
	
	private CycleTimes cycleTimes;
	
	private Result result;	
	
	@XmlElement
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@XmlElement(name = "cycletimes")
	public CycleTimes getCycleTimes() {
		return cycleTimes;
	}

	public void setCycleTimes(CycleTimes cycleTimes) {
		this.cycleTimes = cycleTimes;
	}

	@XmlElement(name = "completedorders")
	public CompletedOrders getCompletedOrders() {
		return completedOrders;
	}

	public void setCompletedOrders(CompletedOrders completedOrders) {
		this.completedOrders = completedOrders;
	}

	@XmlElement(name = "ordersinwork")
	public OrdersInWork getOrdersInWork() {
		return ordersInWork;
	}

	public void setOrdersInWork(OrdersInWork ordersInWork) {
		this.ordersInWork = ordersInWork;
	}

	@XmlElement(name = "waitingliststock")
	public WaitingListStock getWaitingListStock() {
		return waitingListStock;
	}

	public void setWaitingListStock(WaitingListStock waitingListStock) {
		this.waitingListStock = waitingListStock;
	}

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
				+ ", inwardStockMovement=" + inwardStockMovement
				+ ", futureInwardStockMovement=" + futureInwardStockMovement
				+ ", idleTimeCosts=" + idleTimeCosts
				+ ", waitingListWorkstations=" + waitingListWorkstations
				+ ", waitingListStock=" + waitingListStock + ", ordersInWork="
				+ ordersInWork + ", completedOrders=" + completedOrders
				+ ", cycleTimes=" + cycleTimes + ", result=" + result + "]";
	}

	@XmlAttribute
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
