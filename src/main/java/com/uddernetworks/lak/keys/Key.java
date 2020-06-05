package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.sounds.SoundVariant;

/**
 * A mutable object to store data about a physical keyboard key.
 */
public interface Key {

    /**
     * Gets the {@link KeyEnum} that the current {@link Key} is representing.
     *
     * @return The constant {@link KeyEnum}
     */
    KeyEnum getKey();

    /**
     * Gets the {@link SoundVariant} of the key
     *
     * @return The {@link SoundVariant} of the key
     */
    SoundVariant getSound();

    /**
     * Sets the {@link SoundVariant} of the key.
     *
     * @param soundVariant The {@link SoundVariant} of the key
     */
    void setSound(SoundVariant soundVariant);

    /**
     * Gets if the sound should loop.
     *
     * @return If the sound should loop
     */
    boolean isLoop();

    /**
     * Sets if the sound should loop.
     *
     * @param loop If the sound should loop
     */
    void setLoop(boolean loop);
}
