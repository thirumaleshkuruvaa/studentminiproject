package com.thiru.ExceptionalHandling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {


	private HttpStatus httpStatus;
	private String errorMessage;
	private LocalDateTime localDateTime;
	public ErrorResponse(HttpStatus httpStatus, String errorMessage, LocalDateTime localDateTime) {
		super();
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
		this.localDateTime = localDateTime;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
}
