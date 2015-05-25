package de.hska.centurion.domain.input.components.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class General {

	private Statistic capacity;

	private Statistic possibleCapacity;

	private Statistic relPossibleNormalCapacity;

	private Statistic productiveTime;

	private Statistic effiency;

	private Statistic sellWish;

	private Statistic salesQuantity;

	private Statistic deliveryReliability;

	private Statistic idleTime;

	private Statistic idleTimeCosts;

	private Statistic storeValue;

	private Statistic storageCosts;

	@XmlElement(name = "storagecosts")
	public Statistic getStorageCosts() {
		return storageCosts;
	}

	public void setStorageCosts(Statistic storageCosts) {
		this.storageCosts = storageCosts;
	}

	@XmlElement(name = "storevalue")
	public Statistic getStoreValue() {
		return storeValue;
	}

	public void setStoreValue(Statistic storeValue) {
		this.storeValue = storeValue;
	}

	@XmlElement(name = "idletimecosts")
	public Statistic getIdleTimeCosts() {
		return idleTimeCosts;
	}

	public void setIdleTimeCosts(Statistic idleTimeCosts) {
		this.idleTimeCosts = idleTimeCosts;
	}

	@XmlElement(name = "idletime")
	public Statistic getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Statistic idleTime) {
		this.idleTime = idleTime;
	}

	@XmlElement(name = "deliveryreliability")
	public Statistic getDeliveryReliability() {
		return deliveryReliability;
	}

	public void setDeliveryReliability(Statistic deliveryReliability) {
		this.deliveryReliability = deliveryReliability;
	}

	@XmlElement(name = "salesquantity")
	public Statistic getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(Statistic salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	@XmlElement(name = "sellwish")
	public Statistic getSellWish() {
		return sellWish;
	}

	public void setSellWish(Statistic sellWish) {
		this.sellWish = sellWish;
	}

	@XmlElement
	public Statistic getEffiency() {
		return effiency;
	}

	public void setEffiency(Statistic effiency) {
		this.effiency = effiency;
	}

	@XmlElement(name = "productivetime")
	public Statistic getProductiveTime() {
		return productiveTime;
	}

	public void setProductiveTime(Statistic productiveTime) {
		this.productiveTime = productiveTime;
	}

	@XmlElement(name = "relpossiblenormalcapacity")
	public Statistic getRelPossibleNormalCapacity() {
		return relPossibleNormalCapacity;
	}

	public void setRelPossibleNormalCapacity(Statistic relPossibleNormalCapacity) {
		this.relPossibleNormalCapacity = relPossibleNormalCapacity;
	}

	@XmlElement(name = "possiblecapacity")
	public Statistic getPossibleCapacity() {
		return possibleCapacity;
	}

	public void setPossibleCapacity(Statistic possibleCapacity) {
		this.possibleCapacity = possibleCapacity;
	}

	@XmlElement
	public Statistic getCapacity() {
		return capacity;
	}

	public void setCapacity(Statistic capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "General [capacity=" + capacity + ", possibleCapacity="
				+ possibleCapacity + "]";
	}

}
