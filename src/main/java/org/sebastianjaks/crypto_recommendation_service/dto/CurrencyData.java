package org.sebastianjaks.crypto_recommendation_service.dto;

import java.util.List;

public class CurrencyData {
	
	private String currencyName;
	private List<SpotPriceDTO> spotPrices;
	
	
	
	/**
	 * constructor
	 * @param currencyName
	 * @param spotPrices
	 */
	public CurrencyData(String currencyName, List<SpotPriceDTO> spotPrices) {
		this.currencyName=currencyName;
		this.spotPrices=spotPrices;
	}
	
	
	
	// --- getters and setters ---
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public List<SpotPriceDTO> getSpotPrices() {
		return spotPrices;
	}
	public void setSpotPrices(List<SpotPriceDTO> spotPrices) {
		this.spotPrices = spotPrices;
	}
}
