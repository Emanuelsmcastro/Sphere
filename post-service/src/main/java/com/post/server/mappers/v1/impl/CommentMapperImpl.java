package com.post.server.mappers.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.dtos.v1.post.ResponseCommentDTO;
import com.post.server.entities.Comment;
import com.post.server.entities.Comment.Build;
import com.post.server.entities.Post;
import com.post.server.mappers.v1.interfaces.CommentMapper;
import com.post.server.services.v1.interfaces.PostService;

@Component
public class CommentMapperImpl implements CommentMapper {

	@Autowired
	PostService postService;

	@Override
	public Build toBuild(CommentRequestDTO commentRequestDTO) {
		Post post = postService.findByUuid(commentRequestDTO.postUUID());
		return Comment.Build.of(post).setContent(commentRequestDTO.content());
	}

	@Override
	public ResponseCommentDTO toDTO(Comment comment) {
		return new ResponseCommentDTO(comment.getCreatorUUID(), comment.getProfileName(), comment.getContent(),
				comment.getCreatedAt());
	}

	@Override
	public Page<ResponseCommentDTO> toDTO(Page<Comment> commentPageable) {
		return commentPageable.map(this::toDTO);
	}

}
