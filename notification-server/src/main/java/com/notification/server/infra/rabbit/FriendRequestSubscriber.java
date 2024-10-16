package com.notification.server.infra.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestSubscriber {

	@RabbitListener(queues = "${spring.rabbitmq.queues.friend-request}")
	public void ReceiveFriendRequest(String payload) {
		System.out.println(payload);
	}
}
