package com.publisher.server.services.v1.interfaces;

import com.publisher.server.dto.v1.friend.FriendRequestNotification;

public interface FriendRequestService {

	void friendRequestPublish(FriendRequestNotification dto);
}
