package com.uddernetworks.lak.sounds;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

/**
 * An immutable class to store final metadata on the sound.
 */
public interface Sound {

    /**
     * Gets the UUID of the sound.
     *
     * @return The UUID
     */
    UUID getId();

    /**
     * The path of the filesystem where the sound is located.
     *
     * @return The path of the sound
     */
    URI getURI();
}
