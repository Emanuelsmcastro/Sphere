package com.publisher.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.publisher.server.dto.v1.exception.ExceptionResponse;
import com.publisher.server.infra.exceptions.JsonConvertException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JsonConvertException.class)
	public ResponseEntity<ExceptionResponse> jsonConvertException(JsonConvertException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(),
				request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
