package com.chat.server.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.mappers.v1.interfaces.MessageMapper;
import com.chat.server.repositories.MessageRepository;
import com.chat.server.services.v1.interfaces.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired
	MessageRepository messageRep;

	@Override
	public Page<ResponseMessageDTO> findByChat(Chat chat, Pageable pageable) {
		return messageMapper.toDTO(messageRep.findByChat(chat, pageable));
	}

}
