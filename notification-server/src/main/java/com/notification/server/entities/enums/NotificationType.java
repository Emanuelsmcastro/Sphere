package com.notification.server.entities.enums;

import org.springframework.http.HttpStatus;

import com.notification.server.infra.exception.NotificationServerException;

public enum NotificationType {
	FRIEND_REQUEST(0),
	ADDED_FRIEND(1);
	
	private int currentStateValue;
	
	private NotificationType (int stateValue) {
		currentStateValue = stateValue;
	}
	
	public int getCurrentStateValue() {
		return this.currentStateValue;
	}
	
	public NotificationType  getStatus(int value) {
		for(NotificationType type : values()) {
			if(type.getCurrentStateValue() == value) return type;
		}
		throw new NotificationServerException("Notification Type not exists.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
