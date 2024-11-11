package com.utils.infra.exceptions;

import org.springframework.http.HttpStatus;

public class UtilsException extends RuntimeException {
	private static final long serialVersionUID = -802218607632100925L;
	private final HttpStatus status;
	
	public UtilsException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

}
