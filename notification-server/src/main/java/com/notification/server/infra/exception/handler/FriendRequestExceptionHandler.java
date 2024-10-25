package com.notification.server.infra.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.notification.server.dtos.v1.exception.ExceptionResponse;
import com.notification.server.infra.exception.FriendRequestException;
import com.notification.server.infra.exception.FriendRequestRepException;


@RestControllerAdvice
public class FriendRequestExceptionHandler {

	@ExceptionHandler(FriendRequestException.class)
	public ResponseEntity<ExceptionResponse> registrationExceptionHandler(FriendRequestException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
	
	@ExceptionHandler(FriendRequestRepException.class)
	public ResponseEntity<ExceptionResponse> registrationExceptionHandler(FriendRequestRepException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
