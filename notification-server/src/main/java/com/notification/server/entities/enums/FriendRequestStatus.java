package com.notification.server.entities.enums;

import com.notification.server.infra.exception.FriendRequestException;

public enum FriendRequestStatus {
	PENDING(0),
	REJECTED(1),
	ACCEPTED(2);
	
	private int currentStateValue;
	
	private FriendRequestStatus(int stateValue) {
		currentStateValue = stateValue;
	}
	
	public int getCurrentStateValue() {
		return this.currentStateValue;
	}
	
	public FriendRequestStatus getStatus(int value) {
		for(FriendRequestStatus status : values()) {
			if(status.getCurrentStateValue() == value) return status;
		}
		throw new FriendRequestException("Friend Request Status not exits.");
	}
}
