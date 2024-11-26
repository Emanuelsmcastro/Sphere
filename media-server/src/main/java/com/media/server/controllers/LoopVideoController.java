package com.media.server.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.media.server.dtos.v1.loop.ResponseLoopVideo;
import com.media.server.services.v1.interfaces.LoopVideoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video/v1")
public class LoopVideoController {

	@Autowired
	LoopVideoService loopVideoService;

	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping("/test")
	public String test() {
		return "ok";
	}

	@GetMapping("/search/{loopUUID}/{fileName}")
	public Mono<ResponseEntity<byte[]>> getVideo(@PathVariable String fileName, @PathVariable UUID loopUUID) {
		return Mono.fromSupplier(() -> loopVideoService.getVideoByFileName(loopUUID, fileName));
	}

	@PostMapping(value = "/upload-stream")
	public Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono,
			Authentication authentication) {
		Map<String, Object> profileMap = getUserProfile(authentication);
		UUID uuid = UUID.fromString((String) profileMap.get("uuid"));
		String profileName = (String) profileMap.get("name");
		return loopVideoService.uploadFile(filePartMono, uuid, profileName);
	}

	@GetMapping("/get-friend-loops")
	public Mono<Page<ResponseLoopVideo>> getFriendLoops(@RequestHeader(value = "Authorization", required = true) String token,
			@RequestHeader(value = "Cookie", required = true) String cookie, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, Authentication authentication) {
		Map<String, Object> profileMap = getUserProfile(authentication);
		UUID uuid = UUID.fromString((String) profileMap.get("uuid"));
		return loopVideoService.getFriendLoopVideos(uuid, PageRequest.of(page, size), token, cookie);
	}
	
	@GetMapping("/get-all-videos-by-creator-uuid/{creatorUUID}")
	public Flux<ResponseLoopVideo> getAllLoopVideosByCreatorUUID(@PathVariable UUID creatorUUID){
		return loopVideoService.getAllLoopVideosByCreatorUUID(creatorUUID);
	}
	
	private Map<String, Object> getUserProfile(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return profileMap;
	}
}
