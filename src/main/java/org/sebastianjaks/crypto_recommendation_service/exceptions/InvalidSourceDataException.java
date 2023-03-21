package org.sebastianjaks.crypto_recommendation_service.exceptions;

public class InvalidSourceDataException extends Exception{

	private static final long serialVersionUID = 3195633367887515576L;

	
	
	public InvalidSourceDataException(String message) {
		super(message);
	}
	
	
	
	public InvalidSourceDataException() {
		super();
	}
}
