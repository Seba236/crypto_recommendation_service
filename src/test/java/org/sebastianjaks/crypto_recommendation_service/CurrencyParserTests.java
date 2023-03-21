package org.sebastianjaks.crypto_recommendation_service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sebastianjaks.crypto_recommendation_service.business.parser.CurrencyParser;
import org.sebastianjaks.crypto_recommendation_service.dto.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.CurrencyNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

public class CurrencyParserTests {
	
	@Test
	public void testParsingCurrencyFile() throws URISyntaxException, IOException, InvalidSourceDataException, CurrencyNotPresentException {
		File fileInput	= new File(CurrencyParserTests.class.getResource("/org/sebastianjaks/crypto_recommendation_service/BTC_values.csv").toURI());
		
		List<SpotPriceDTO> spotPrices = new CurrencyParser(fileInput).readCurrencyFile();
		
		Assertions.assertEquals(100, spotPrices.size());
	}
	
}
