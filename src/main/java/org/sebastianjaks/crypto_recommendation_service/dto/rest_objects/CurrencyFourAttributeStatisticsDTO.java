package org.sebastianjaks.crypto_recommendation_service.dto.rest_objects;

public class CurrencyFourAttributeStatisticsDTO {
	
	private String currencyName;
	private double maxPrize;
	private double minPrize;
	private double oldestPrize;
	private double newestPrize;
	
	
	
	/**
	 * constructor
	 * @param currencyName
	 * @param maxPrize
	 * @param minPrize
	 * @param oldestPrize
	 * @param newestPrize
	 */
	public CurrencyFourAttributeStatisticsDTO(String currencyName, double maxPrize, double minPrize, double oldestPrize, double newestPrize) {
		this.currencyName = currencyName;
		this.maxPrize = maxPrize;
		this.minPrize = minPrize;
		this.oldestPrize = oldestPrize;
		this.newestPrize = newestPrize;
	}

	
	
	// --- getters and setters ---
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public double getMaxPrize() {
		return maxPrize;
	}
	public void setMaxPrize(double maxPrize) {
		this.maxPrize = maxPrize;
	}
	public double getMinPrize() {
		return minPrize;
	}
	public void setMinPrize(double minPrize) {
		this.minPrize = minPrize;
	}
	public double getOldestPrize() {
		return oldestPrize;
	}
	public void setOldestPrize(double oldestPrize) {
		this.oldestPrize = oldestPrize;
	}
	public double getNewestPrize() {
		return newestPrize;
	}
	public void setNewestPrize(double newestPrize) {
		this.newestPrize = newestPrize;
	}
}
