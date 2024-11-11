package com.chat.server.mappers.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.server.dtos.v1.chat.CreateChatRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.mappers.v1.interfaces.ChatMapper;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Component
public class ChatMapperImpl implements ChatMapper {
	
	@Autowired
	GenericMapper genericMapper;

	@Override
	public CreateChatRequestDTO convertStringJsonToDTO(String payload) {
		return genericMapper.convertStringJsonToObject(CreateChatRequestDTO.class, payload);
	}

	@Override
	public Chat toEntity(CreateChatRequestDTO dto) {
		Chat chat = new Chat();
		chat.getParticipantsUUID().add(dto.profile());
		chat.getParticipantsUUID().add(dto.profile2());
		return chat;
	}

	@Override
	public ResponseChatDTO toDTO(Chat entity) {
		return new ResponseChatDTO(entity.getUuid());
	}

}
