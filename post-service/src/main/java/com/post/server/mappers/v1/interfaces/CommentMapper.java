package com.post.server.mappers.v1.interfaces;

import com.post.server.dtos.v1.post.CommentRequestDTO;
import com.post.server.entities.Comment;

public interface CommentMapper {

	Comment.Build toBuild(CommentRequestDTO commentRequestDTO);
}
