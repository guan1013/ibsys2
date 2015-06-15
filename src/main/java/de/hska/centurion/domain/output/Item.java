package de.hska.centurion.domain.output;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents one sellwish for the list of sales or direct sales.
 * 
 * @author Simon
 *
 */
@XmlRootElement(name = "item")
public class Item {
	
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * the name of the article.
	 * Either 1, 2 or 3.
	 */
	private int article;
	
	/**
	 * the quantity of items of this article.
	 */
	
	private int quantity;
	
	/**
	 * the price of the article apiece.
	 * only needed if the sale is a direct sale.
	 * Else this should be null.
	 */
	
	private Double price;
	
	/**
	 * the contract penalty of the direct sell.
	 * Only needed if the sale is a direct sale.
	 * Else this should be null.
	 */
	
	private Double penalty;
	
	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * The constructor to create a normal sellwish.
	 * @param article
	 * 				the name of the article, either 1, 2 or 3.
	 * @param quantity
	 * 				the quantity of items of the article.
	 */
	public Item(int article, int quantity) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.price = null;
		this.penalty = null;
	}

	public Item() {
		super();
	}

	/**
	 * The constructor to create a direct sell.
	 * @param article
	 * 				the name of the article, either 1, 2 or 3.
	 * @param quantity
	 * 				the quantity of items of the article.
	 * @param price
	 * 				the price apiece of the article.
	 * @param penalty
	 * 				the contract penalty for this article.
	 */
	public Item(int article, int quantity, double price, double penalty) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.price = price;
		this.penalty = penalty;
	}

	/*
	 * ======================== METHODS ========================
	 */
	public int getArticle() {
		return article;
	}
	@XmlAttribute(name = "article")
	public void setArticle(int article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}
	@XmlAttribute(name = "quantity")
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}
	@XmlAttribute(name = "price")
	public void setPrice(double price) {
		this.price = price;
	}

	public double getPenalty() {
		return penalty;
	}
	@XmlAttribute(name = "penalty")
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + article;
		long temp;
		temp = Double.doubleToLongBits(penalty);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (article != other.article)
			return false;
		if (Double.doubleToLongBits(penalty) != Double
				.doubleToLongBits(other.penalty))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [article=" + article + ", quantity=" + quantity
				+ ", price=" + price + ", penalty=" + penalty + "]";
	}
}
