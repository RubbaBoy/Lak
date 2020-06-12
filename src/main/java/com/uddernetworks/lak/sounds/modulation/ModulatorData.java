package com.uddernetworks.lak.sounds.modulation;

import java.util.HashMap;
import java.util.Map;

/**
 * Essentially a wrapper for a Map< String, Object > from endpoint data, to ensure type safety.
 */
public class ModulatorData {

    private final Map<String, Object> data;

    public ModulatorData() {
        this(new HashMap<>());
    }

    public ModulatorData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Gets the value for the given key if it matches the type T.
     *
     * @param key The key
     * @param <T> The type to get
     * @return The value associated with the given key, or null
     */
    public <T> T get(String key) {
        return (T) data.get(key);
    }

    /**
     * Gets the value for the given key if it matches the type T, or a default value.
     *
     * @param key The key
     * @param def The default value instead of null
     * @param <T> The type to get
     * @return The value associated with the given key, or the default
     */
    public <T> T get(String key, T def) {
        try {
            var gotten = data.get(key);
            return gotten == null ? def : (T) gotten;
        } catch (Exception ignored) {
            return def;
        }
    }
}
