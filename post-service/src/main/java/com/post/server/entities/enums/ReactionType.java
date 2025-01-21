package com.post.server.entities.enums;

import org.springframework.http.HttpStatus;

import com.post.server.infra.exceptions.PostServiceException;

public enum ReactionType {
	
	LIKE(0);
	
	private int currentReactionTypeValue;
	
	private ReactionType(int value) {
		currentReactionTypeValue = value;
	}
	
	public int getReactionTypeValue() {
		return currentReactionTypeValue;
	}
	
	public ReactionType getReactionType(int value) {
		for(ReactionType reactionType : values()) {
			if(reactionType.getReactionTypeValue() == value)
				return reactionType;
		}
		throw new PostServiceException("Reaction Type Value Invalid.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
