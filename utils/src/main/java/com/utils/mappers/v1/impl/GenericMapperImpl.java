package com.utils.mappers.v1.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.infra.exceptions.UtilsException;
import com.utils.mappers.v1.interfaces.GenericMapper;

@Component
public class GenericMapperImpl implements GenericMapper{

	@Override
	public <T> T convertStringJsonToObject(Class<T> object, String payload) {
		ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.readValue(payload, object);
	    } catch (Exception e) {
	    	throw new UtilsException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
	public String convertObjectToJsonString(Object o) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonString = objectMapper.writeValueAsString(o);
			return jsonString;
		} catch (Exception e) {
			throw new UtilsException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
