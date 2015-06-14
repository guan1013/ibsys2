package de.hska.centurion.domain.output;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * This class represents a production order in the list of production orders.
 * @author Simon
 *
 */
public class Production {
	
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	/**
	 * The name of the article, between 1 and 56 (with exceptions).
	 */
	
	private int article;
	
	/**
	 * The quantity of the article to be produced.
	 */
	
	private int quantity;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	/**
	 * The constructor to create a production order.
	 * @param article
	 * 				The name of the article, between 1 and 56 (with exceptions).
	 * @param quantity
	 * 				The quantity of the article to be produced.
	 */
	public Production(int article, int quantity) {
		super();
		this.article = article;
		this.quantity = quantity;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + article;
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
		Production other = (Production) obj;
		if (article != other.article)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Production [article=" + article + ", quantity=" + quantity
				+ "]";
	}

}
