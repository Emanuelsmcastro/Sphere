package com.chat.server.infra.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.server.services.v1.interfaces.ChatService;

@Component
public class CreateChatRequestSubscriber {
	
	@Autowired
	ChatService service;

	@RabbitListener(queues = "${spring.rabbitmq.queues.create-chat-request}")
	public void receiverCreateChatRequest(String payload) {
		service.saveStringJson(payload);
	}
}
