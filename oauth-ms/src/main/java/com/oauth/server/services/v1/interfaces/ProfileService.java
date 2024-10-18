package com.oauth.server.services.v1.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;

public interface ProfileService {

	List<ResponseProfileDTO> getProfiles(String name);

	Page<ResponseProfileDTO> getProfiles(String name, Pageable pageable);
}
