package com.post.server.services.v1.interfaces;

import java.util.UUID;

import com.post.server.dtos.v1.post.ReactionRequestDTO;

public interface ReactionService {

	public void save(UUID userUUID, ReactionRequestDTO reactionRequestDTO);
}
