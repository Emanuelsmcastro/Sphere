package com.chat.server.controllers.v1;

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

import com.chat.server.dtos.v1.chat.MessageRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.enums.ChatType;
import com.chat.server.services.v1.interfaces.ChatService;

@RestController
@RequestMapping("/chat/v1")
public class ChatController {

	@Autowired
	ChatService chatService;

	@GetMapping("/get-chat/{friendUserProfile}")
	public ResponseEntity<ResponseChatDTO> getChatByTypeAndParticipantsUUID(
			@PathVariable(name = "friendUserProfile") UUID friendUserProfile, Authentication authentication) {
		UUID currentProfileUUID = getUserProfileUUID(authentication);
		return ResponseEntity.status(HttpStatus.OK).body(chatService
				.getChatByChatTypeAndParticipantsUUID(ChatType.PRIVATE_CHAT, currentProfileUUID, friendUserProfile));
	}

	@GetMapping("/get-chat-messages/{chatUUID}")
	public ResponseEntity<Page<ResponseMessageDTO>> getChatMessages(@PathVariable(name = "chatUUID") UUID chatUUID,
			Authentication authentication, Pageable pageable) {
		UUID currentProfileUUID = getUserProfileUUID(authentication);
		return ResponseEntity.status(HttpStatus.OK)
				.body(chatService.findMessagesByChat(chatUUID, currentProfileUUID, pageable));
	}

	@PostMapping("/send-message")
	public ResponseEntity<Void> sendMessage(@RequestBody MessageRequestDTO dto, Authentication authentication) {
		Map<String, Object> profileMap = getUserProfile(authentication);
		MessageRequestDTO newDTO = new MessageRequestDTO(dto.chatUUID(), getUserProfileUUID(authentication),
				(String) profileMap.get("name"), dto.message());
		chatService.addMessage(newDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	private UUID getUserProfileUUID(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return UUID.fromString((String) profileMap.get("uuid"));

	}

	private Map<String, Object> getUserProfile(Authentication authentication) {
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
		Map<String, Object> profileMap = jwtToken.getToken().getClaimAsMap("profile");
		return profileMap;
	}
}
