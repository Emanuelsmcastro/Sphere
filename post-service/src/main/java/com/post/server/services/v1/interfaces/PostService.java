package com.post.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.post.server.dtos.v1.post.ResponsePostWithFullProfileInformationDTO;
import com.post.server.entities.Post;

public interface PostService {

	public void save(Post post);
	
	Page<ResponsePostWithFullProfileInformationDTO> getAllFriendPosts(UUID profileUUID, Pageable pageable);
}
