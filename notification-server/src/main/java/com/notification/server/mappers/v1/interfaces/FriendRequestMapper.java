package com.notification.server.mappers.v1.interfaces;

import org.springframework.data.domain.Page;

import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.entities.FriendRequestNotification;

public interface FriendRequestMapper {

	FriendRequestNotification convertStringJsonToObject(String json);
	
	FriendRequestNotificationDTO toDTO(FriendRequestNotification entity);
	
	Page<FriendRequestNotificationDTO> toDTO(Page<FriendRequestNotification> page);
}
