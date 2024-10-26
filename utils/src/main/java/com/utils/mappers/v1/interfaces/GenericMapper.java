package com.utils.mappers.v1.interfaces;

public interface GenericMapper {

	<T> T convertStringJsonToObject(Class<T> object, String payload);
	
	String convertObjectToJsonString(Object o);
}
