package com.post.server.mappers.v1.interfaces;

import com.post.server.entities.Post;

public interface PostMapper<T, B> {

	Post toEntityPost(T dto);
	
	B toDTO(Post post);
}
