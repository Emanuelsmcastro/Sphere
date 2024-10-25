package com.chat.server.mappers.v1.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.chat.server.dtos.v1.chat.CreateChatRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.exceptions.ChatException;
import com.chat.server.mappers.v1.interfaces.ChatMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatMapperImpl implements ChatMapper{

	@Override
	public CreateChatRequestDTO convertStringJsonToDTO(String payload) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(payload, CreateChatRequestDTO.class);
	    } catch (Exception e) {
	    	throw new ChatException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
	public Chat toEntity(CreateChatRequestDTO dto) {
		Chat chat = new Chat();
		chat
			.getParticipantsUUID()
			.add(dto.profile());
		chat
			.getParticipantsUUID()
			.add(dto.profile2());
		return chat;
	}

	@Override
	public ResponseChatDTO toDTO(Chat entity) {
		return new ResponseChatDTO(entity.getUuid());
	}

}
