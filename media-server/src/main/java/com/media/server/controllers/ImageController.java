package com.media.server.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.media.server.services.v1.interfaces.ProfileImageService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/image/v1")
public class ImageController {

	@Autowired
	ProfileImageService profileImageService;
	
	@GetMapping("/get-profile-image/{profileUUID}")
	public Mono<ResponseEntity<Resource>> getProfileImage(@PathVariable UUID profileUUID){
		return profileImageService.getProfileImage(profileUUID);
	}

	@PostMapping(value = "/upload-stream")
	public Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono,
			Authentication authentication) {
		Map<String, Object> profileMap = getUserProfile(authentication);
		UUID uuid = UUID.fromString((String) profileMap.get("uuid"));
		String profileName = (String) profileMap.get("name");
		return profileImageService.uploadFile(filePartMono, uuid, profileName);
	}

	private Map<String, Object> getUserProfile(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return profileMap;
	}
}
