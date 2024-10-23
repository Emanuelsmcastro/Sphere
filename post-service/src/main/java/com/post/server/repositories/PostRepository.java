package com.post.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post.server.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
