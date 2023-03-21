package org.sebastianjaks.crypto_recommendation_service.dto;

import java.util.Date;

public class SpotPriceDTO {
	
	private Date dateTime;
	private double price;
	
	
	
	/**
	 * constructor
	 * @param dateTime
	 * @param price
	 */
	public SpotPriceDTO(Date dateTime, double price) {
		this.dateTime=dateTime;
		this.price=price;
	}
	
	
	
	//--- getters and setters ---
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date day) {
		this.dateTime = day;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
