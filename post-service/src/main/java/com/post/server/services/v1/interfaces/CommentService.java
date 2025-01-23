package com.post.server.services.v1.interfaces;

import java.util.UUID;

import com.post.server.dtos.v1.post.CommentRequestDTO;

public interface CommentService {

	public void save(UUID userUUID, CommentRequestDTO commentRequestDTO);
}
