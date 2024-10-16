package com.publisher.server.infra.exceptions;

public class JsonConvertException extends RuntimeException{
	private static final long serialVersionUID = -7780016691371494831L;

	public JsonConvertException(String msg) {
		super(msg);
	}
}
