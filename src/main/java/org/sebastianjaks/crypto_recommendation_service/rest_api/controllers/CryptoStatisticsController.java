package org.sebastianjaks.crypto_recommendation_service.rest_api.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.sebastianjaks.crypto_recommendation_service.business.CurrencyService;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyFourAttributeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyNormalizedRangeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.BadHttpParameterException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;
import org.sebastianjaks.crypto_recommendation_service.rest_api.validators.ParametersValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoStatisticsController {
	
	private CurrencyService currencyService;
	
	private ParametersValidator parametersValidator;
	
	
	
	/**
	 * constructor
	 * @param currencyService
	 */
	public CryptoStatisticsController(CurrencyService currencyService, ParametersValidator parametersValidator) {
		this.currencyService=currencyService;
		this.parametersValidator=parametersValidator;
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws DataNotPresentException 
	 * @throws InvalidSourceDataException 
	 * @throws IOException 
	 */
	@GetMapping("/get_all_normalized_ranges_sorted")
	public List<CurrencyNormalizedRangeStatisticsDTO> getNormalizedRangesSorted() throws IOException, InvalidSourceDataException{
		return currencyService.getCurrenciesOrderByNormRange();
	}
	
	
	
	/**
	 * 
	 * @param currencyName
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws DataNotPresentException
	 * @throws BadHttpParameterException
	 */
	@GetMapping("/get_statistics_for_currency")
	public CurrencyFourAttributeStatisticsDTO getStatisticsForCurrency(@RequestParam(value="currency") String currencyName) throws IOException, InvalidSourceDataException, DataNotPresentException, BadHttpParameterException{
		//check currency parameter
		parametersValidator.validateCurrencyName(currencyName);
		//retrieve data
		return currencyService.getCurrencyStatistics(currencyName);
	}
	
	
	
	/**
	 * 
	 * @param day
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws DataNotPresentException
	 * @throws BadHttpParameterException
	 * @throws ParseException
	 */
	@GetMapping("/get_highest_normalized_range")
	public CurrencyNormalizedRangeStatisticsDTO getHighestNormalizedRange(@RequestParam(value="day") String day) throws IOException, InvalidSourceDataException, BadHttpParameterException, DataNotPresentException{
		//check day parameter
		Date dayDate = parametersValidator.validateDayPatternAndConvert(day);
		//retrieve data
		return currencyService.getCurrencyHighestNormRange(dayDate);
	}
	
}
