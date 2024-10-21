package com.chat.server.services.v1.interfaces;

import com.chat.server.infra.entities.Chat;

public interface ChatService {

	void save(Chat chat);
	
	void saveStringJson(String payload);
}
