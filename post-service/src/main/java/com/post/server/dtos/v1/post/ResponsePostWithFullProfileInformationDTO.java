package com.post.server.dtos.v1.post;

import java.util.UUID;

public record ResponsePostWithFullProfileInformationDTO(UUID postUUID, UUID creator, String profileName, String description, MetaInfDTO metaInf) {

}
