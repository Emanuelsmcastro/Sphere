package com.oauth.server.dtos.v1.exceptions;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, Integer status, String message, String path) {

}