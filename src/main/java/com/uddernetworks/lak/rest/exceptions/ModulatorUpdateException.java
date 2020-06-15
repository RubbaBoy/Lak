package com.uddernetworks.lak.rest.exceptions;

import com.uddernetworks.lak.sounds.modulation.ModulatorData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ModulatorUpdateException extends EndpointException {
    public ModulatorUpdateException(ModulatorData data) {
        super(HttpStatus.BAD_REQUEST, "A modulator could not be created with the given endpoint data: " + data.getData());
    }

    public ModulatorUpdateException(ModulatorData data, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, "A modulator could not be created with the given endpoint data: " + data.getData(), cause);
    }
}
