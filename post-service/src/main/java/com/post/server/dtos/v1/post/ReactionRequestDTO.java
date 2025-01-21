


package com.post.server.dtos.v1.post;

import java.util.UUID;

import com.post.server.entities.enums.ReactionType;

public record ReactionRequestDTO(UUID postUUID, ReactionType reactionType) {

}
