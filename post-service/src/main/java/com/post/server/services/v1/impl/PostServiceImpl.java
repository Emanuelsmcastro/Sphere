package com.post.server.services.v1.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.post.server.clients.OauthServerClient;
import com.post.server.dtos.v1.post.RequestCreatePostDTO;
import com.post.server.dtos.v1.post.ResponsePostDTO;
import com.post.server.dtos.v1.profile.ResponseProfileDTO;
import com.post.server.entities.Post;
import com.post.server.infra.exceptions.PostServiceException;
import com.post.server.mappers.v1.interfaces.PostMapper;
import com.post.server.repositories.MetaInfRepository;
import com.post.server.repositories.PostRepository;
import com.post.server.services.v1.interfaces.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostMapper<RequestCreatePostDTO, ResponsePostDTO> postMapper;

	@Autowired
	MetaInfRepository metaInfRep;

	@Autowired
	OauthServerClient oauthServicerClient;

	@Transactional
	@Override
	public void save(Post post) {
		metaInfRep.save(post.getMetaInf());
		postRepository.save(post);
	}

	@Override
	public Page<ResponsePostDTO> getAllFriendPosts(UUID profileUUID, Pageable pageable) {
		List<ResponseProfileDTO> responseProfileDTOList = oauthServicerClient.getAllFriends().getBody();
		List<UUID> friendList = responseProfileDTOList.stream().map(ResponseProfileDTO::uuid)
				.collect(Collectors.toList());

		Page<Post> postPage = postRepository.findPostsByCreators(friendList, pageable);

		return shufflePage(postMapper.toDTO(postPage));
	}

	public Page<ResponsePostDTO> shufflePage(Page<ResponsePostDTO> responsePostDTOPageable) {
		List<ResponsePostDTO> postList = new ArrayList<>(responsePostDTOPageable.getContent());

		Collections.shuffle(postList);

		return new PageImpl<>(postList,
				PageRequest.of(responsePostDTOPageable.getNumber(), responsePostDTOPageable.getSize()),
				responsePostDTOPageable.getTotalElements());
	}

	@Override
	public Post findByUuid(UUID uuid) {
		return postRepository.findByUuid(uuid)
				.orElseThrow(() -> new PostServiceException("Post not find.", HttpStatus.BAD_REQUEST));
	}

}
