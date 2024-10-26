package com.post.server.infra.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.post.server.infra.exceptions.PostServiceException;
import com.utils.dtos.v1.exception.ExceptionResponse;

@ControllerAdvice
public class PostServiceExceptionHandler {

	@ExceptionHandler(PostServiceException.class)
	public ResponseEntity<ExceptionResponse> postServiceExceptionHandler(PostServiceException ex, WebRequest request){
		HttpStatus status = ex.getStatus();
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
