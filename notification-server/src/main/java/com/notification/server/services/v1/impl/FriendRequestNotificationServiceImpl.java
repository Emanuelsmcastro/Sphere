package com.notification.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.dtos.v1.friend.UpdateFriendRequestNotificationDTO;
import com.notification.server.entities.FriendRequestNotification;
import com.notification.server.entities.enums.FriendRequestStatus;
import com.notification.server.infra.exception.FriendRequestRepException;
import com.notification.server.mappers.v1.interfaces.FriendRequestMapper;
import com.notification.server.repositories.FriendRequestRepository;
import com.notification.server.services.v1.interfaces.FriendRequestNotificationService;

@Service
public class FriendRequestNotificationServiceImpl implements FriendRequestNotificationService{
	
	@Autowired
	FriendRequestRepository rep;
	
	@Autowired
	FriendRequestMapper mapper;

	@Override
	public void saveJson(String json) {
		save(convertStringJsonToObject(json));
	}
	
	@Override
	public void save(FriendRequestNotification friendRequestNotification) throws FriendRequestRepException{
		try {			
			rep.save(friendRequestNotification);
		} catch(DataIntegrityViolationException ex) {
			throw new FriendRequestRepException("Friend Request Notification cannot be saved.");
		}
	}

	@Override
	public FriendRequestNotification findBySenderAndReceiver(UUID sender, UUID receiver) {
		return rep.findBySenderAndReceiver(sender, receiver).orElseThrow(() -> new FriendRequestRepException("Friend Request Notification not found."));
	}

	@Override
	public FriendRequestNotification convertStringJsonToObject(String json) {
		return mapper.convertStringJsonToObject(json);
	}

	@Override
	public Page<FriendRequestNotificationDTO> findByReceiver(UUID receiver, Pageable pageable) {
		return mapper.toDTO(rep.findByReceiver(receiver, pageable));
	}

	@Override
	public void update(UpdateFriendRequestNotificationDTO dto) {
		FriendRequestNotification friendRequest = findByUuid(dto.uuid());
		friendRequest.setStatus(dto.status());
		rep.save(friendRequest);
	}
	
	private FriendRequestNotification findByUuid(UUID uuid) {
		return rep.findByUuid(uuid).orElseThrow(() -> new FriendRequestRepException("Friend Request not exists."));
	}

	@Override
	public Page<FriendRequestNotificationDTO> findByReceiverAndStatus(UUID receiver, FriendRequestStatus status,
			Pageable pageable) {
		return mapper.toDTO(rep.findByReceiverAndStatus(receiver, status, pageable));
	}


}
