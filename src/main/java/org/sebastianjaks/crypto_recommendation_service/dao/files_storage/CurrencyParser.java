package org.sebastianjaks.crypto_recommendation_service.dao.files_storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

public class CurrencyParser {
	
	
	private File file;
	
	
	
	/**
	 * constructor
	 * @param file
	 */
	public CurrencyParser(File file) {
		this.file=file;
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidSourceDataException 
	 * @throws DataNotPresentException 
	 */
	public List<SpotPriceDTO> readCurrencyFile() throws IOException, InvalidSourceDataException, DataNotPresentException{
		
		if(!getFile().exists())
			throw new DataNotPresentException();
		
		List<SpotPriceDTO> dayPrices = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(getFile()))){
			//skip first line
			br.readLine();
			
			//read all other lines
			String line;
			while((line=br.readLine())!=null) {
				//skip possible blank trailing lines
				if(line.isBlank())
					continue;
				
				dayPrices.add(parseLine(line));
			}
		}
		
		return dayPrices;
	}

	
	
	private SpotPriceDTO parseLine(String line) throws InvalidSourceDataException {
		String[] parts = line.split(",");
		
		if(parts.length!=3)
			throw new InvalidSourceDataException();
		
		Date day = new Date(Long.parseLong(parts[0]));
		double price = Double.parseDouble(parts[2]);

		return new SpotPriceDTO(day, price);
	}
	
	
	
	public File getFile() {
		return this.file;
	}
}
