package com.chat.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.server.infra.entities.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>{

}
