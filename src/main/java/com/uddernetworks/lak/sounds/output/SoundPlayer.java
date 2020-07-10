package com.uddernetworks.lak.sounds.output;

import com.uddernetworks.lak.keys.KeyEnum;

/**
 * Plays sounds according from a {@link KeyEnum} depending on its implementation.
 */
public interface SoundPlayer {

    /**
     * Plays the sound associated with the given {@link KeyEnum}.
     *
     * @param key The {@link KeyEnum} to get the sound from
     */
    void playSound(KeyEnum key);
}
