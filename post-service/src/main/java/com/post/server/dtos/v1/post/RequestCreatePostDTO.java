package com.post.server.dtos.v1.post;

import java.util.UUID;

public record RequestCreatePostDTO(UUID creator, String profileName, String description, MetaInfDTO metaInf) {

}
