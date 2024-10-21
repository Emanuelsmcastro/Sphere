package com.oauth.server.infra.exceptions;

public class OauthServerException extends RuntimeException {
	private static final long serialVersionUID = -7276390547738192960L;

	public OauthServerException(String msg) {
		super(msg);
	}
}
