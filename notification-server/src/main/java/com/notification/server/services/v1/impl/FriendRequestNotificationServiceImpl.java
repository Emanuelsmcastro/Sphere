package com.notification.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.notification.server.entities.FriendRequestNotification;
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


}