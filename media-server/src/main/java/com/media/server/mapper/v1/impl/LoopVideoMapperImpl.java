package com.media.server.mapper.v1.impl;

import org.springframework.stereotype.Component;

import com.media.server.dtos.v1.loop.ResponseLoopVideo;
import com.media.server.entities.LoopVideo;
import com.media.server.mapper.v1.interfaces.LoopVideoMapper;

@Component
public class LoopVideoMapperImpl implements LoopVideoMapper{

	@Override
	public ResponseLoopVideo toDTO(LoopVideo entity) {
		return new ResponseLoopVideo(entity.getUuid(), entity.getCreatorUUID(), entity.getCreatorName(), entity.getVideoURL());
	}

}
