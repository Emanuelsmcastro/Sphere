package com.chat.server.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	Page<Message> findByChat(Chat chat, Pageable pegeable);
}
