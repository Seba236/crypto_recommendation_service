package org.sebastianjaks.crypto_recommendation_service.service;

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
import org.sebastianjaks.crypto_recommendation_service.dto.currency_data.CurrencyData;
import org.sebastianjaks.crypto_recommendation_service.dto.currency_data.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyNormalizedRangeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

class GetCurrenciesOrderByNormTests {

	/**
	 * test the service method that should return list of currencies ordered by range</br>
	 * correct input - method should work properly
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws ParseException
	 */
	@Test
	void testGetCurrenciesOrderByNormRange() throws IOException, InvalidSourceDataException, ParseException {
		CurrencyFileReader currencyFileReader = mock(CurrencyFileReader.class);
		when(currencyFileReader.readAllCurrencyFiles()).thenReturn(generateTestCurrencies());
		
		CurrencyService currencyService = new CurrencyService(currencyFileReader);
		List<CurrencyNormalizedRangeStatisticsDTO> result = currencyService.getCurrenciesOrderByNormRange();
		
		//test length
		Assertions.assertEquals(2, result.size());
		
		//test order
		Assertions.assertEquals("BTC", result.get(0).getCurrencyName());
		Assertions.assertEquals("ETH", result.get(1).getCurrencyName());
		
		//test results
		Assertions.assertEquals(3, result.get(0).getNormalizedRange());
		Assertions.assertEquals(0.4, result.get(1).getNormalizedRange());
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
		
		
		return Arrays.asList(new CurrencyData[] {data1, data2});
	}
	
}
