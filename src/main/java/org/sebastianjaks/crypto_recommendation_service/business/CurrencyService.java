package org.sebastianjaks.crypto_recommendation_service.business;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sebastianjaks.crypto_recommendation_service.dao.files_storage.CurrencyFileReader;
import org.sebastianjaks.crypto_recommendation_service.dto.CurrencyData;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyFourAttributeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.dto.rest_objects.CurrencyNormalizedRangeStatisticsDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;
import org.sebastianjaks.crypto_recommendation_service.utils.CurrencyDataUtils;
import org.sebastianjaks.crypto_recommendation_service.utils.StatisticsCalculator;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
	
	
	private CurrencyFileReader fileReader;
	
	
	
	/**
	 * constructor
	 * @param fileReader
	 */
	public CurrencyService(CurrencyFileReader fileReader) {
		this.fileReader = fileReader;
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws DataNotPresentException
	 */
	public List<CurrencyNormalizedRangeStatisticsDTO> getCurrenciesOrderByNormRange() throws IOException, InvalidSourceDataException{
		List<CurrencyNormalizedRangeStatisticsDTO> result = new ArrayList<>();
		
		//load all current files
		List<CurrencyData> allCurrentData = fileReader.readAllCurrencyFiles();
		
		//calculate necessary statistics and create objects for the rest api
		for (CurrencyData curencyData : allCurrentData) {
			result.add(new CurrencyNormalizedRangeStatisticsDTO(curencyData.getCurrencyName(), StatisticsCalculator.calculateNormalizedRange(curencyData.getSpotPrices())));
		}
		
		//sort by normalized range descending
		result.sort((o1, o2)-> -Double.compare(o1.getNormalizedRange(), o2.getNormalizedRange()));
		
		return result;
	}



	/**
	 * 
	 * @param currencyName
	 * @return
	 * @throws DataNotPresentException 
	 * @throws InvalidSourceDataException 
	 * @throws IOException 
	 */
	public CurrencyFourAttributeStatisticsDTO getCurrencyStatistics(String currencyName) throws IOException, InvalidSourceDataException, DataNotPresentException {
		//load relevant file and throw exception if missing or corrupt
		CurrencyData currencyData = fileReader.readCurrencyFile(currencyName);
		
		// calculate statistics and create object for REST API
		double minPrice = StatisticsCalculator.getMinPrice(currencyData.getSpotPrices());
		double maxPrice = StatisticsCalculator.getMaxPrice(currencyData.getSpotPrices());
		double oldestPrice = currencyData.getSpotPrices().get(0).getPrice();
		double newestPrice = currencyData.getSpotPrices().get(currencyData.getSpotPrices().size()-1).getPrice();
		CurrencyFourAttributeStatisticsDTO result = new CurrencyFourAttributeStatisticsDTO(currencyName, maxPrice, minPrice, oldestPrice, newestPrice);
		
		return result;
	}
	
	
	
	/**
	 * TODO empty data handling
	 * @param day
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws ParseException 
	 * @throws DataNotPresentException 
	 */
	public CurrencyNormalizedRangeStatisticsDTO getCurrencyHighestNormRange(Date day) throws IOException, InvalidSourceDataException, ParseException, DataNotPresentException{
		List<CurrencyNormalizedRangeStatisticsDTO> result = new ArrayList<>();
		
		//load all current files
		List<CurrencyData> allCurrentData = fileReader.readAllCurrencyFiles();
		
		//filter by date range
		for (CurrencyData currencyData : allCurrentData) {
			CurrencyDataUtils.filterDataForOneDay(currencyData, day);
		}
		
		//calculate necessary statistics and create objects for the rest api
		for (CurrencyData curencyData : allCurrentData) {
			result.add(new CurrencyNormalizedRangeStatisticsDTO(curencyData.getCurrencyName(), StatisticsCalculator.calculateNormalizedRange(curencyData.getSpotPrices())));
		}
		
		//select currency with highest range
		CurrencyNormalizedRangeStatisticsDTO currencyMaxRange = null;
		for (CurrencyNormalizedRangeStatisticsDTO temp : result) {
			if(currencyMaxRange==null || currencyMaxRange.getNormalizedRange()<temp.getNormalizedRange())
				currencyMaxRange = temp;
		}
		
		//check for missing data
		if(currencyMaxRange==null || currencyMaxRange.getNormalizedRange()==-1)
			throw new DataNotPresentException();
			
		
		return currencyMaxRange;
	}
}
