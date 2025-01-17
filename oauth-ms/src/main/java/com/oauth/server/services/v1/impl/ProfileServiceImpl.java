package com.oauth.server.services.v1.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oauth.server.dtos.v1.chat.CreateChatRequestDTO;
import com.oauth.server.dtos.v1.user.AddFriendCallbackDTO;
import com.oauth.server.dtos.v1.user.RequestAddFriendDTO;
import com.oauth.server.dtos.v1.user.ResponseProfileDTO;
import com.oauth.server.entities.Profile;
import com.oauth.server.infra.exceptions.ProfileRepException;
import com.oauth.server.mapper.v1.interfaces.ProfileMapper;
import com.oauth.server.repositories.ProfileRepository;
import com.oauth.server.services.v1.interfaces.ChatRequestService;
import com.oauth.server.services.v1.interfaces.ProfileService;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Service
public class ProfileServiceImpl implements ProfileService {

	private final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Autowired
	GenericMapper genericMapper;

	@Autowired
	ProfileMapper<Profile, ResponseProfileDTO> mapper;

	@Autowired
	ProfileRepository rep;

	@Autowired
	ChatRequestService chatRequestService;

	@Autowired
	@Qualifier("addFriendCallbackQueue")
	Queue addFriendCallbackQueue;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	public List<ResponseProfileDTO> getProfiles(String name) {
		return mapper.toDTO(rep.findByFullNameContaining(name));
	}

	@Override
	public Page<ResponseProfileDTO> getProfiles(String name, Pageable pageable, UUID userUUID) {
		return mapper.toDTO(rep.findByFullNameContaining(name, pageable, userUUID));
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

		sendAddFriendCallback(profile, toAdd);
		sendCreateChatRequest(profile.getUuid(), toAdd.getUuid());

		logger.debug("Add Friend: " + profile.getUser().getUsername() + " | " + toAdd.getUser().getUsername());

	}

	@Override
	public Page<ResponseProfileDTO> getUserProfileFriends(UUID userProfileUUID, Pageable pageable) {
		return mapper.toDTO(rep.findAllByFriendUuid(userProfileUUID, pageable));
	}

	private void sendCreateChatRequest(UUID userProfileUUID, UUID friendProfileUUID) {
		chatRequestService.createChatRequest(new CreateChatRequestDTO(userProfileUUID, friendProfileUUID));
	}

	private void sendAddFriendCallback(Profile sender, Profile receiver) {
		AddFriendCallbackDTO callbackDTO = new AddFriendCallbackDTO(mapper.toDTO(sender), mapper.toDTO(receiver));
		rabbitTemplate.convertAndSend(addFriendCallbackQueue.getName(),
				genericMapper.convertObjectToJsonString(callbackDTO));
	}

	@Override
	public List<ResponseProfileDTO> getUserProfilesFriend(UUID userProfileUUID) {
		return mapper.toDTO(rep.findAllByFriendUuid(userProfileUUID));
	}

}