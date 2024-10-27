package com.sphere.websockets.infra.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfiguration.class);
	
	@Autowired
	WebsocketsProperties websocketsProperties;
	
	@Autowired
	Map<String, WebSocketSession> sessions = new HashMap<>();
	
	@Autowired
	AuthHandshakeInterceptor authHandsInterceptor;
	
	@Autowired
	CustomWebSocketHandler customWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		String path = websocketsProperties.getPath();
		logger.info("Registry Websocket path: " + path);
		registry.addHandler(customWebSocketHandler, path).setAllowedOrigins("http://localhost:3000").addInterceptors(authHandsInterceptor);
	}
	
	
	
}