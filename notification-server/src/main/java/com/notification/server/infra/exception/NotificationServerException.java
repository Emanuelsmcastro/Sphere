package com.notification.server.infra.exception;

import org.springframework.http.HttpStatus;

public class NotificationServerException extends RuntimeException {
	private static final long serialVersionUID = -2431565415553399332L;
	private final HttpStatus status;

	public NotificationServerException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
