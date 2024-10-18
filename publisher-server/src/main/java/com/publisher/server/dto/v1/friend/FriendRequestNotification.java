package com.publisher.server.dto.v1.friend;

import java.util.UUID;

public record FriendRequestNotification(UUID sender, String senderName, UUID receiver) {
}
