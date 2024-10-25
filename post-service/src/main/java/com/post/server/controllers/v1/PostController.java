package com.post.server.controllers.v1;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.services.v1.interfaces.PostService;

@RestController
@RequestMapping("/post/v1")
public class PostController {
	
	@Autowired
	PostService postService;

	@GetMapping("/get-friend-posts")
	public ResponseEntity<Page<ResponsePostDTO>> getFriendPosts(Authentication authentication, Pageable pageable){
		UUID profileUUID = getUserProfileUUID(authentication);
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllFriendPosts(profileUUID, pageable));
	}
	
	private UUID getUserProfileUUID(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return UUID.fromString((String) profileMap.get("uuid"));

	}
}
