package com.oauth.server.infra.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${spring.rabbitmq.queues.create-chat-request}")
	String createChatRequestQueue;

	@Bean
	Queue queueConfig() {
		return new Queue(createChatRequestQueue, true);
	}
}
