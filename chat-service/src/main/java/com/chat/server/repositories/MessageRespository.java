package com.chat.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.server.infra.entities.Message;

public interface MessageRespository extends JpaRepository<Message, Long>{

}
