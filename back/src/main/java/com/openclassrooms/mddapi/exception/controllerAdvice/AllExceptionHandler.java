package com.openclassrooms.mddapi.exception.controllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.openclassrooms.mddapi.exception.AuthenticationException;
import com.openclassrooms.mddapi.exception.ConflictException;
import com.openclassrooms.mddapi.exception.JwtAuthenticationException;
import com.openclassrooms.mddapi.exception.NotFoundException;

@ControllerAdvice
public class AllExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        LOGGER.error("An error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        LOGGER.error("An invalid argument error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument provided.");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(NotFoundException ex, WebRequest request) {
        LOGGER.error("Item not found error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested entity was not found.");
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException ex, WebRequest request) {
        LOGGER.error("Conflict error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The requested entity already exists.");
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handleJwtAuthenticationException(JwtAuthenticationException ex, WebRequest request) {
        LOGGER.error("Authentication error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        LOGGER.error("Authentication error occurred during the request: {}", request.toString());
        LOGGER.error("Content of error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
    }
}
