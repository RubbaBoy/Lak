package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.sounds.output.SoundPlayer;

/**
 * Receives key inputs from a {@link KeyboardInput} and sends it to the {@link KeyboardOutput} and {@link SoundPlayer}.
 */
public interface KeyboardInterceptor {

    /**
     * Invoked when a key is received from a {@link KeyboardInput}.
     *
     * @param keyEnum The {@link KeyEnum} being received
     * @param keyAction
     */
    void receiveKey(KeyEnum keyEnum, KeyAction keyAction);

    /**
     * Sets the sound to be enabled/disabled.
     *
     * @param soundEnabled If the sound should be enabled
     */
    void setSoundEnabled(boolean soundEnabled);

    /**
     * Returns if the sound is enabled.
     *
     * @return If the sound is enabled
     */
    boolean isSoundEnabled();
}
