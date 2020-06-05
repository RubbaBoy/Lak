package com.uddernetworks.lak.sounds.modulation;

/**
 * Essentially a wrapper for a Map< String, Object > from endpoint data, to ensure type safety.
 */
public interface ModulatorData {

    /**
     * Gets the value for the given key if it matches the type T.
     *
     * @param key The key
     * @param <T> The type to get
     * @return The value associated with the given key, or null
     */
    <T> T get(String key);

    /**
     * Gets the value for the given key if it matches the type T, or a default value.
     *
     * @param key The key
     * @param def The default value instead of null
     * @param <T> The type to get
     * @return The value associated with the given key, or the default
     */
    <T> T get(String key, T def);
}
