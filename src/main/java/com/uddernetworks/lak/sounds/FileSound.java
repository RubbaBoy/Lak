package com.uddernetworks.lak.sounds;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

/**
 * A {@link Sound} that comes from an uploaded file.
 */
public class FileSound implements Sound {

    private final UUID id;
    private final URI uri;

    public FileSound(UUID id, URI uri) {
        this.id = id;
        this.uri = uri;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public String toString() {
        return "FileSound{" +
                "id=" + id +
                ", uri=" + uri +
                '}';
    }
}
