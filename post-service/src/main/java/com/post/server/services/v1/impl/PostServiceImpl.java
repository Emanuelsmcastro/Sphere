package com.post.server.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.post.server.entities.Post;
import com.post.server.repositories.MetaInfRepository;
import com.post.server.repositories.PostRepository;
import com.post.server.services.v1.interfaces.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	MetaInfRepository metaInfRep;

	@Transactional
	@Override
	public void save(Post post) {
		metaInfRep.save(post.getMetaInf());
		postRepository.save(post);
	}

}
