/**
 * 
 */
package de.hska.centurion.domain.output;

/**
 * @author Simon
 *
 */
public class Order {
	private int article;
	private int quantity;
	private int modus; // 1 = Sonderbestellung, 2 = Billiganbieter, 3 = JIT, 4 =
						// Eil, 5 = Normal

	public Order(int article, int quantity, int modus) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.modus = modus;
	}

	public int getArticle() {
		return article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getModus() {
		return modus;
	}

	public void setModus(int modus) {
		this.modus = modus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + article;
		result = prime * result + modus;
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
		Order other = (Order) obj;
		if (article != other.article)
			return false;
		if (modus != other.modus)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [article=" + article + ", quantity=" + quantity
				+ ", modus=" + modus + "]";
	}

}
