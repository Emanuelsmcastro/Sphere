package com.video.server.mapper.v1.impl;

import org.springframework.stereotype.Component;

import com.video.server.dtos.v1.loop.ResponseLoopVideo;
import com.video.server.entities.LoopVideo;
import com.video.server.mapper.v1.interfaces.LoopVideoMapper;

@Component
public class LoopVideoMapperImpl implements LoopVideoMapper{

	@Override
	public ResponseLoopVideo toDTO(LoopVideo entity) {
		return new ResponseLoopVideo(entity.getUuid(), entity.getCreatorUUID(), entity.getCreatorName(), entity.getVideoURL());
	}

}
