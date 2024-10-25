package com.post.server.infra.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.post.server.dtos.v1.post.RequestCreatePostDTO;
import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.mappers.v1.interfaces.GenericMapper;
import com.post.server.mappers.v1.interfaces.PostMapper;
import com.post.server.services.v1.interfaces.PostService;

@Component
public class CreatePostRequestSubscriber {
	
	@Autowired
	PostMapper<RequestCreatePostDTO, ResponsePostDTO> postMapper;
	
	@Autowired
	GenericMapper genericMapper;
	
	@Autowired
	PostService postService;

	@RabbitListener(queues = "${spring.rabbitmq.queues.create-post-request}")
	public void createPostRequest(String payload) {
		RequestCreatePostDTO createPostDTO = genericMapper.convertStringJsonToObject(RequestCreatePostDTO.class, payload);
		System.out.println(createPostDTO);
		postService.save(postMapper.toEntityPost(createPostDTO));
	}
}
