package com.uddernetworks.lak.sounds;

import java.util.UUID;

/**
 * A {@link Sound} that comes from an uploaded file.
 */
public class FileSound implements Sound {

    private final UUID id;

    public FileSound(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
