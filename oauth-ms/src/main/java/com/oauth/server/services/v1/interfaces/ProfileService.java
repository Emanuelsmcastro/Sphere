package com.oauth.server.services.v1.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oauth.server.dtos.v1.user.RequestAddFriendDTO;
import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.entities.Profile;

public interface ProfileService {

	List<ResponseProfileDTO> getProfiles(String name);

	Page<ResponseProfileDTO> getProfiles(String name, Pageable pageable);

	Page<ResponseProfileDTO> getUserProfileFriends(UUID userProfileUUID, Pageable pageable);

	Profile findByUUID(UUID uuid);

	void addFriend(RequestAddFriendDTO dto, UUID profileUUID);
}
