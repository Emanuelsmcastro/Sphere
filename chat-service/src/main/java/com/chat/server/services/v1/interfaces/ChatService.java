package com.chat.server.services.v1.interfaces;

import java.util.UUID;

import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.enums.ChatType;

public interface ChatService {

	void save(Chat chat);
	
	void saveStringJson(String payload);
	
	ResponseChatDTO getChatByChatTypeAndParticipantsUUID(ChatType type, UUID userUUID1, UUID userUUID2);
}
