package com.uddernetworks.lak.keys;

import javax.annotation.PostConstruct;

/**
 * Handles input from a keyboard. Where said keyboard input comes from is implementation-dependant, the default
 * implementation is from normal system USB input.
 */
public interface KeyboardInput {

    /**
     * Initializes the keyboard listening, if required by the implementation.
     * Should inclue {@link PostConstruct}
     */
    void init();

    /**
     * Starts listening for keyboard input.
     */
    void startListening();

    /**
     * Stops the listening of keyboard input.
     */
    void stopListening();
}
