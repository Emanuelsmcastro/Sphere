package com.post.server.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post.server.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	@Query("SELECT q FROM Comment q WHERE q.post.uuid = :postUUID")
	Page<Comment> findByPostUuid(@Param("postUUID") UUID postUUID, Pageable pageable);
}
