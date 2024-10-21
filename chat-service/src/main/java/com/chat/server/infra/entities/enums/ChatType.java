package com.chat.server.infra.entities.enums;

import org.springframework.http.HttpStatus;

import com.chat.server.infra.exceptions.ChatException;

public enum ChatType {

	PRIVATE_CHAT(0);
	
	private int currentValue;
	
	ChatType(int value){
		currentValue = value;
	}
	
	public int getValue() {
		return currentValue;
	}
	
	public ChatType getType(int value) {	
		for(ChatType type : values()) {
			if(type.getValue() == value) return type;
		}
		throw new ChatException("Chat type not exists.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
