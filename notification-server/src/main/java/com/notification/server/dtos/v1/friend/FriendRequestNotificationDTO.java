package com.notification.server.dtos.v1.friend;

import java.util.UUID;

public record FriendRequestNotificationDTO(UUID uuid, UUID sender, String senderName, UUID receiver) {
}
