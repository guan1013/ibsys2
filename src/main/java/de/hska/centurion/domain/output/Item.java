package de.hska.centurion.domain.output;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents one sellwish for the list of sales or direct sales.
 * 
 * @author Simon
 *
 */
@XmlRootElement(name = "item")
@XmlType(propOrder={"article", "quantity", "price", "penalty"})
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
	
	private String price;
	
	/**
	 * the contract penalty of the direct sell.
	 * Only needed if the sale is a direct sale.
	 * Else this should be null.
	 */
	
	private String penalty;
	
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
	public Item(int article, int quantity, String price, String penalty) {
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

	public String getPrice() {
		return price;
	}
	@XmlAttribute(name = "price")
	public void setPrice(String price) {
		this.price = price;
	}

	public String getPenalty() {
		return penalty;
	}
	@XmlAttribute(name = "penalty")
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	@Override
	public String toString() {
		return "Item [article=" + article + ", quantity=" + quantity
				+ ", price=" + price + ", penalty=" + penalty + "]";
	}
}
