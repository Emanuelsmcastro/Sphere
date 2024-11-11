package com.video.server.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.video.server.entities.LoopVideo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LoopVideoRepository extends R2dbcRepository<LoopVideo, Long>{
	
	@Query("SELECT * FROM loop_video_db WHERE creator_uuid IN (:uuids)")
	Flux<LoopVideo> findByCreatorUUIDIn(List<UUID> uuids, Pageable pageable);
	
	@Query("SELECT COUNT(*) FROM loop_video_db WHERE creator_uuid IN (:uuids)") 
	Mono<Long> countByCreatorUUIDIn(List<UUID> uuids);
}
