package com.publisher.server.services.v1.interfaces;

import com.publisher.server.dto.v1.post.RequestCreatePostDTO;

public interface PostRequestService {

	void createPostRequest(RequestCreatePostDTO dto);
}
