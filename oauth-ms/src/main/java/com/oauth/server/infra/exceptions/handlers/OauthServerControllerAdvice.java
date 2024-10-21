package com.oauth.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.oauth.server.dtos.v1.exceptions.ExceptionResponse;
import com.oauth.server.infra.exceptions.OauthServerException;

@ControllerAdvice
public class OauthServerControllerAdvice {

	@ExceptionHandler(OauthServerException.class)
	public ResponseEntity<ExceptionResponse> profileRepExceptionHandler(OauthServerException ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
