package com.oauth.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.server.dtos.v1.chat.CreateChatRequestDTO;
import com.oauth.server.infra.exceptions.OauthServerException;
import com.oauth.server.services.v1.interfaces.ChatRequestService;

@Service
public class ChatRequesService implements ChatRequestService {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	@Qualifier("createChatRequestQueue")
	Queue createChatRequestQueue;

	@Override
	public void createChatRequest(CreateChatRequestDTO dto) {
		rabbitTemplate.convertAndSend(createChatRequestQueue.getName(), this.convertToJson(dto));
	}

	private String convertToJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new OauthServerException("Error in Json Converter.");
		}
	}
}
