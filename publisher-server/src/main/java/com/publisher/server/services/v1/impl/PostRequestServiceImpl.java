package com.publisher.server.services.v1.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publisher.server.dto.v1.post.RequestCreatePostDTO;
import com.publisher.server.infra.exceptions.JsonConvertException;
import com.publisher.server.services.v1.interfaces.PostRequestService;

@Service
public class PostRequestServiceImpl implements PostRequestService{
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	@Qualifier("createPostRequestQueue")
	Queue reatePostRequestQueue;

	@Override
	public void createPostRequest(RequestCreatePostDTO dto) {
		rabbitTemplate.convertAndSend(reatePostRequestQueue.getName(), convertToJson(dto));
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
