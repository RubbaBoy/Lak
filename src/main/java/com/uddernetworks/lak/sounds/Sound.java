package com.uddernetworks.lak.sounds;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

/**
 * An immutable class to store final metadata on the sound.
 */
@JsonDeserialize(as = FileSound.class)
public interface Sound {

    /**
     * Gets the UUID of the sound.
     *
     * @return The UUID
     */
    UUID getId();

    /**
     * The path relative to the sound base where the sound is located.
     *
     * @return The relative path of the sound
     */
    String getRelativePath();
}
