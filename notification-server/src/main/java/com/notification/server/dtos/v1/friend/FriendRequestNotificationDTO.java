package com.notification.server.dtos.v1.friend;

import java.util.UUID;

public record FriendRequestNotificationDTO(UUID sender, UUID receiver) {
}
