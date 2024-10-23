package com.notification.server.infra.rabbit;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.server.dtos.v1.notification.NotificationDTO;
import com.notification.server.dtos.v1.user.AddFriendCallbackDTO;
import com.notification.server.entities.enums.NotificationType;
import com.notification.server.infra.exception.NotificationServerException;

@Component
public class AddFriendCallbackSubscriber {
	
	private static Logger logger = LoggerFactory.getLogger(AddFriendCallbackSubscriber.class);
	
	@Autowired
	Map<String, WebSocketSession> sessions;

	@RabbitListener(queues = "${spring.rabbitmq.queues.add-friend-callback}")
	public void addFriendRequestCallback(String payload) {
		AddFriendCallbackDTO callbackDTO = convertStringJsonToDTO(payload);
		WebSocketSession senderSession = sessions.get(callbackDTO.sender().uuid().toString());
		WebSocketSession receiverSession = sessions.get(callbackDTO.receiver().uuid().toString());
		if(senderSession != null) {
			String senderNotification = convertToJsonString(callbackDTO.receiver());
			try {
				senderSession.sendMessage(new TextMessage(senderNotification));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		if(receiverSession != null) {
			String receiverNotification = convertToJsonString(callbackDTO.sender());
			try {
				receiverSession.sendMessage(new TextMessage(receiverNotification));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	private AddFriendCallbackDTO convertStringJsonToDTO(String payload) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(payload, AddFriendCallbackDTO.class);
	    } catch (Exception e) {
	    	throw new NotificationServerException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	private String convertToJsonString(Object o) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper.writeValueAsString(new NotificationDTO(NotificationType.ADDED_FRIEND, o));
			return jsonString;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
