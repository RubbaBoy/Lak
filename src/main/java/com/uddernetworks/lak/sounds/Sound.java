package com.uddernetworks.lak.sounds;

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
}
