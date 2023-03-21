package org.sebastianjaks.crypto_recommendation_service.utils;

import java.util.Date;

import org.sebastianjaks.crypto_recommendation_service.dto.currency_data.CurrencyData;

public class CurrencyDataUtils {
	
	private CurrencyDataUtils() {
	    throw new IllegalStateException("Utility class");
	}
	
	/**
	 * leaves data that are inside the specified range (start included, end excluded)
	 * @param currencyData
	 * @param rangeStart
	 * @param rangeEnd
	 */
	public static void filterDataByDateRange(CurrencyData currencyData, Date rangeStart, Date rangeEnd) {
		currencyData.setSpotPrices(currencyData.getSpotPrices().stream().filter(sp->(sp.getDateTime().getTime()>=rangeStart.getTime()) && sp.getDateTime().before(rangeEnd)).toList());
	}
	
	
	
	/**
	 * leaves data for only specified day</br>
	 * @param currencyData
	 * @param day - expected to be set to the first millisecond of the day
	 */
	public static void filterDataForOneDay(CurrencyData currencyData, Date day) {
		Date rangeEnd = new Date(day.getTime()+86400000);
		filterDataByDateRange(currencyData, day, rangeEnd);
	}
	
}
