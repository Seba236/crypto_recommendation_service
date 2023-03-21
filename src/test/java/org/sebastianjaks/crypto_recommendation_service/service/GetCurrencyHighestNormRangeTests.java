package org.sebastianjaks.crypto_recommendation_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sebastianjaks.crypto_recommendation_service.business.CurrencyService;
import org.sebastianjaks.crypto_recommendation_service.dao.files_storage.CurrencyFileReader;
import org.sebastianjaks.crypto_recommendation_service.dto.CurrencyData;
import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyNormalizedRangeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

public class GetCurrencyHighestNormRangeTests {
	
	/**
	 * test the service method that should return currency with greatest range for specific day</br>
	 * correct input - method should work properly
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws ParseException
	 * @throws DataNotPresentException
	 */
	@Test
	public void testGetCurrencyHighestNormRange() throws IOException, InvalidSourceDataException, ParseException, DataNotPresentException {
		CurrencyFileReader currencyFileReader = mock(CurrencyFileReader.class);
		when(currencyFileReader.readAllCurrencyFiles()).thenReturn(generateTestCurrencies());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		CurrencyService currencyService = new CurrencyService(currencyFileReader);
		CurrencyNormalizedRangeStatisticsDTO result = currencyService.getCurrencyHighestNormRange(sdf.parse("2023-01-01"));
		
		//test result
		Assertions.assertEquals("ETH", result.getCurrencyName());
		Assertions.assertEquals(0.4, result.getNormalizedRange());
	}
	
	
	
	/**
	 * test the service method that should return currency with greatest range for specific day</br>
	 * no data for given day for all currencies - InvalidSourceDataException is expected to be thrown
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws ParseException
	 * @throws DataNotPresentException
	 */
	@Test
	public void testGetCurrencyHighestNormRangeNoMatchingData() throws IOException, InvalidSourceDataException, ParseException, DataNotPresentException {
		CurrencyFileReader currencyFileReader = mock(CurrencyFileReader.class);
		when(currencyFileReader.readAllCurrencyFiles()).thenReturn(generateTestCurrencies());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		CurrencyService currencyService = new CurrencyService(currencyFileReader);
		assertThrows(DataNotPresentException.class, ()->currencyService.getCurrencyHighestNormRange(sdf.parse("2023-01-23")));

	}
	
	

	private List<CurrencyData> generateTestCurrencies() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		List<SpotPriceDTO> spotPrices1 = new ArrayList<>();
		spotPrices1.add(new SpotPriceDTO(sdf.parse("2023-01-01 11:00"), 1400));
		spotPrices1.add(new SpotPriceDTO(sdf.parse("2023-01-01 18:00"), 1000));
		spotPrices1.add(new SpotPriceDTO(sdf.parse("2023-01-02 11:00"), 1200));
		CurrencyData data1 = new CurrencyData("ETH", spotPrices1);
		
		List<SpotPriceDTO> spotPrices2 = new ArrayList<>();
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-01 11:00"), 20000));
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-02 18:00"), 5000));
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-02 20:00"), 14000));
		CurrencyData data2 = new CurrencyData("BTC", spotPrices2);
		
		List<SpotPriceDTO> spotPrices3 = new ArrayList<>();
		spotPrices3.add(new SpotPriceDTO(sdf.parse("2023-01-02 11:00"), 1));
		spotPrices3.add(new SpotPriceDTO(sdf.parse("2023-01-02 18:00"), 10000));
		spotPrices3.add(new SpotPriceDTO(sdf.parse("2023-01-02 20:00"), 5));
		CurrencyData data3 = new CurrencyData("LTC", spotPrices3);
		
		
		return Arrays.asList(new CurrencyData[] {data1, data2, data3});
	}

}
