package de.hska.centurion.domain.output;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon
 *
 */
public class Input {

	private QualityControl qualityControl = new QualityControl();
	private List<Item> sellWish = new ArrayList<Item>();
	private List<Item> sellDirect = new ArrayList<Item>();
	private List<Order> orderList = new ArrayList<Order>();
	private List<Production> productionList = new ArrayList<Production>();
	private List<WorkingTime> workingTimeList = new ArrayList<WorkingTime>();

	public QualityControl getQualityControl() {
		return qualityControl;
	}

	public void setQualityControl(QualityControl qualityControl) {
		this.qualityControl = qualityControl;
	}

	public List<Item> getSellWish() {
		return sellWish;
	}

	public void setSellWish(List<Item> sellWish) {
		this.sellWish = sellWish;
	}

	public void addSellWish(Item sellWish) {
		if (this.sellWish == null) {
			this.sellWish = new ArrayList<Item>();
		}
		this.sellWish.add(sellWish);
	}

	public List<Item> getSellDirect() {
		return sellDirect;
	}

	public void setSellDirect(List<Item> sellDirect) {
		this.sellDirect = sellDirect;
	}

	public void addSellDirect(Item sellDirect) {
		if (this.sellDirect == null) {
			this.sellDirect = new ArrayList<Item>();
		}
		this.sellDirect.add(sellDirect);
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public void addOrder(Order order) {
		if (this.orderList == null) {
			this.orderList = new ArrayList<Order>();
		}
		this.orderList.add(order);
	}

	public List<Production> getProductionList() {
		return productionList;
	}

	public void setProductionList(List<Production> productionList) {
		this.productionList = productionList;
	}

	public void addProduction(Production production) {
		if (this.productionList == null) {
			this.productionList = new ArrayList<Production>();
		}
		this.productionList.add(production);
	}

	public List<WorkingTime> getWorkingTimeList() {
		return workingTimeList;
	}

	public void setWorkingTimeList(List<WorkingTime> workingTimeList) {
		this.workingTimeList = workingTimeList;
	}
	
	public void addWorkingTime(WorkingTime workingTime) {
		if (this.workingTimeList == null) {
			this.workingTimeList = new ArrayList<WorkingTime>();
		}
		this.workingTimeList.add(workingTime);
	}

	@Override
	public String toString() {
		return "Input [qualityControl=" + qualityControl 
				+ ", sellWishSize=" + sellWish.size()
				+ ", sellDirectSize=" + sellDirect.size()
				+ ", orderListSize=" + orderList.size()
				+ ", productionListSize=" + productionList.size()
				+ ", workingListSize=" + workingTimeList.size()
				+ "]";
	}

}
