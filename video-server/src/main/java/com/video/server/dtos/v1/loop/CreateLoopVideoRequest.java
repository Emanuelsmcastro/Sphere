package com.video.server.dtos.v1.loop;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record CreateLoopVideoRequest(UUID creatorUUID, String creatorName, MultipartFile file) {

}
