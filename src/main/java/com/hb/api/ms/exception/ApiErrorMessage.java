package com.hb.api.ms.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Model for the Error Messages
 */
public class ApiErrorMessage {
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	private ApiErrorMessage() {
		timestamp = LocalDateTime.now();
	}

	ApiErrorMessage(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiErrorMessage(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
