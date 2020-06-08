package com.uddernetworks.lak.sounds;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Gets the {@link Sound} associated with the given UUID, if any.
     *
     * @param soundUUID The UUID to search for
     * @return The {@link Sound} found, if any
     */
    Optional<Sound> getSound(UUID soundUUID);

    /**
     * Sets all sounds to the given list. This is used for initialization such as initial database queries, and should
     * NOT be used for updating. Will only do anything if the sounds list is unpopulated.
     *
     * @param sounds The sounds to set
     */
    void setSounds(List<Sound> sounds);

    /**
     * Adds a {@link Sound} to be used in listings and storage. This must be added before being used by a
     * {@link SoundVariant} via {@link #addSoundVariant(Sound)}.
     *
     * @param sound The {@link Sound} to add
     */
    void addSound(Sound sound);

    /**
     * Gets the {@link SoundVariant} associated with the given UUID, if any.
     *
     * @param variantUUID The UUID to search for
     * @return The {@link SoundVariant} found, if any
     */
    Optional<SoundVariant> getVariant(UUID variantUUID);

    /**
     * Sets all sound variants to the given list. This is used for initialization such as initial database queries, and
     * should NOT be used for updating. Will only do anything if the sound variants list is unpopulated.
     *
     * @param variants The sound variants to set
     */
    void setVariants(List<SoundVariant> variants);

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
