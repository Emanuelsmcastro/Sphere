package com.oauth.server.services.v1.interfaces;

import com.oauth.server.dtos.v1.user.RequestUserRegister;

public interface UserService {

	void register(RequestUserRegister dto);
}
