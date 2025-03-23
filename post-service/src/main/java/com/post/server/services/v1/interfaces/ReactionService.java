package com.post.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.dtos.v1.post.ResponseReactionDTO;

public interface ReactionService {

	public void save(UUID userUUID, String profileName, ReactionRequestDTO reactionRequestDTO);
	
	Page<ResponseReactionDTO> getReactionByPostUuid(UUID postUUID, Pageable pageable);
}
