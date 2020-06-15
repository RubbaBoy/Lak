package com.uddernetworks.lak.database.key;

import com.uddernetworks.lak.keys.Key;

import java.util.concurrent.CompletableFuture;

public interface KeyRepository {

    /**
     * Updates a {@link Key}'s properties in the database.
     *
     * @param key The {@link Key} to update
     * @return The asynchronous task
     */
    CompletableFuture<Void> updateKey(Key key);

}
