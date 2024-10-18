package com.oauth.server.mapper.v1.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oauth.server.dtos.v1.user.ResponseProfileDTO;

public interface ProfileMapper<T, B> {

	B toDTO(T entity);

	List<B> toDTO(List<T> entities);

	Page<ResponseProfileDTO> toDTO(Page<T> entityPageable);
}
