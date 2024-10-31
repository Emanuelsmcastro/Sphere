package com.post.server.infra.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.post.server.dtos.v1.post.RequestCreatePostDTO;
import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.mappers.v1.interfaces.PostMapper;
import com.post.server.services.v1.interfaces.PostService;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Component
public class CreatePostRequestSubscriber {
	
	private final Logger logger = LoggerFactory.getLogger(CreatePostRequestSubscriber.class);
	
	@Autowired
	PostMapper<RequestCreatePostDTO, ResponsePostDTO> postMapper;
	
	@Autowired
	GenericMapper genericMapper;
	
	@Autowired
	PostService postService;

	@RabbitListener(queues = "${spring.rabbitmq.queues.create-post-request}")
	public void createPostRequest(String payload) {
		RequestCreatePostDTO createPostDTO = genericMapper.convertStringJsonToObject(RequestCreatePostDTO.class, payload);
		logger.debug("Post Request: " + createPostDTO);
		postService.save(postMapper.toEntityPost(createPostDTO));
	}
}
