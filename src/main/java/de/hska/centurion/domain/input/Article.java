package de.hska.centurion.domain.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Article {

	private int id;

	private int amount;

	private int startAmount;

	private String pctString;

	private String priceString;

	private String stockValueString;

	@XmlAttribute(name = "stockvalue")
	public String getStockValueString() {
		return stockValueString;
	}

	public void setStockValueString(String stockValueString) {
		this.stockValueString = stockValueString;
	}

	@XmlAttribute(name = "price")
	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

	@XmlAttribute(name = "pct")
	public String getPctString() {
		return pctString;
	}

	public void setPctString(String pctString) {
		this.pctString = pctString;
	}

	@XmlAttribute(name = "startamount")
	public int getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(int startAmount) {
		this.startAmount = startAmount;
	}

	@XmlAttribute
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", amount=" + amount + ", startAmount="
				+ startAmount + ", pctString=" + pctString + ", priceString="
				+ priceString + ", stockValueString=" + stockValueString + "]";
	}
}
