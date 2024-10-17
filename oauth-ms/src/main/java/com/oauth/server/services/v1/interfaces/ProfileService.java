package com.oauth.server.services.v1.interfaces;

import java.util.List;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;

public interface ProfileService {

	List<ResponseProfileDTO> getProfiles(String name);
}
