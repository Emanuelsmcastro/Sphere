package com.chat.server.infra.websocket;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.chat.server.infra.entities.JwtAuthentication;

@Component
public class CustomWebSocketHandler extends AbstractWebSocketHandler {

	@Autowired
	Map<String, WebSocketSession> sessions;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
		JwtAuthentication principal = (JwtAuthentication) session.getPrincipal();
		String uuid = getProfileUUID(principal);
		if (principal == null || uuid == null) {
			session.close(SERVER_ERROR);
			return;
		}
		sessions.put(uuid, session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		JwtAuthentication principal = (JwtAuthentication) session.getPrincipal();
		String uuid = getProfileUUID(principal);
		sessions.remove(uuid);
	}
	
	private String getProfileUUID(JwtAuthentication principal) {
		Map<String, Object> profileClaims = principal.getDetails().getClaimAsMap("profile");
		String uuid = (String) profileClaims.get("uuid");
		return uuid;
	}

}