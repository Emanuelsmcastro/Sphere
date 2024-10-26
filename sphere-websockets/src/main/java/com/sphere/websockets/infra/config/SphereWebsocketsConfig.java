package com.sphere.websockets.infra.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

@Configuration
@ComponentScan(basePackages = "com.sphere.websockets")
public class SphereWebsocketsConfig {

	private final Map<String, WebSocketSession> sessions = new HashMap<>();

	@Bean
	Map<String, WebSocketSession> getSessions() {
		return sessions;
	}

}
