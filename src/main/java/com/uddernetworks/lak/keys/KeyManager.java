package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.rest.key.KeyEndpointBodies;

import java.util.List;

/**
 * Manages keys/their properties, and storage of them.
 */
public interface KeyManager {

    /**
     * Gets all {@link Key}s.
     *
     * @return An immutable list of all {@link Key}s
     */
    List<Key> getAllKeys();

    /**
     * Gets a {@link Key} from a given {@link KeyEnum}.
     *
     * @param keyEnum The {@link KeyEnum} to lookup
     * @return The {@link Key}
     */
    Key getKeyFrom(KeyEnum keyEnum);

    /**
     * Updates the internal storage of {@link Key}s for the given {@link Key}.
     *
     * @param updatingKey The {@link Key} to update
     */
    void updateKey(KeyEndpointBodies.UpdatingKey updatingKey);
}
