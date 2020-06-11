package com.uddernetworks.lak.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(EndpointException.class)
    protected ResponseEntity<Object> handleConflict(EndpointException e, WebRequest request) {
        return handle(e, request, e.getStatus(), e.getLocalizedMessage());
    }

    private ResponseEntity<Object> handle(EndpointException e, WebRequest request, HttpStatus status, String message) {
        return handleExceptionInternal(e, new ApiError(status.value(), message), new HttpHeaders(), status, request);
    }

    static class ApiError {
        private final int status;
        private final String message;

        public ApiError(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
