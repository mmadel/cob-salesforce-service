package com.cob.salesforce.exceptions;


import com.cob.salesforce.exceptions.response.ControllerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.ws.rs.WebApplicationException;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = InvalidBearerTokenException.class)
    public ResponseEntity handleInvalidBearerTokenException(InvalidBearerTokenException invalidBearerTokenException) {
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(invalidBearerTokenException.getMessage(), HttpStatus.UNAUTHORIZED);
        log.error(controllerErrorResponse.getMessage());
        return new ResponseEntity(controllerErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleAccessDeniedException(AuthenticationException authenticationException) {
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
        log.error(controllerErrorResponse.getMessage());
        return new ResponseEntity<>(controllerErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(accessDeniedException.getMessage(), HttpStatus.UNAUTHORIZED);
        log.error(controllerErrorResponse.getMessage());
        return new ResponseEntity<>(controllerErrorResponse, HttpStatus.UNAUTHORIZED);
    }
}
