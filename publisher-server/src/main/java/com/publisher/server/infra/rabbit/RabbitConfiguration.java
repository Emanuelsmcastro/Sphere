package com.publisher.server.infra.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${spring.rabbitmq.queues.friend-request}")
	String friendRequestQueue;
	
	@Bean
	Queue queueConfig() {
		return new Queue(friendRequestQueue, true);
	}
}
