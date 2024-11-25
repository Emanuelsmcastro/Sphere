package com.media.server.dtos.v1.utils;

import java.nio.file.Path;
import java.util.UUID;

public record OutputVideoDestinationDTO(UUID uuid, Path destinationDir) {

}
