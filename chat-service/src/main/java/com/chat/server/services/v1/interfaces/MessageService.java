package com.chat.server.services.v1.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chat.server.dtos.v1.chat.ResponseMessageDTO;
import com.chat.server.infra.entities.Chat;

public interface MessageService {

	Page<ResponseMessageDTO> findByChat(Chat chat, Pageable pageable);
}
