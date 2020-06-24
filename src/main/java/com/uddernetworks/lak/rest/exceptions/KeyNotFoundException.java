package com.uddernetworks.lak.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KeyNotFoundException extends EndpointException {
    public KeyNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "The given key ID could not be resolved to a key");
    }

    public KeyNotFoundException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, "The given key ID could not be resolved to a key", cause);
    }
}
