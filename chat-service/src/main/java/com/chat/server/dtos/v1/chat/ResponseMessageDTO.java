package com.chat.server.dtos.v1.chat;

import java.sql.Timestamp;
import java.util.UUID;

public record ResponseMessageDTO(UUID messageUUID, UUID chatUUID, UUID senderUUID, String senderName, String message, Timestamp createdAt) {

}
