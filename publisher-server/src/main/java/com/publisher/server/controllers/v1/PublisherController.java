package com.publisher.server.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.publisher.server.dto.v1.friend.FriendRequestNotification;
import com.publisher.server.services.v1.interfaces.FriendRequestService;

@RestController
@RequestMapping("/publisher/v1")
public class PublisherController {
	
	@Autowired
	FriendRequestService friendRequestService;
	
	@PostMapping("/friend-request")
	public ResponseEntity<Void> friendRequest(@RequestBody FriendRequestNotification dto){
		friendRequestService.friendRequestPublish(dto);
		return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
	}
}
