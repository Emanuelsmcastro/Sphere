package com.oauth.server.mapper.v1.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.entities.Profile;
import com.oauth.server.mapper.v1.interfaces.ProfileMapper;

@Component
public class ProfileMapperImpl implements ProfileMapper<Profile, ResponseProfileDTO> {

	@Override
	public ResponseProfileDTO toDTO(Profile entity) {
		return new ResponseProfileDTO(entity.getFullName());
	}

	@Override
	public List<ResponseProfileDTO> toDTO(List<Profile> entities) {
		return entities.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
