package com.uddernetworks.lak.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class SoundNotFoundException extends EndpointException {
    public SoundNotFoundException(UUID uuid) {
        super(HttpStatus.BAD_REQUEST, "The given UUID could not be resolved to a sound: " + uuid);
    }

    public SoundNotFoundException(UUID uuid, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, "The given UUID could not be resolved to a sound: " + uuid, cause);
    }
}
