package org.sebastianjaks.crypto_recommendation_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotPresentException extends Exception{

	private static final long serialVersionUID = -7475530848520078922L;

}
