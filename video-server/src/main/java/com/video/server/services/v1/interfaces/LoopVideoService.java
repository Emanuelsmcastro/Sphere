package com.video.server.services.v1.interfaces;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestPart;

import reactor.core.publisher.Mono;

public interface LoopVideoService {
	
	final String VIDEO_PATH = "classpath:videos/";

	ResponseEntity<byte[]> getVideoByFileName(String fileName, String rangeHeader);
	
	Mono<ResponseEntity<String>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono, UUID profileUUID, String profileName);
}
