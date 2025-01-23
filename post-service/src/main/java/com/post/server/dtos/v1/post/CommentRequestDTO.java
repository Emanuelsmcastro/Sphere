package com.post.server.dtos.v1.post;

import java.util.UUID;

public record CommentRequestDTO(UUID postUUID, String content) {

}
