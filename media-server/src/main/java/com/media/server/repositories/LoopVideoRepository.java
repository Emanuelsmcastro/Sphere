package com.media.server.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.media.server.entities.LoopVideo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LoopVideoRepository extends R2dbcRepository<LoopVideo, Long>{
	
	@Query("SELECT * FROM loop_video_db lv " +
		   "WHERE lv.id IN (SELECT MIN(id) " +
		   "FROM loop_video_db " +
		   "WHERE creator_uuid IN (:uuids) " +
		   "GROUP BY creator_uuid)")
	Flux<LoopVideo> findByCreatorUUIDIn(List<UUID> uuids, Pageable pageable);
	
	@Query("SELECT * FROM loop_video_db " + 
	       "WHERE creator_uuid = :creatorUUID")
	Flux<LoopVideo> findAllByCreatorUUID(UUID creatorUUID);
	
	@Query("SELECT COUNT(*) FROM loop_video_db WHERE creator_uuid IN (:uuids)") 
	Mono<Long> countByCreatorUUIDIn(List<UUID> uuids);
}
