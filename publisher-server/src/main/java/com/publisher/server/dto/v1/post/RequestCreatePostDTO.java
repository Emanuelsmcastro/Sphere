package com.publisher.server.dto.v1.post;

import java.util.UUID;

public record RequestCreatePostDTO(UUID creator, String description, MetaInfDTO metaInf) {

}
