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
     * Sets all {@link Key}s to a given list. This is used for initialization such as initial database queries, and
     * should NOT be used for updating. Will only do anything if the sound variants list is unpopulated.
     *
     * @param keys The keys to set
     */
    void setKeys(List<Key> keys);

    /**
     * Updates the internal storage of {@link Key}s for the given {@link Key}.
     *
     * @param updatingKey The {@link Key} to update
     */
    void updateKey(KeyEndpointBodies.UpdatingKey updatingKey);
}
