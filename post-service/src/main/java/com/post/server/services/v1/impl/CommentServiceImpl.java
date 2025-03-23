package com.post.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.dtos.v1.post.ResponseCommentDTO;
import com.post.server.mappers.v1.interfaces.CommentMapper;
import com.post.server.repositories.CommentRepository;
import com.post.server.services.v1.interfaces.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentMapper commentMapper;

	@Autowired
	CommentRepository commentRep;

	@Override
	public void save(UUID userUUID, String profileName, CommentRequestDTO commentRequestDTO) {
		commentRep.save(
				commentMapper.toBuild(commentRequestDTO).setCreatorUUID(userUUID).setProfileName(profileName).build());
	}

	@Override
	public Page<ResponseCommentDTO> getCommentByPostUUID(UUID postUUID, Pageable pageable) {
		return commentMapper.toDTO(commentRep.findByPostUuid(postUUID, pageable));
	}

}
