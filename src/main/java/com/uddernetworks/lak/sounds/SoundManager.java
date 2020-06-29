package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.database.sound.SoundRepository;
import com.uddernetworks.lak.rest.sound.SoundEndpointBodies;

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
    List<Sound> getAllSounds();

    /**
     * Gets all {@link SoundVariant}s.
     *
     * @return All {@link SoundVariant}s
     */
    List<SoundVariant> getAllSoundVariants();

    /**
     * Checks if the given {@link Sound} has been added via {@link #addSound(Sound)}.
     *
     * @param sound The {@link Sound} to check
     * @return If the {@link Sound} has been added
     */
    boolean isSoundAdded(UUID soundUUID);

    /**
     * Checks if the given {@link SoundVariant} has been added via {@link #addSoundVariant(String, Sound)}.
     *
     * @param soundVariant The {@link SoundVariant} to check
     * @return If the {@link SoundVariant} has been added
     */
    boolean isSoundVariantAdded(UUID variantUUID);

    /**
     * Gets the {@link Sound} associated with the given UUID, if any.
     *
     * @param soundUUID The UUID to search for
     * @return The {@link Sound} found, if any
     */
    Optional<Sound> getSound(UUID soundUUID);

    /**
     * Removes a given {@link Sound}.
     * This method also asynchronously updates the database via {@link SoundRepository#removeSound(UUID)}.
     *
     * @param sound The {@link Sound} to remove
     */
    void removeSound(Sound sound);

    /**
     * Removes a given {@link Sound} by its UUID.
     * This method also asynchronously updates the database via {@link SoundRepository#removeSound(UUID)}.
     *
     * @param soundUUID The UUID of the {@link Sound} to remove
     */
    void removeSound(UUID soundUUID);

    /**
     * Sets all sounds to the given list. This is used for initialization such as initial database queries, and should
     * NOT be used for updating. Will only do anything if the sounds list is unpopulated.
     *
     * @param sounds The sounds to set
     */
    void setSounds(List<Sound> sounds);

    /**
     * Adds a {@link Sound} to be used in listings and storage. This must be added before being used by a
     * {@link SoundVariant} via {@link #addSoundVariant(String, Sound)}.
     * This method also asynchronously updates the database via {@link SoundRepository#addSound(Sound)}.
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
     * This method also asynchronously updates the database via {@link SoundRepository#addVariant(SoundVariant)}.
     * This is equivalent to invoking {@link #addSoundVariant(String, Sound)} with the description being null.
     *
     *
     * @param sound The base {@link Sound}.
     * @return The created {@link SoundVariant}
     */
    SoundVariant addSoundVariant(Sound sound);

    /**
     * Adds a {@link SoundVariant} based off of a given {@link Sound}.
     * If the {@link Sound} has not been addeed via {@link #addSound(Sound)}, it will be added automatically.
     * This method also asynchronously updates the database via {@link SoundRepository#addVariant(SoundVariant)}.
     *
     * @param description The description (sometimes used as a name) of the sound, used for display purposes, not
     *                    identification. May be null.
     * @param sound The base {@link Sound}.
     * @return The created {@link SoundVariant}
     */
    SoundVariant addSoundVariant(String description, Sound sound);

    /**
     * Removes a given {@link SoundVariant}.
     * This method also asynchronously updates the database via {@link SoundRepository#removeVariant(UUID)}.
     *
     * @param sound The {@link SoundVariant} to remove
     */
    void removeSoundVariant(SoundVariant sound);

    /**
     * Removes a given {@link SoundVariant} by its UUID.
     * This method also asynchronously updates the database via {@link SoundRepository#removeVariant(UUID)}.
     *
     * @param variantUUID The UUID of the {@link SoundVariant} to remove
     */
    void removeSoundVariant(UUID variantUUID);

    /**
     * Updates a stored {@link SoundVariant} if it doesn't match with the stored copy
     * This method also asynchronously updates the database via {@link SoundRepository#updateVariant(SoundVariant)}.
     *
     * @param updatingVariant The {@link SoundEndpointBodies.UpdatingVariant} to update
     */
    void updateVariant(SoundEndpointBodies.UpdatingVariant updatingVariant);
}
