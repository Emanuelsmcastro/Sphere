package com.notification.server.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.notification.server.dtos.v1.friend.RequestAddFriendDTO;

@Component
@FeignClient(contextId = "OauthServerClient", name = "oauth-ms", path = "oauth/v1/private")
public interface OauthServerClient {

	@GetMapping
	String test();
	
	@PostMapping("/add-friend")
	ResponseEntity<Void> addFriend(@RequestBody RequestAddFriendDTO dto);
}
