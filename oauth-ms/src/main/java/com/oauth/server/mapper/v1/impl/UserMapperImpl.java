package com.oauth.server.mapper.v1.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oauth.server.dtos.v1.user.ResponseUserDTO;
import com.oauth.server.entities.User;
import com.oauth.server.mapper.v1.interfaces.UserMapper;

@Component
public class UserMapperImpl implements UserMapper<User, ResponseUserDTO> {

	@Override
	public ResponseUserDTO toDTO(User entity) {
		return new ResponseUserDTO(entity.getUsername());
	}

	@Override
	public List<ResponseUserDTO> toDTO(List<User> entities) {
		return entities.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
