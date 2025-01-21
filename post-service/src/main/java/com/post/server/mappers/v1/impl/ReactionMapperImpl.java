package com.post.server.mappers.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.post.server.dtos.v1.post.ReactionRequestDTO;
import com.post.server.entities.Post;
import com.post.server.entities.Reaction;
import com.post.server.entities.Reaction.Build;
import com.post.server.mappers.v1.interfaces.ReactionMapper;
import com.post.server.services.v1.interfaces.PostService;

@Component
public class ReactionMapperImpl implements ReactionMapper {

	@Autowired
	PostService postService;

	@Override
	public Build toBuild(ReactionRequestDTO reactionRequestDTO) {
		Post post = postService.findByUuid(reactionRequestDTO.postUUID());
		return Reaction.Build.of().setPost(post).setReactionType(reactionRequestDTO.reactionType());
	}

}
