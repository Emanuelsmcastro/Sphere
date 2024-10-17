package com.notification.server.mappers.v1.impl;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.infra.exception.FriendRequestException;
import com.notification.server.mappers.v1.interfaces.FriendRequestMapper;

@Component
public class FriendRequestMapperImp implements FriendRequestMapper{

	@Override
	public FriendRequestNotification convertStringJsonToObject(String json) {
		FriendRequestNotificationDTO dto = convertStringJsonToDTO(json);
		return FriendRequestNotification
				.Builder.of()
				.setSender(dto.sender())
				.setReceiver(dto.receiver())
				.build();
	}

	
	private FriendRequestNotificationDTO convertStringJsonToDTO(String json) {
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(json, FriendRequestNotificationDTO.class);
	    } catch (Exception e) {
	    	throw new FriendRequestException(e.getMessage());
	    }
	}
}
