package org.sebastianjaks.crypto_recommendation_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sebastianjaks.crypto_recommendation_service.business.CurrencyService;
import org.sebastianjaks.crypto_recommendation_service.dao.files_storage.CurrencyFileReader;
import org.sebastianjaks.crypto_recommendation_service.dto.CurrencyData;
import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyFourAttributeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

public class GetCurrencyStatisticsTests {
	
	
	/**
	 * test the service method that should return basic statistics for given currency</br>
	 * correct input - method should work properly
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws ParseException
	 * @throws DataNotPresentException
	 */
	@Test
	public void testGetCurrencyStatistics() throws IOException, InvalidSourceDataException, ParseException, DataNotPresentException {
		CurrencyFileReader currencyFileReader = mock(CurrencyFileReader.class);
		when(currencyFileReader.readCurrencyFile("BTC")).thenReturn(generateTestCurrency());
		
		CurrencyService currencyService = new CurrencyService(currencyFileReader);
		CurrencyFourAttributeStatisticsDTO result = currencyService.getCurrencyStatistics("BTC");
		
		//test results
		Assertions.assertEquals(5000, result.getMinPrize());
		Assertions.assertEquals(20000, result.getMaxPrize());
		Assertions.assertEquals(14000, result.getNewestPrize());
		Assertions.assertEquals(20000, result.getOldestPrize());
	}



	private CurrencyData generateTestCurrency() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		List<SpotPriceDTO> spotPrices2 = new ArrayList<>();
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-01 11:00"), 20000));
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-01 18:00"), 5000));
		spotPrices2.add(new SpotPriceDTO(sdf.parse("2023-01-02 11:00"), 14000));
		CurrencyData data2 = new CurrencyData("BTC", spotPrices2);
		
		return data2;
	}
}
