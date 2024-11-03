package com.video.server.services.v1.interfaces;

import org.springframework.http.ResponseEntity;

public interface LoopVideoService {
	
	final String VIDEO_PATH = "classpath:videos/";

	ResponseEntity<byte[]> getVideoByFileName(String fileName, String rangeHeader);
}
