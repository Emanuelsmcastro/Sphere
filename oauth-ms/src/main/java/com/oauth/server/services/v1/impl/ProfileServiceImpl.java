package com.oauth.server.services.v1.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.user.RequestAddFriendDTO;
import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.entities.Profile;
import com.oauth.server.infra.exceptions.ProfileRepException;
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

	@Override
	public Page<ResponseProfileDTO> getProfiles(String name, Pageable pageable) {
		return mapper.toDTO(rep.findByFullNameContaining(name, pageable));
	}

	@Override
	public Profile findByUUID(UUID uuid) {
		return rep.findByUuid(uuid).orElseThrow(() -> new ProfileRepException("Profile Not exists."));
	}

	@Override
	public void addFriend(RequestAddFriendDTO dto, UUID profileUUID) {
		Profile toAdd = findByUUID(dto.userUUID());
		Profile profile = findByUUID(profileUUID);
		profile.getFriends().add(toAdd);
		toAdd.getFriends().add(profile);
		rep.save(profile);
		rep.save(toAdd);

		System.out.println(profile);
	}

	@Override
	public Page<ResponseProfileDTO> getUserProfileFriends(UUID userProfileUUID, Pageable pageable) {
		return mapper.toDTO(rep.findAllByFriendUuid(userProfileUUID, pageable));
	}

}