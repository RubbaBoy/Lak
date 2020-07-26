package com.uddernetworks.lak.keys;

/**
 * Sends keyboard input to the output destination. This may be a USB output, Bluetooth, PS/2, etc. See implementation
 * for details.
 */
public interface KeyboardOutput {

    /**
     * Outputs a given {@link KeyEnum}.
     *
     * @param keyEnum The {@link KeyEnum} to output
     * @param keyAction
     */
    void outputKey(KeyEnum keyEnum, KeyAction keyAction);

}
