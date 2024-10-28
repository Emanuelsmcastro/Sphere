package com.chat.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chat.server.dtos.v1.chat.MessageRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.enums.ChatType;

public interface ChatService {

	void save(Chat chat);
	
	void saveStringJson(String payload);
	
	void addMessage(MessageRequestDTO dto);
	
	ResponseChatDTO getChatByChatTypeAndParticipantsUUID(ChatType type, UUID userUUID1, UUID userUUID2);
	
	Chat findByUuid(UUID uuid);
	
	Chat findByUuidAndSenderUuid(UUID chatUuid, UUID senderUuid);
	
	Page<ResponseMessageDTO> findMessagesByChat(UUID chatUUID, UUID profileUUID, Pageable pageable);
}
