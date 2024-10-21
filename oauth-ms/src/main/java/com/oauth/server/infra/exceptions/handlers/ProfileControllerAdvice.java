package com.oauth.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.oauth.server.dtos.v1.exceptions.ExceptionResponse;
import com.oauth.server.infra.exceptions.ProfileRepException;

@ControllerAdvice
public class ProfileControllerAdvice {

	@ExceptionHandler(ProfileRepException.class)
	public ResponseEntity<ExceptionResponse> profileRepExceptionHandler(ProfileRepException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}