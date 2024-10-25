package com.oauth.server.dtos.v1.chat;

import java.util.UUID;

public record CreateChatRequestDTO(UUID profile, UUID profile2) {

}
