package org.sebastianjaks.crypto_recommendation_service.dto.rest_objects;

public class CurrencyNormalizedRangeStatisticsDTO {
	
	private String currencyName;
	private double normalizedRange;
	
	
	
	/**
	 * constructor
	 * @param currencyName
	 * @param normalizedRange
	 */
	public CurrencyNormalizedRangeStatisticsDTO(String currencyName, double normalizedRange) {
		this.currencyName=currencyName;
		this.normalizedRange=normalizedRange;
	}
	
	
	
	// --- getters and setters ---
	public String getCurrencyName() {
		return currencyName;
	}
	public double getNormalizedRange() {
		return normalizedRange;
	}
}
