package com.notification.server.mappers.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.mappers.v1.interfaces.FriendRequestMapper;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Component
public class FriendRequestMapperImp implements FriendRequestMapper {

	@Autowired
	GenericMapper genericMapper;

	@Override
	public FriendRequestNotification convertStringJsonToObject(String json) {
		FriendRequestNotificationDTO dto = genericMapper.convertStringJsonToObject(FriendRequestNotificationDTO.class,
				json);
		return FriendRequestNotification.Builder.of().setSender(dto.sender()).setSenderName(dto.senderName())
				.setReceiver(dto.receiver()).build();
	}

	@Override
	public FriendRequestNotificationDTO toDTO(FriendRequestNotification entity) {
		return new FriendRequestNotificationDTO(entity.getUuid(), entity.getSender(), entity.getSenderName(),
				entity.getReceiver());
	}

	@Override
	public Page<FriendRequestNotificationDTO> toDTO(Page<FriendRequestNotification> page) {
		return page.map(this::toDTO);
	}
}
