package com.chat.server.mappers.v1.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.chat.server.dtos.v1.chat.MessageRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Message;
import com.chat.server.mappers.v1.interfaces.MessageMapper;

@Component
public class MessageMapperImpl implements MessageMapper {

	@Override
	public Message toEntity(MessageRequestDTO dto) {
		return Message.Builder.of(dto.sender(), dto.message()).setSenderName(dto.senderName()).build();
	}

	@Override
	public ResponseMessageDTO toDTO(Message message) {
		return new ResponseMessageDTO(message.getUuid(), message.getChat().getUuid(), message.getSenderUUID(), message.getSenderName(),
				message.getMessage(), message.getCreatedAt());
	}

	@Override
	public Page<ResponseMessageDTO> toDTO(Page<Message> pageable) {
		return pageable.map(this::toDTO);
	}

}
