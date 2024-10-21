package com.chat.server.controllers.v1;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.infra.entities.enums.ChatType;
import com.chat.server.services.v1.interfaces.ChatService;

@RestController
@RequestMapping("/chat/v1")
public class ChatController {
	
	@Autowired
	ChatService chatService;

	@GetMapping("/get-chat/{friendUserProfile}")
	public ResponseEntity<ResponseChatDTO> getChatByTypeAndParticipantsUUID(
				@PathVariable(name = "friendUserProfile") UUID friendUserProfile,
				Authentication authentication
			){	
		UUID currentProfileUUID = getUserProfileUUID(authentication);
		return ResponseEntity.status(HttpStatus.OK).body(chatService.getChatByChatTypeAndParticipantsUUID(ChatType.PRIVATE_CHAT, currentProfileUUID, friendUserProfile));
	}
	
	private UUID getUserProfileUUID(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return UUID.fromString((String) profileMap.get("uuid"));

	}
}
