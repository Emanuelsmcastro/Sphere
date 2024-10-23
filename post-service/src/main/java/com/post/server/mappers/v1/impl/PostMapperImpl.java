package com.post.server.mappers.v1.impl;

import org.springframework.stereotype.Component;

import com.post.server.dtos.v1.post.MetaInfDTO;
import com.post.server.dtos.v1.post.RequestCreatePostDTO;
import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.entities.Post;
import com.post.server.mappers.v1.interfaces.PostMapper;

@Component
public class PostMapperImpl implements PostMapper<RequestCreatePostDTO, ResponsePostDTO> {

	@Override
	public Post toEntityPost(RequestCreatePostDTO dto) {
		return Post.Builder.of(dto.creator(), dto.description()).addMetaInfHashtags(dto.metaInf().hashtags()).build();
	}

	@Override
	public ResponsePostDTO toDTO(Post post) {
		return new ResponsePostDTO(post.getUuid(), post.getCreator(), post.getDescription(),
				new MetaInfDTO(post.getMetaInf().getHashtags()));
	}

}
