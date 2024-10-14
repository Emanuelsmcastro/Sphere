package com.oauth.server.infra.exceptions;

public class RegistrationException extends RuntimeException {
	private static final long serialVersionUID = -3013817082975158631L;

	public RegistrationException(String msg) {
		super(msg);
	}

}
