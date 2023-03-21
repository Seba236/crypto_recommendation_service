package org.sebastianjaks.crypto_recommendation_service.dao;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sebastianjaks.crypto_recommendation_service.dao.files_storage.CurrencyParser;
import org.sebastianjaks.crypto_recommendation_service.dto.currency_data.SpotPriceDTO;
import org.sebastianjaks.crypto_recommendation_service.exceptions.DataNotPresentException;
import org.sebastianjaks.crypto_recommendation_service.exceptions.InvalidSourceDataException;

class CurrencyParserTests {
	
	@Test
	void testParsingCurrencyFile() throws URISyntaxException, IOException, InvalidSourceDataException, DataNotPresentException {
		File fileInput	= new File(CurrencyParserTests.class.getResource("/org/sebastianjaks/crypto_recommendation_service/BTC_values.csv").toURI());
		
		List<SpotPriceDTO> spotPrices = new CurrencyParser(fileInput).readCurrencyFile();
		
		Assertions.assertEquals(100, spotPrices.size());
	}
	
}
