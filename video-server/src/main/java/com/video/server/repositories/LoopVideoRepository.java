package com.video.server.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.video.server.entities.LoopVideo;

@Repository
public interface LoopVideoRepository extends R2dbcRepository<LoopVideo, Long>{
	
}
