package com.notification.server.mappers.v1.interfaces;

import com.notification.server.entities.FriendRequestNotification;

public interface FriendRequestMapper {

	FriendRequestNotification convertStringJsonToObject(String json);
}
