package com.notification.server.infra.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
	
	@Autowired
	Map<String, WebSocketSession> sessions = new HashMap<>();
	
	@Autowired
	AuthHandshakeInterceptor authHandsInterceptor;
	
	@Autowired
	CustomWebSocketHandler customWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(customWebSocketHandler, "/ws/notification/v1").setAllowedOrigins("http://localhost:3000").addInterceptors(authHandsInterceptor);
	}
	
	
	
}
