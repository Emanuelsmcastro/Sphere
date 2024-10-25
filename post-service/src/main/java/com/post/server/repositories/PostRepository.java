package com.post.server.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post.server.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("SELECT p FROM Post p WHERE p.creator IN :creators")
    public Page<Post> findPostsByCreators(@Param("creators") List<UUID> creators, Pageable pageable);
}
