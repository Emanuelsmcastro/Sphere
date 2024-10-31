package com.publisher.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.publisher.server.dto.v1.friend.FriendRequestNotification;
import com.publisher.server.services.v1.interfaces.FriendRequestService;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Service
public class FriendRequestServiceImpl implements FriendRequestService{
	
	@Autowired
	GenericMapper genericMapper;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier("friendRequestQueue")
	Queue queueFriendRequest;

	@Override
	public void friendRequestPublish(FriendRequestNotification dto) {
		rabbitTemplate.convertAndSend(queueFriendRequest.getName(), genericMapper.convertObjectToJsonString(dto));
	}
}
