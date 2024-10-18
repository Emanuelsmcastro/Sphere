package com.notification.server.controllers.v1;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.server.clients.OauthServerClient;
import com.notification.server.dtos.v1.friend.FriendRequestNotificationDTO;
import com.notification.server.dtos.v1.friend.UpdateFriendRequestNotificationDTO;
import com.notification.server.entities.enums.FriendRequestStatus;
import com.notification.server.services.v1.interfaces.FriendRequestNotificationService;

@RestController
@RequestMapping("/notification/v1")
public class NotificationServerController {
	
	@Autowired
	OauthServerClient oauthServerClient;
	
	@Autowired
	FriendRequestNotificationService friendRequestNotificationService;
	
	@GetMapping
	public String test() {
		System.out.println(oauthServerClient.test());
		return "Notification Test";
	}
	
	@GetMapping("/get-friend-request-notifications")
	public ResponseEntity<Page<FriendRequestNotificationDTO>> getFriendRequestNotifications(Authentication authentication, Pageable pageable){
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(
					friendRequestNotificationService
					.findByReceiverAndStatus(UUID.fromString((String) profileMap.get("uuid")), FriendRequestStatus.PENDING, pageable)
				);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<Void> updateFriendRequestNotification(@RequestBody UpdateFriendRequestNotificationDTO dto){
		friendRequestNotificationService.update(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
