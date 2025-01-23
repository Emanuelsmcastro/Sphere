package com.post.server.dtos.v1.post;

import java.sql.Timestamp;
import java.util.UUID;

public record ResponseCommentDTO(UUID creatorUUID, String content, Timestamp createdAt) {

}
