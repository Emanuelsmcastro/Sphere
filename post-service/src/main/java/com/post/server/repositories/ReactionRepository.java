package com.post.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.post.server.entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long>{

}
