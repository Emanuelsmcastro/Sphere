package com.post.server.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.post.server.entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

	@Query("SELECT r FROM Reaction r WHERE r.post.uuid = :postUUID")
	public Page<Reaction> findByPostUuid(@Param("postUUID") UUID postUUID, Pageable pageable);
}
