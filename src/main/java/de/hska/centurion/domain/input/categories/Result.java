package de.hska.centurion.domain.input.categories;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.hska.centurion.domain.input.components.result.DefectiveGoods;
import de.hska.centurion.domain.input.components.result.DirectSale;
import de.hska.centurion.domain.input.components.result.General;
import de.hska.centurion.domain.input.components.result.MarketplaceSale;
import de.hska.centurion.domain.input.components.result.NormalSale;
import de.hska.centurion.domain.input.components.result.Summary;

@XmlRootElement
public class Result {

	private General general;

	private DefectiveGoods defectiveGoods;

	private NormalSale normalSale;

	private DirectSale directSale;
	
	private MarketplaceSale marketplaceSale;
	
	private Summary summary;

	@XmlElement
	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	@XmlElement(name = "marketplacesale")
	public MarketplaceSale getMarketplaceSale() {
		return marketplaceSale;
	}

	public void setMarketplaceSale(MarketplaceSale marketplaceSale) {
		this.marketplaceSale = marketplaceSale;
	}

	@XmlElement(name = "directsale")
	public DirectSale getDirectSale() {
		return directSale;
	}

	public void setDirectSale(DirectSale directSale) {
		this.directSale = directSale;
	}

	@XmlElement(name = "normalsale")
	public NormalSale getNormalSale() {
		return normalSale;
	}

	public void setNormalSale(NormalSale normalSale) {
		this.normalSale = normalSale;
	}

	@XmlElement(name = "defectivegoods")
	public DefectiveGoods getDefectiveGoods() {
		return defectiveGoods;
	}

	public void setDefectiveGoods(DefectiveGoods defectiveGoods) {
		this.defectiveGoods = defectiveGoods;
	}

	@XmlElement
	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

}
