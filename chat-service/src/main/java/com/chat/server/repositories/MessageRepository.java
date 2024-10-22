package com.chat.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.server.infra.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
