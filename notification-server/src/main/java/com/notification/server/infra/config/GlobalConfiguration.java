package com.notification.server.infra.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.socket.WebSocketSession;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class GlobalConfiguration {
	
	private final Map<String, WebSocketSession> sessions = new HashMap<>();
	
	@Bean
	Map<String, WebSocketSession> getSessions(){
		return sessions;
	}
	
	
}

