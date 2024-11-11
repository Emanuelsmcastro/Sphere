package com.utils.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.utils.dtos.v1.exception.ExceptionResponse;
import com.utils.infra.exceptions.UtilsException;

@ControllerAdvice
public class UtilsExceptionHandler {

	@ExceptionHandler(UtilsException.class)
	public ResponseEntity<ExceptionResponse> utilsExceptionHandler(UtilsException ex, WebRequest request){
		HttpStatus status = ex.getStatus();
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
