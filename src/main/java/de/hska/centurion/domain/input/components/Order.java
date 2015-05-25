package de.hska.centurion.domain.input.components;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

	private int orderPeriod;

	private int id;

	/*
	 * mode 5: normal
	 * mode 4: fast
	 * mode 3: JIT
	 * mode 2: cheap vendor
	 * mode 1: special order
	 * 
	 */
	private int mode;

	private int article;

	private int amount;

	private int time;

	private String materialCostsStr;

	private String orderCostsStr;

	private String entireCostsStr;

	private String pieceCostsStr;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@XmlAttribute
	public int getArticle() {
		return article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	@XmlAttribute
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@XmlAttribute
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@XmlAttribute(name = "materialcosts")
	public String getMaterialCostsStr() {
		return materialCostsStr;
	}

	public void setMaterialCostsStr(String materialCostsStr) {
		this.materialCostsStr = materialCostsStr;
	}

	@XmlAttribute(name = "ordercosts")
	public String getOrderCostsStr() {
		return orderCostsStr;
	}

	public void setOrderCostsStr(String orderCostsStr) {
		this.orderCostsStr = orderCostsStr;
	}

	@XmlAttribute(name = "entirecosts")
	public String getEntireCostsStr() {
		return entireCostsStr;
	}

	public void setEntireCostsStr(String entireCostsStr) {
		this.entireCostsStr = entireCostsStr;
	}

	@XmlAttribute(name = "piececosts")
	public String getPieceCostsStr() {
		return pieceCostsStr;
	}

	public void setPieceCostsStr(String pieceCostsStr) {
		this.pieceCostsStr = pieceCostsStr;
	}

	@XmlAttribute(name = "orderperiod")
	public int getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(int orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	@Override
	public String toString() {
		return "Order [orderPeriod=" + orderPeriod + ", id=" + id + ", mode="
				+ mode + ", article=" + article + ", amount=" + amount
				+ ", time=" + time + ", materialCostsStr=" + materialCostsStr
				+ ", orderCostsStr=" + orderCostsStr + ", entireCostsStr="
				+ entireCostsStr + ", pieceCostsStr=" + pieceCostsStr + "]";
	}
}
