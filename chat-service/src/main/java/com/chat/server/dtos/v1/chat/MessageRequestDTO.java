package com.chat.server.dtos.v1.chat;

import java.util.UUID;

public record MessageRequestDTO(UUID chatUUID, UUID sender, String message) {

}
