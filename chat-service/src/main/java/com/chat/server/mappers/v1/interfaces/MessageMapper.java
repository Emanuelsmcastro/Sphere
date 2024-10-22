package com.chat.server.mappers.v1.interfaces;

import com.chat.server.dtos.v1.chat.MessageRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Message;

public interface MessageMapper {

	Message toEntity(MessageRequestDTO dto);
	
	ResponseMessageDTO toDTO(Message message);
}
