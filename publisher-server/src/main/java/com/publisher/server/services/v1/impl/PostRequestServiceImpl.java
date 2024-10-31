package com.publisher.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.publisher.server.dto.v1.post.RequestCreatePostDTO;
import com.publisher.server.services.v1.interfaces.PostRequestService;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Service
public class PostRequestServiceImpl implements PostRequestService{
	
	@Autowired
	GenericMapper genericMapper;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier("createPostRequestQueue")
	Queue reatePostRequestQueue;

	@Override
	public void createPostRequest(RequestCreatePostDTO dto) {
		rabbitTemplate.convertAndSend(reatePostRequestQueue.getName(), genericMapper.convertObjectToJsonString(dto));
	}

}
