package com.chat.server.services.v1.impl;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.chat.server.dtos.v1.chat.MessageRequestDTO;
import com.chat.server.dtos.v1.chat.ResponseChatDTO;
import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.Message;
import com.chat.server.infra.entities.enums.ChatType;
import com.chat.server.infra.exceptions.ChatException;
import com.chat.server.mappers.v1.interfaces.ChatMapper;
import com.chat.server.mappers.v1.interfaces.MessageMapper;
import com.chat.server.repositories.ChatRepository;
import com.chat.server.repositories.MessageRepository;
import com.chat.server.services.v1.interfaces.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatMapper chatMapper;
	
	@Autowired
	MessageMapper messageMapper;

	@Autowired
	ChatRepository chatRep;
	
	@Autowired
	MessageRepository messageRep;
	
	@Autowired
	Map<String, WebSocketSession> sessions;

	@Override
	public void save(Chat chat) {
		chatRep.save(chat);
	}

	@Override
	public void saveStringJson(String payload) {
		save(chatMapper.toEntity(chatMapper.convertStringJsonToDTO(payload)));
	}

	@Override
	public ResponseChatDTO getChatByChatTypeAndParticipantsUUID(ChatType type, UUID userUUID1, UUID userUUID2) {
		return chatMapper.toDTO(chatRep
								    .getChatByChatTypeAndParticipantsUUID(type, userUUID1, userUUID2)
									.orElseThrow(() -> new ChatException("Chat not found", HttpStatus.BAD_REQUEST)));
	}

	@Override
	public void addMessage(MessageRequestDTO dto) {
		Chat chat = findByUuidAndSenderUuid(dto.chatUUID(), dto.sender());
		Message message = Message
				.Builder
				.of(dto.sender(), dto.message())
				.setChat(chat)
				.setSenderName(dto.senderName())
				.build();
		sendMessageToReceiver(messageRep.save(message));
		
	}

	@Override
	public Chat findByUuid(UUID uuid) {
		return chatRep.findByUuid(uuid).orElseThrow(() -> new ChatException("Chat not found.", HttpStatus.BAD_REQUEST));
	}
	
	@Override
	public Chat findByUuidAndSenderUuid(UUID chatUuid, UUID senderUuid) {
		return chatRep.findByUuidAndSenderUuid(chatUuid, senderUuid).orElseThrow(() -> new ChatException("Chat not found.", HttpStatus.BAD_REQUEST));
	}
	
	private void sendMessageToReceiver(Message message) {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(message);
		ResponseMessageDTO response = messageMapper.toDTO(message);
		System.out.println(response);
		Set<UUID> participantsToReceive = message
				.getChat()
				.getParticipantsUUID()
				.stream()
				.filter(participant -> !participant.equals(message.getSenderUUID()))
				.collect(Collectors.toSet());
		participantsToReceive.forEach(participant -> {
			WebSocketSession session = sessions.get(participant.toString());
			if(session != null) {
				try {
					String jsonString = objectMapper.writeValueAsString(response);
					System.out.println(jsonString);
					session.sendMessage(new TextMessage(jsonString));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
