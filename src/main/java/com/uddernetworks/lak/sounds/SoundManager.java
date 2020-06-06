package com.uddernetworks.lak.sounds;

import java.util.List;

/**
 * Manages the storage of {@link Sound}s and {@link SoundVariant}s.
 */
public interface SoundManager {

    /**
     * Gets all {@link Sound}s.
     *
     * @return All {@link Sound}s
     */
    List<SoundVariant> getAllSounds();

    /**
     * Checks if the given {@link Sound} has been added via {@link #addSound(Sound)}.
     *
     * @param sound The {@link Sound} to check
     * @return If the {@link Sound} has been added
     */
    boolean isSoundAdded(Sound sound);

    /**
     * Adds a {@link Sound} to be used in listings and storage. This must be added before being used by a
     * {@link SoundVariant} via {@link #addSoundVariant(Sound)}.
     *
     * @param sound The {@link Sound} to add
     */
    void addSound(Sound sound);

    /**
     * Adds a {@link SoundVariant} based off of a given {@link Sound}.
     * If the {@link Sound} has not been addeed via {@link #addSound(Sound)}, it will be added automatically.
     *
     * @param sound The base {@link Sound}.
     * @return The created {@link SoundVariant}
     */
    SoundVariant addSoundVariant(Sound sound);

    /**
     * Updates a stored {@link SoundVariant} if it doesn't match with the stored copy
     *
     * @param soundVariant The {@link SoundVariant} to update
     */
    void updateVariant(SoundVariant soundVariant);
}
