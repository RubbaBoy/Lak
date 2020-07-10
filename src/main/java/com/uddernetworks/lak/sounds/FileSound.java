package com.uddernetworks.lak.sounds;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

/**
 * A {@link Sound} that comes from an uploaded file.
 */
public class FileSound implements Sound {

    private final UUID id;
    private final String relPath;

    public FileSound(UUID id, String relPath) {
        this.id = id;
        this.relPath = relPath;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getRelativePath() {
        return relPath;
    }

    @Override
    public String toString() {
        return "FileSound{" +
                "id=" + id +
                ", relPath=" + relPath +
                '}';
    }
}
