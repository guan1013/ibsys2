package de.hska.centurion.domain.output;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents the XML-Input, which is needed to plan one period for
 * the scsim.de simulation.
 * 
 * @author Simon
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "input")
public class Input {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * Static element of the XML-Input.
	 */
	@XmlElement(name="qualitycontrol")
	private QualityControl qualityControl = new QualityControl();

	/**
	 * Contains the number of bicycles to be selled.
	 */
	@XmlElementWrapper(name = "sellwish")
	@XmlElement(name = "item")
	private List<Item> sellWish = new ArrayList<Item>();

	/**
	 * Contains the number of bicycles to be selled in special orders.
	 */
	@XmlElementWrapper(name = "selldirect")
	@XmlElement(name = "item")
	private List<Item> sellDirect = new ArrayList<Item>();

	/**
	 * A list of purchase orders for this period.
	 */
	@XmlElementWrapper(name = "orderlist")
	@XmlElement(name = "order")
	private List<Order> orderList = new ArrayList<Order>();

	/**
	 * A list of production orders for this period.
	 */
	@XmlElementWrapper(name = "productionlist")
	@XmlElement(name = "production")
	private List<Production> productionList = new ArrayList<Production>();

	/**
	 * A list, that contains the number of shifts and overtime for each
	 * workspace.
	 */
	@XmlElementWrapper(name = "workingtimelist")
	@XmlElement(name = "workingtime")
	private List<WorkingTime> workingTimeList = new ArrayList<WorkingTime>();

	/*
	 * ======================== METHODS ========================
	 */

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

	/**
	 * Adds a sellwish to the list of sellwishs.
	 * 
	 * @param sellWish
	 *            The sellwish to be added.
	 */
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

	/**
	 * Adds a direct sell to the list of direct sells.
	 * 
	 * @param sellDirect
	 *            The direct sell to be added.
	 */
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

	/**
	 * Adds a purchase order to the list.
	 * 
	 * @param order
	 *            the purchase order to be added.
	 */
	public void addOrder(Order order) {
		if (this.orderList == null) {
			this.orderList = new ArrayList<Order>();
		}
		this.orderList.add(order);
	}

	/**
	 * Removes a purchase order to the list.
	 * 
	 * @param index
	 *            the index of the purchase order to be removed.
	 */
	public void removeOrder(int index) {
		if (this.orderList == null) {
			return;
		}
		this.orderList.remove(index);
	}

	public List<Production> getProductionList() {
		return productionList;
	}

	public void setProductionList(List<Production> productionList) {
		this.productionList = productionList;
	}

	/**
	 * Adds a production order to the list.
	 * 
	 * @param production
	 *            the production order to be added.
	 */
	public void addProduction(Production production) {
		if (this.productionList == null) {
			this.productionList = new ArrayList<Production>();
		}
		this.productionList.add(production);
	}

	/**
	 * Changes the chronological order of the production order list. Example: If
	 * you want to put the first item of the list in the third place index must
	 * be 0, and newIndex must be 2.
	 * 
	 * @param index
	 *            the original index of the production order.
	 * @param newIndex
	 *            the new index of the production order.
	 */
	public void changeProductionOrder(int index, int newIndex) {
		if (this.productionList == null) {
			this.productionList = new ArrayList<Production>();
		}
		Production production = this.productionList.get(index);
		this.productionList.remove(index);
		this.productionList.add(newIndex, production);
	}

	/**
	 * Splits a production order in two separate orders. Example: If you want to
	 * split the first production order with a amount of 100 items in two orders
	 * with 50 items each index must be 0 amountA must be 50 and amountB must be
	 * 50.
	 * 
	 * @param index
	 *            the index of the production order.
	 * @param amountA
	 *            the number of items to be produced in the first separate
	 *            order.
	 * @param amountB
	 *            the number of items to be produced in the second separate
	 *            order.
	 */
	public void splitProduction(int index, int amountA, int amountB) {
		if (this.productionList == null) {
			this.productionList = new ArrayList<Production>();
		}
		if (amountA == 0 || amountB == 0) {
			return;
		}

		Production production = this.productionList.get(index);
		if (amountA + amountB != production.getQuantity()) {
			return;
		}
		production.setQuantity(amountA);
		Production productionB = new Production(production.getArticle(),
				amountB);
		productionList.add(index + 1, productionB);

	}

	public List<WorkingTime> getWorkingTimeList() {
		return workingTimeList;
	}

	public void setWorkingTimeList(List<WorkingTime> workingTimeList) {
		this.workingTimeList = workingTimeList;
	}

	/**
	 * Adds a configuration (shift and overtime) for one workspace to the list.
	 * 
	 * @param workingTime
	 */
	public void addWorkingTime(WorkingTime workingTime) {
		if (this.workingTimeList == null) {
			this.workingTimeList = new ArrayList<WorkingTime>();
		}
		this.workingTimeList.add(workingTime);
	}

	@Override
	public String toString() {
		return "Input [qualityControl=" + qualityControl + ", sellWishSize="
				+ sellWish.size() + ", sellDirectSize=" + sellDirect.size()
				+ ", orderListSize=" + orderList.size()
				+ ", productionListSize=" + productionList.size()
				+ ", workingListSize=" + workingTimeList.size() + "]";
	}

}
