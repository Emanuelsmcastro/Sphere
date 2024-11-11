package com.chat.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.chat.server.infra.exceptions.ChatException;
import com.utils.dtos.v1.exception.ExceptionResponse;

@ControllerAdvice
public class ChatExceptionHandler {

	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ExceptionResponse> chatException(ChatException ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getStatus().value(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(ex.getStatus().value()).body(response);
	}
	
}
