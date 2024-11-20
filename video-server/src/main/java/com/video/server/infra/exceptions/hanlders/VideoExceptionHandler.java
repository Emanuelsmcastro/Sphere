package com.video.server.infra.exceptions.hanlders;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.utils.dtos.v1.exception.ExceptionResponse;
import com.video.server.infra.exceptions.VideoException;

@ControllerAdvice
public class VideoExceptionHandler {

	@ExceptionHandler(VideoException.class)
	public ResponseEntity<ExceptionResponse> getVideoException(VideoException ex, WebRequest request){
		HttpStatus status = ex.getStatus();
		ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), ex.getMessage(), request.getDescription(false));
		return ResponseEntity.status(status.value()).body(response);
	}
}
