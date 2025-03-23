package com.post.server.mappers.v1.interfaces;

import org.springframework.data.domain.Page;

import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.dtos.v1.post.ResponseReactionDTO;
import com.post.server.entities.Reaction;

public interface ReactionMapper {
	
	Reaction.Build toBuild(ReactionRequestDTO reactionRequestDTO);
	
	ResponseReactionDTO toDTO(Reaction reaction);
	
	Page<ResponseReactionDTO> toDTO(Page<Reaction> reactionPageable);
}
