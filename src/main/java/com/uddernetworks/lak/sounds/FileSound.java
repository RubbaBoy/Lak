package com.uddernetworks.lak.sounds;

import java.nio.file.Path;
import java.util.UUID;

/**
 * A {@link Sound} that comes from an uploaded file.
 */
public class FileSound implements Sound {

    private final UUID id;
    private final Path path;

    public FileSound(UUID id, Path path) {
        this.id = id;
        this.path = path;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Path getPath() {
        return path;
    }
}
