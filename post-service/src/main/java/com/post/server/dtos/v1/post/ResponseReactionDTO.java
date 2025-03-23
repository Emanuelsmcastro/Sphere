package com.post.server.dtos.v1.post;

import java.sql.Timestamp;
import java.util.UUID;

import com.post.server.entities.enums.ReactionType;

public record ResponseReactionDTO(ReactionType reactionType, UUID userUUID, String profileName, Timestamp createdAt) {

}
