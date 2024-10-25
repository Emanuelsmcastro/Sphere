package com.notification.server.infra.exception;

public class FriendRequestException extends RuntimeException{
	private static final long serialVersionUID = -6085093239429209552L;

	public FriendRequestException(String msg) {
		super(msg);
	}
}
