package com.hb.api.ms.exception;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception Handler for the Hotel Reservation API
 */
@RestControllerAdvice
public class HotelApiExceptionHandler {

	Logger logger = Logger.getLogger(HotelApiExceptionHandler.class.getName());

	/**
	 * Exception handler for invalid requests.
	 *
	 * @param e InvalidRequestException
	 */
	@ExceptionHandler(value = InvalidRequestException.class)
	public ResponseEntity<ApiErrorMessage> handleInvalidRequest(InvalidRequestException e) {
		return handleBadRequest(e);
	}

	private ResponseEntity<ApiErrorMessage> handleBadRequest(Exception e) {
		logger.info(HttpStatus.BAD_REQUEST.getReasonPhrase() + e); // get full stacktrace
		return new ResponseEntity<>(new ApiErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
