package com.uddernetworks.lak.rest;

import org.springframework.http.HttpStatus;

public class EndpointException extends RuntimeException {

    private final HttpStatus status;

    public EndpointException(HttpStatus status) {
        this.status = status;
    }

    public EndpointException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public EndpointException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
