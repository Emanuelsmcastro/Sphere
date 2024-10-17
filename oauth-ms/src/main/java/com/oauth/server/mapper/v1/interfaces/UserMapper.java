package com.oauth.server.mapper.v1.interfaces;

import java.util.List;

public interface UserMapper<T, B> {

	B toDTO(T entity);

	List<B> toDTO(List<T> entities);
}
