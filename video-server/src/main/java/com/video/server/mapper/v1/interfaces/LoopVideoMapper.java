package com.video.server.mapper.v1.interfaces;

import com.video.server.dtos.v1.loop.ResponseLoopVideo;
import com.video.server.entities.LoopVideo;

public interface LoopVideoMapper {

	ResponseLoopVideo toDTO(LoopVideo entity);
}
