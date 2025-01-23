package com.post.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.post.server.dtos.v1.post.CommentRequestDTO;
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
	public void save(UUID userUUID, CommentRequestDTO commentRequestDTO) {
		commentRep.save(commentMapper.toBuild(commentRequestDTO).setCreatorUUID(userUUID).build());
	}

}
