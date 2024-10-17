package com.notification.server.services.v1.interfaces;

import java.util.UUID;

import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.infra.exception.FriendRequestRepException;

public interface FriendRequestNotificationService {

	void saveJson(String json);
	
	void save(FriendRequestNotification friendRequestNotification) throws FriendRequestRepException;
	
	FriendRequestNotification findBySenderAndReceiver(UUID sender, UUID receiver);
	
	FriendRequestNotification convertStringJsonToObject(String json);
}
