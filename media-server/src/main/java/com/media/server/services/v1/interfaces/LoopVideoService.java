package com.media.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestPart;

import com.media.server.dtos.v1.loop.ResponseLoopVideo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoopVideoService {
	
	final String VIDEO_PATH_TEMPLATE = "file:videos/%s/%s";

	ResponseEntity<byte[]> getVideoByFileName(UUID uuid, String fileName, String rangeHeader);
	
	Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono, UUID profileUUID, String profileName);
	
	Mono<Page<ResponseLoopVideo>> getFriendLoopVideos(UUID profileUUID, Pageable pageable, String token, String cookie);
	
	Flux<ResponseLoopVideo> getAllLoopVideosByCreatorUUID(UUID creatorUUID);
}
