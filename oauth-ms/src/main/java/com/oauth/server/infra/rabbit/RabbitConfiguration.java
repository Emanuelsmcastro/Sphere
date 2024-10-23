package com.oauth.server.infra.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Value("${spring.rabbitmq.queues.create-chat-request}")
	String createChatRequestQueue;

	@Value("${spring.rabbitmq.queues.add-friend-callback}")
	String addFriendCallback;

	@Bean
	@Qualifier("createChatRequestQueue")
	Queue createChatRequestQueue() {
		return new Queue(createChatRequestQueue, true);
	}

	@Bean
	@Qualifier("addFriendCallbackQueue")
	Queue addFriendCallbackQueue() {
		return new Queue(addFriendCallback, true);
	}
}
