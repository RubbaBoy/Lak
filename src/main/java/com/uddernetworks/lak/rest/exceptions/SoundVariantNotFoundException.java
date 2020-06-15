package com.uddernetworks.lak.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SoundVariantNotFoundException extends EndpointException {
    public SoundVariantNotFoundException(UUID uuid) {
        super(HttpStatus.BAD_REQUEST, "The given UUID could not be resolved to a sound variant: " + uuid);
    }

    public SoundVariantNotFoundException(UUID uuid, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, "The given UUID could not be resolved to a sound variant: " + uuid, cause);
    }
}
