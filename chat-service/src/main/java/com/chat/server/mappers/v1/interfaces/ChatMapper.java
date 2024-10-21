package com.chat.server.mappers.v1.interfaces;

import com.chat.server.dtos.v1.chat.CreateChatRequestDTO;
import com.chat.server.infra.entities.Chat;

public interface ChatMapper {

	CreateChatRequestDTO convertStringJsonToDTO(String payload);
	
	Chat toEntity(CreateChatRequestDTO dto);
}
