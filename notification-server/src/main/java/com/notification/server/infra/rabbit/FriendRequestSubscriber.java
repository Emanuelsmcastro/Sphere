package com.notification.server.infra.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notification.server.services.v1.interfaces.FriendRequestNotificationService;

@Component
public class FriendRequestSubscriber {
	
	private static Logger logger = LoggerFactory.getLogger(FriendRequestSubscriber.class);
	
	@Autowired
	FriendRequestNotificationService service;

	@RabbitListener(queues = "${spring.rabbitmq.queues.friend-request}")
	public void ReceiveFriendRequest(String payload) {
		saveFriendRequestNotification(payload);
	}
	
	private void saveFriendRequestNotification(String payload) {
		try {			
			service.saveJson(payload);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
	}
}
