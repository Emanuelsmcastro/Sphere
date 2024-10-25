package com.post.server.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.post.server.dtos.v1.profile.ResponseProfileDTO;

@Component
@FeignClient(contextId = "OauthServerClient", name = "oauth-ms", path = "oauth/v1/private")
public interface OauthServerClient {

	@GetMapping("/get-all-friends")
	public ResponseEntity<List<ResponseProfileDTO>> getAllFriends();
}
