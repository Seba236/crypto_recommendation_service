package org.sebastianjaks.crypto_recommendation_service.rest_api.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.sebastianjaks.crypto_recommendation_service.exceptions.BadHttpParameterException;
import org.springframework.stereotype.Service;

@Service
public class ParametersValidator {
	
	
	private static final Pattern CURRENCY_NAME_PATTERN = Pattern.compile("[A-Z]{3}");
	
	private static final SimpleDateFormat DAY_FORMATTER = new SimpleDateFormat("yyyy-M-d");
	
	
	
	/**
	 * 
	 * @param currencyName
	 * @throws BadHttpParameterException 
	 */
	public void validateCurrencyName(String currencyName) throws BadHttpParameterException {
		if(!CURRENCY_NAME_PATTERN.matcher(currencyName).matches())
			throw new BadHttpParameterException("Text not valid as currency name: "+currencyName);
	}
	
	
	
	/**
	 * 
	 * @param day
	 * @throws BadHttpParameterException
	 */
	public Date validateDayPatternAndConvert(String day) throws BadHttpParameterException {
		try {
			return DAY_FORMATTER.parse(day);
		} catch (ParseException e) {
			throw new BadHttpParameterException("Text not valid as day: "+day);
		}
	}
}
