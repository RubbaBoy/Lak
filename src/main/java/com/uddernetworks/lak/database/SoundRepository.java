package com.uddernetworks.lak.database;

import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundVariant;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SoundRepository {

    /**
     * Initializes the database with relevant connections.
     *
     * @throws IOException If an exception occurs
     */
    void init() throws IOException;

    /**
     * Adds a given {@link Sound} to the database.
     *
     * @param sound The {@link Sound} to add
     * @return The asynchronous task
     */
    CompletableFuture<Void> addSound(Sound sound);

    /**
     * Adds a given {@link Sound} to the database.
     *
     * @param soundUUID The UUID of the {@link Sound} to add
     * @return The asynchronous task
     */
    CompletableFuture<Void> removeSound(UUID soundUUID);

    /**
     * Adds a given {@link SoundVariant} to the database.
     *
     * @param soundVariant The {@link SoundVariant} to add
     * @return The asynchronous task
     */
    CompletableFuture<Void> addVariant(SoundVariant soundVariant);

    /**
     * Adds a given {@link SoundVariant} to the database.
     *
     * @param variantUUID The UUID of the {@link SoundVariant} to add
     * @return The asynchronous task
     */
    CompletableFuture<Void> removeVariant(UUID variantUUID);

    /**
     * Updates a {@link SoundVariant} already in the database.
     *
     * @param soundVariant The {@link SoundVariant} to update
     * @return The asynchronous task
     */
    CompletableFuture<Void> updateVariant(SoundVariant soundVariant);

}
