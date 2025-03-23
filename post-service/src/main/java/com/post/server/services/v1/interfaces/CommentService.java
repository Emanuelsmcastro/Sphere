package com.post.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.dtos.v1.post.ResponseCommentDTO;

public interface CommentService {

	public void save(UUID userUUID, String profileName, CommentRequestDTO commentRequestDTO);
	
	Page<ResponseCommentDTO> getCommentByPostUUID(UUID postUUID, Pageable pageable);
}
