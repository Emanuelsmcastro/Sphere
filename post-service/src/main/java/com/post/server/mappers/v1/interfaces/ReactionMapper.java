package com.post.server.mappers.v1.interfaces;

import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.entities.Reaction;

public interface ReactionMapper {
	
	Reaction.Build toBuild(ReactionRequestDTO reactionRequestDTO);

}
