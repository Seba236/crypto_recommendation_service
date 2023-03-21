package org.sebastianjaks.crypto_recommendation_service.utils;

import java.util.List;

import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;

public class StatisticsCalculator {
	
	/**
	 * returns normalized range calculated as (maxPrice-minPrice)/minPrice
	 * @param maxPrice
	 * @param minPrice
	 * @return
	 */
	public static double calculateNormalizedRange(double maxPrice, double minPrice) {
		return (maxPrice-minPrice)/minPrice;
	}
	
	
	
	/**
	 * returns normalized range calculated as (maxPrice-minPrice)/minPrice, or -1 if no data in the list
	 * @param spotPrices
	 * @return
	 */
	public static double calculateNormalizedRange(List<SpotPriceDTO> spotPrices) {
		if(spotPrices.size()==0)
			return -1;
		
		double maxPrice = getMaxPrice(spotPrices);
		double minPrice = getMinPrice(spotPrices);
		return calculateNormalizedRange(maxPrice, minPrice);
	}
	
	
	
	/**
	 * 
	 * @param spotPrices
	 * @return
	 */
	public static double getMaxPrice(List<SpotPriceDTO> spotPrices) {
		return spotPrices.stream().mapToDouble(sp->sp.getPrice()).max().orElse(-1);
	}
	
	
	
	/**
	 * 
	 * @param spotPrices
	 * @return
	 */
	public static double getMinPrice(List<SpotPriceDTO> spotPrices) {
		return spotPrices.stream().mapToDouble(sp->sp.getPrice()).min().orElse(-1);
	}
}
