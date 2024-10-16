package com.publisher.server.dto.v1.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, Integer status, String message, String path) {

}