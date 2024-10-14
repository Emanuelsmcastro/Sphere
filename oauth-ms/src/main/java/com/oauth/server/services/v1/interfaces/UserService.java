package com.oauth.server.services.v1.interfaces;

import com.oauth.server.dtos.v1.user.RequestUserRegister;

import jakarta.transaction.Transactional;

public interface UserService {

	@Transactional
	void register(RequestUserRegister dto);
}
