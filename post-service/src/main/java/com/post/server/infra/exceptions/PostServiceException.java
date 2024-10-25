package com.post.server.infra.exceptions;

import org.springframework.http.HttpStatus;

public class PostServiceException extends RuntimeException {
	private static final long serialVersionUID = 2087838161786524091L;
	private final HttpStatus status;

	public PostServiceException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
