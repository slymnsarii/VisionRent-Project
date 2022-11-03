package com.visionrent.exception.message;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class ApiResponseError {

	//AMACIM:customize error mesajlarini bu sinif icinde tutacagiz
	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	
	//exceprion mesaji
	private String message;
	
	//request edilen endpoint'i tutmak icin
	private String requestURI;
	
	//private constructor
	private ApiResponseError() {
		timestamp=LocalDateTime.now();
	}
	
	//Getter-Setter
	
	public ApiResponseError(HttpStatus status) {
		this();
		this.message="Unexpected Error";
		this.status=status;
	}
	public ApiResponseError(HttpStatus status, String message, String requestURI) {
		this(status); //1 parametreli olan constructur'i cagriyor
		this.message=message;
		this.requestURI=requestURI;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	
	
	
	
	
}
