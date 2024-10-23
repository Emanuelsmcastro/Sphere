package com.oauth.server.dtos.v1.user;

public record AddFriendCallbackDTO(ResponseProfileDTO sender, ResponseProfileDTO receiver) {

}
