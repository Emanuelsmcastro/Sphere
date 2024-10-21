package com.oauth.server.controllers.v1;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.server.dtos.v1.user.RequestAddFriendDTO;
import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.services.v1.interfaces.ProfileService;

@RestController
@RequestMapping("/oauth/v1/private")
public class PrivateOauthController {

	@Autowired
	ProfileService profileService;

	@GetMapping
	public String test() {
		return "ok";
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<Page<ResponseProfileDTO>> seatchUsers(@PathVariable String name, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfiles(name, pageable));
	}

	@PostMapping("/add-friend")
	public ResponseEntity<Void> addFriend(@RequestBody RequestAddFriendDTO dto, Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		UUID sender = UUID.fromString((String) profileMap.get("uuid"));
		System.out.println(sender);
		profileService.addFriend(dto, sender);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();

	}

}
