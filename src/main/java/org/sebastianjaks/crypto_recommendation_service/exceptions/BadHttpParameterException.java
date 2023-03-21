package org.sebastianjaks.crypto_recommendation_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadHttpParameterException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * 
	 * @param message
	 */
	public BadHttpParameterException(String message) {
		super(message);
	}
	
}
