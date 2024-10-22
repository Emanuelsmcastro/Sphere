package com.chat.server.infra.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class GlobalConfiguration {

private final Map<String, WebSocketSession> sessions = new HashMap<>();
	
	@Bean
	Map<String, WebSocketSession> getSessions(){
		return sessions;
	}
}
