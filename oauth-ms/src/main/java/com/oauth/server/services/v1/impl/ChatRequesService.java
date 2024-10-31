package com.oauth.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.chat.CreateChatRequestDTO;
import com.oauth.server.services.v1.interfaces.ChatRequestService;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Service
public class ChatRequesService implements ChatRequestService {

	@Autowired
	GenericMapper genericMapper;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	@Qualifier("createChatRequestQueue")
	Queue createChatRequestQueue;

	@Override
	public void createChatRequest(CreateChatRequestDTO dto) {
		rabbitTemplate.convertAndSend(createChatRequestQueue.getName(), genericMapper.convertObjectToJsonString(dto));
	}
}
