package com.oauth.server.services.v1.interfaces;

import java.util.List;

import com.oauth.server.dtos.v1.user.RequestUserRegister;
import com.oauth.server.dtos.v1.user.ResponseUserDTO;

public interface UserService {

	void register(RequestUserRegister dto);

	List<ResponseUserDTO> getUsers(String username);
}
