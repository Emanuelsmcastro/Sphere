package com.media.server.infra.exceptions;

import org.springframework.http.HttpStatus;

public class MediaException extends RuntimeException {
	private static final long serialVersionUID = -7152554316346783734L;
	private final HttpStatus status;

	public MediaException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
