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
     */
    void receiveKey(KeyEnum keyEnum);
}
