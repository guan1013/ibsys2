package de.hska.centurion.domain.input;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WarehouseStock {

	@XmlElement(name = "totalstockvalue")
	private String totalStockValueString;

	private List<Article> articles;

	public WarehouseStock() {
		this.articles = new ArrayList<Article>();
	}
	
	@XmlElement(name = "article")
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> article) {
		this.articles = article;
	}

	public String getTotalStockValueString() {
		return totalStockValueString;
	}

	public void setTotalStockValue(String totalStockValueString) {
		this.totalStockValueString = totalStockValueString;
	}

	@Override
	public String toString() {
		return "Warehousestock [totalStockValueString=" + totalStockValueString
				+ ", articles=" + articles.size() + "]";
	}

}
