package com.post.server.mappers.v1.interfaces;

import org.springframework.data.domain.Page;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.dtos.v1.post.ResponseCommentDTO;
import com.post.server.entities.Comment;

public interface CommentMapper {

	Comment.Build toBuild(CommentRequestDTO commentRequestDTO);
	
	ResponseCommentDTO toDTO(Comment comment);
	
	Page<ResponseCommentDTO> toDTO(Page<Comment> commentPageable);
}
