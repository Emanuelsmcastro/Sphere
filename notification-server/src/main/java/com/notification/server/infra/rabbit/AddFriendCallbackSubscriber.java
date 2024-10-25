package com.notification.server.infra.rabbit;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.notification.server.dtos.v1.notification.NotificationDTO;
import com.notification.server.dtos.v1.user.AddFriendCallbackDTO;
import com.notification.server.entities.enums.NotificationType;
import com.notification.server.mappers.v1.interfaces.GenericMapper;

@Component
public class AddFriendCallbackSubscriber {

	private static Logger logger = LoggerFactory.getLogger(AddFriendCallbackSubscriber.class);

	@Autowired
	Map<String, WebSocketSession> sessions;

	@Autowired
	GenericMapper genericMapper;

	@RabbitListener(queues = "${spring.rabbitmq.queues.add-friend-callback}")
	public void addFriendRequestCallback(String payload) {
		AddFriendCallbackDTO callbackDTO = genericMapper.convertStringJsonToObject(AddFriendCallbackDTO.class, payload);
		sendNotificationToSender(callbackDTO);
		sendNotificationToReceiver(callbackDTO);
	}

	private void sendNotificationToSender(AddFriendCallbackDTO dto) {
		WebSocketSession senderSession = sessions.get(dto.sender().uuid().toString());
		if (senderSession != null) {
			String senderNotification = genericMapper
					.convertObjectToJsonString(new NotificationDTO(NotificationType.ADDED_FRIEND, dto.receiver()));
			try {
				senderSession.sendMessage(new TextMessage(senderNotification));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private void sendNotificationToReceiver(AddFriendCallbackDTO dto) {
		WebSocketSession receiverSession = sessions.get(dto.receiver().uuid().toString());
		if (receiverSession != null) {
			String receiverNotification = genericMapper
					.convertObjectToJsonString(new NotificationDTO(NotificationType.ADDED_FRIEND, dto.sender()));
			try {
				receiverSession.sendMessage(new TextMessage(receiverNotification));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
