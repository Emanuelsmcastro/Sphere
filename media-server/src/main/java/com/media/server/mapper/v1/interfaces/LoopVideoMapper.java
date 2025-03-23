package com.media.server.mapper.v1.interfaces;

import com.media.server.dtos.v1.loop.ResponseLoopVideo;
import com.media.server.entities.LoopVideo;

public interface LoopVideoMapper {

	ResponseLoopVideo toDTO(LoopVideo entity);
}
