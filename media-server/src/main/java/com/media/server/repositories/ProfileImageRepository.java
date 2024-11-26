package com.media.server.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.media.server.entities.ProfileImage;

import reactor.core.publisher.Mono;

@Repository
public interface ProfileImageRepository extends R2dbcRepository<ProfileImage, Long>{
	
	Mono<ProfileImage> findByIsMainProfileImageAndProfileUUID(boolean isMainProfileImage, UUID profileUUID);
}
