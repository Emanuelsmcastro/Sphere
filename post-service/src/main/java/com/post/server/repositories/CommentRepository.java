package com.post.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post.server.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
