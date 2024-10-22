package com.chat.server.dtos.v1.chat;

import java.sql.Timestamp;
import java.util.UUID;

public record ResponseMessageDTO(UUID chatUUID, UUID senderUUID, String message, Timestamp createdAt) {

}
