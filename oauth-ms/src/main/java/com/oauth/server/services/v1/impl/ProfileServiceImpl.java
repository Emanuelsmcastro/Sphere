package com.oauth.server.services.v1.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.entities.Profile;
import com.oauth.server.mapper.v1.interfaces.ProfileMapper;
import com.oauth.server.repositories.ProfileRepository;
import com.oauth.server.services.v1.interfaces.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileMapper<Profile, ResponseProfileDTO> mapper;

	@Autowired
	ProfileRepository rep;

	@Override
	public List<ResponseProfileDTO> getProfiles(String name) {
		return mapper.toDTO(rep.findByFullNameContaining(name));
	}

}