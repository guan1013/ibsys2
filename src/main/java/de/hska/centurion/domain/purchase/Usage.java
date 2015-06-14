package de.hska.centurion.domain.purchase;

/**
 * This Class represents how often a K-Item is used in the three end products
 * P1, P2 and P3.
 * 
 * @author Simon
 *
 */
public class Usage {
	/*
	 * ======================== ATTRIBUTES ========================
	 */
	private int usageP1;
	private int usageP2;
	private int usageP3;

	/*
	 * ======================== CONSTRUCTORS ========================
	 */
	public Usage(int usageP1, int usageP2, int usageP3) {
		super();
		this.usageP1 = usageP1;
		this.usageP2 = usageP2;
		this.usageP3 = usageP3;
	}

	/*
	 * ======================== METHODS ========================
	 */

	public int getUsageP1() {
		return usageP1;
	}

	public void setUsageP1(int usageP1) {
		this.usageP1 = usageP1;
	}

	public int getUsageP2() {
		return usageP2;
	}

	public void setUsageP2(int usageP2) {
		this.usageP2 = usageP2;
	}

	public int getUsageP3() {
		return usageP3;
	}

	public void setUsageP3(int usageP3) {
		this.usageP3 = usageP3;
	}
	
	public int getUsageP(int productIndex) {
		if (productIndex == 1) {
			return usageP1;
		}
		else if (productIndex == 2) {
			return usageP2;
		}
		else if (productIndex == 3) {
			return usageP3;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Usage [usageP1=" + usageP1 + ", usageP2=" + usageP2
				+ ", usageP3=" + usageP3 + "]";
	}

}
