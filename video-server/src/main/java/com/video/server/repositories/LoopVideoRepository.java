package com.video.server.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.video.server.entities.LoopVideo;

@Repository
public interface LoopVideoRepository extends ReactiveCrudRepository<LoopVideo, Long>{
	
}
