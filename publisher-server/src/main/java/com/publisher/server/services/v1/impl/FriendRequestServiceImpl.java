package com.publisher.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publisher.server.dto.v1.friend.FriendRequestNotification;
import com.publisher.server.infra.exceptions.JsonConvertException;
import com.publisher.server.services.v1.interfaces.FriendRequestService;

@Service
public class FriendRequestServiceImpl implements FriendRequestService{
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	Queue queueFriendRequest;

	@Override
	public void friendRequestPublish(FriendRequestNotification dto) {
		rabbitTemplate.convertAndSend(queueFriendRequest.getName(), convertToJson(dto));
	}
	
	private String convertToJson(Object o){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new JsonConvertException("Error converting Object to Json.");
		}
	}
}
