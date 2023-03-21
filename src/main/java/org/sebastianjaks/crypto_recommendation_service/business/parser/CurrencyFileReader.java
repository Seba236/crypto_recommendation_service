package org.sebastianjaks.crypto_recommendation_service.business.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sebastianjaks.crypto_recommendation_service.constants.ApplicationConstants;
import org.sebastianjaks.crypto_recommendation_service.dto.CurrencyData;
import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.CurrencyNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;
import org.springframework.stereotype.Service;

@Service
public class CurrencyFileReader {
	
	
	private Logger log = LogManager.getLogger(CurrencyFileReader.class);
	
	
	/**
	 * loads all files
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException
	 * @throws CurrencyNotPresentException
	 */
	public List<CurrencyData> readAllCurrencyFiles() throws IOException, InvalidSourceDataException{
		List<CurrencyData> result = new ArrayList<>();
		
		File directory = new File(ApplicationConstants.INPUT_FILES_DIRECTORY);
		File[] files = directory.listFiles();
		
		Pattern p = Pattern.compile("([A-Z]{3})_values.csv");
		
		for(int i=0;i<files.length;i++) {
			Matcher m = p.matcher(files[i].getName());

			if(m.matches()) {
				List<SpotPriceDTO> spotPrices;
				try {
					spotPrices = new CurrencyParser(files[i]).readCurrencyFile();
				} catch (CurrencyNotPresentException e) {
					//this exception happens only when the input file is deleted between the finding and parsing the file
					log.error(e.getMessage(), e);
					continue;
				}
				CurrencyData data = new CurrencyData(m.group(1), spotPrices);
				result.add(data);
			}
		}
		
		return result;
	}



	/**
	 * loads data from file for specific currency
	 * @param currencyName
	 * @return
	 * @throws IOException
	 * @throws InvalidSourceDataException - if file is found, but its internal structure is unexpected, or the file contains no data
	 * @throws CurrencyNotPresentException - if the file is not found
	 */
	public CurrencyData readCurrencyFile(String currencyName) throws IOException, InvalidSourceDataException, CurrencyNotPresentException {
		//compose expected file name
		String fileName = currencyName+"_values.csv";
		
		//create file
		File f = new File(ApplicationConstants.INPUT_FILES_DIRECTORY+File.separator+fileName);
		
		//parse file
		List<SpotPriceDTO> spotPrices =  new CurrencyParser(f).readCurrencyFile();
		if(spotPrices.isEmpty())
			throw new InvalidSourceDataException("no data in file "+fileName);
		
		//create data object and return
		return new CurrencyData(currencyName, spotPrices);
	}
	
}
