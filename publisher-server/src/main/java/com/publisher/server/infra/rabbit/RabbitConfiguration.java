package com.publisher.server.infra.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${spring.rabbitmq.queues.friend-request}")
	String friendRequestQueue;
	
	@Value("${spring.rabbitmq.queues.create-post-request}")
	String createPostRequest;
	
	@Bean
	@Qualifier("friendRequestQueue")
	Queue friendRequestQueue() {
		return new Queue(friendRequestQueue, true);
	}
	
	@Bean
	@Qualifier("createPostRequestQueue")
	Queue createPostRequest() {
		return new Queue(createPostRequest, true);
	}
}
