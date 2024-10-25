package com.oauth.server.dtos.v1.user;

public record RequestUserRegister(String username, String profileFirstName, String profileLastName, String password,
		String password2) {

}
