package com.post.server.services.v1.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.mappers.v1.interfaces.ReactionMapper;
import com.post.server.repositories.ReactionRepository;
import com.post.server.services.v1.interfaces.ReactionService;

@Service
public class ReactionServiceImpl implements ReactionService {
	
	@Autowired
	ReactionRepository reactionRep;
	
	@Autowired
	ReactionMapper reactionMapper;

	@Override
	public void save(UUID userUUID, ReactionRequestDTO reactionRequestDTO) {
		reactionRep.save(reactionMapper.toBuild(reactionRequestDTO).setUserUUID(userUUID).build());
	}

}
