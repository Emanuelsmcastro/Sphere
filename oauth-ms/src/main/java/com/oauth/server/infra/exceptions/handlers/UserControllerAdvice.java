package com.oauth.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.oauth.server.infra.exceptions.RegistrationException;
import com.utils.dtos.v1.exception.ExceptionResponse;

@ControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<ExceptionResponse> registrationExceptionHandler(RegistrationException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
