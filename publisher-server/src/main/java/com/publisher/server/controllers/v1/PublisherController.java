package com.publisher.server.controllers.v1;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
	public ResponseEntity<Void> friendRequest(@RequestBody FriendRequestNotification dto, Authentication authentication){
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
        UUID sender = UUID.fromString((String) profileMap.get("uuid"));
        FriendRequestNotification newDTO = new FriendRequestNotification(sender, (String) profileMap.get("name"), dto.receiver());
		friendRequestService.friendRequestPublish(newDTO);
		return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
	}
}
