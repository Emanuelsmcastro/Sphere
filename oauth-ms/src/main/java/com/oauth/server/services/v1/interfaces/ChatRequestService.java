package com.oauth.server.services.v1.interfaces;

import com.oauth.server.dtos.v1.chat.CreateChatRequestDTO;

public interface ChatRequestService {

	void createChatRequest(CreateChatRequestDTO dto);
}
