package com.chat.server.infra.exceptions;

import org.springframework.http.HttpStatus;

public class ChatException extends RuntimeException{
	private static final long serialVersionUID = 9153241424101694443L;
	private final HttpStatus status;

	public ChatException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}
