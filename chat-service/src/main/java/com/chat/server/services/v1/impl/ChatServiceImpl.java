package com.chat.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.enums.ChatType;
import com.chat.server.infra.exceptions.ChatException;
import com.chat.server.mappers.v1.interfaces.ChatMapper;
import com.chat.server.repositories.ChatRepository;
import com.chat.server.services.v1.interfaces.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatMapper mapper;

	@Autowired
	ChatRepository rep;

	@Override
	public void save(Chat chat) {
		System.out.println(rep.save(chat));
	}

	@Override
	public void saveStringJson(String payload) {
		save(mapper.toEntity(mapper.convertStringJsonToDTO(payload)));
	}

	@Override
	public ResponseChatDTO getChatByChatTypeAndParticipantsUUID(ChatType type, UUID userUUID1, UUID userUUID2) {
		return mapper.toDTO(rep
							    .getChatByChatTypeAndParticipantsUUID(type, userUUID1, userUUID2)
								.orElseThrow(() -> new ChatException("Chat not found", HttpStatus.BAD_REQUEST)));
	}
}
