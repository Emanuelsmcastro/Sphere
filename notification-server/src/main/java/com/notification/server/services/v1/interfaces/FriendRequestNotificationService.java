package com.notification.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.dtos.v1.friend.UpdateFriendRequestNotificationDTO;
import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.entities.enums.FriendRequestStatus;
import com.notification.server.infra.exception.FriendRequestRepException;

public interface FriendRequestNotificationService {

	void saveJson(String json);
	
	void save(FriendRequestNotification friendRequestNotification) throws FriendRequestRepException;
	
	void update(UpdateFriendRequestNotificationDTO dto);
	
	FriendRequestNotification findBySenderAndReceiver(UUID sender, UUID receiver);
	
	FriendRequestNotification convertStringJsonToObject(String json);
	
	Page<FriendRequestNotificationDTO> findByReceiver(UUID receiver, Pageable pageable);
	
	Page<FriendRequestNotificationDTO> findByReceiverAndStatus(UUID receiver, FriendRequestStatus status, Pageable pageable);
}
