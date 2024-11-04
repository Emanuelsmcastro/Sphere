package com.video.server.dtos.v1.loop;

import java.util.UUID;

public record ResponseLoopVideo(UUID uuid, UUID creatorUUID, String creatorName, String fileURL) {

}
