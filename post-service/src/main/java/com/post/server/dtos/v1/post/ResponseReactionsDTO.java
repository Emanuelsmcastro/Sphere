package com.post.server.dtos.v1.post;

import java.sql.Timestamp;
import java.util.UUID;

import com.post.server.entities.enums.ReactionType;

public record ResponseReactionsDTO(ReactionType reactionType, UUID userUUID, Timestamp createdAt) {

}
