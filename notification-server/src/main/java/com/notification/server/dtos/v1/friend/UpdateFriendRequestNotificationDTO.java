package com.notification.server.dtos.v1.friend;

import java.util.UUID;

import com.notification.server.entities.enums.FriendRequestStatus;

public record UpdateFriendRequestNotificationDTO(UUID uuid, FriendRequestStatus status) {
}
