package com.oussama.socialmedia.authservice.controller;


import com.oussama.socialmedia.authservice.Dto.ExceptionResponse;
import com.oussama.socialmedia.authservice.Dto.ExceptionType;
import com.oussama.socialmedia.authservice.exception.InvalidCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .type(ExceptionType.BAD_CREDENTIAL)
                        .build(),HttpStatusCode.valueOf(401)
        );
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotAllowed(MethodNotAllowedException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .error(ex.getMessage())
                        .type(ExceptionType.METHOD_NOT_ALLOWED)
                        .build(),HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .error("internal server error")
                        .type(ExceptionType.UNKNOWN)
                        .build(),HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
